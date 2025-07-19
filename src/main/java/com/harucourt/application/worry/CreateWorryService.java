package com.harucourt.application.worry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harucourt.domain.user.domain.User;
import com.harucourt.domain.user.exception.UserNotFoundException;
import com.harucourt.domain.worry.domain.Worry;
import com.harucourt.domain.worry.domain.type.WorryCategory;
import com.harucourt.domain.worry.domain.value.Judge;
import com.harucourt.infrastructure.persistence.user.UserRepository;
import com.harucourt.infrastructure.persistence.worry.WorryRepository;
import com.harucourt.presentation.worry.dto.request.CreateWorryRequest;
import com.harucourt.shared.auth.CustomUserDetails;
import com.harucourt.shared.error.GlobalErrorProperty;
import com.harucourt.shared.error.HaruException;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.StructuredResponseCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CreateWorryService {

    private final WorryRepository worryRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public Long execute(CustomUserDetails userDetails, CreateWorryRequest request) throws JsonProcessingException {
        User user = userRepository.findByUuid(userDetails.uuid())
                .orElseThrow(UserNotFoundException::new);

        String input = generatePrompt(request);

        OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        StructuredResponseCreateParams<Judge> createParams = ResponseCreateParams.builder()
                .input(input)
                .text(Judge.class)
                .model(ChatModel.GPT_4O_MINI)
                .build();

        Judge judge = client.responses().create(createParams).output().stream()
                .flatMap(item -> item.message().stream())
                .flatMap(message -> message.content().stream())
                .flatMap(content -> content.outputText().stream())
                .findFirst()
                .orElseThrow(() -> new HaruException(GlobalErrorProperty.INTERNAL_SERVER_ERROR));

        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(judge));

        Worry worry = worryRepository.save(new Worry(
                request.categoryList(),
                request.title(),
                request.content(),
                request.responseMode(),
                judge,
                user
        ));

        return worry.getId();
    }

    private String generatePrompt(CreateWorryRequest request) {
        return String.format(
                """
                        카테고리: %s
                        
                        고민 제목:
                        %s
                        
                        고민 내용:
                        %s
                        
                        응답 모드: %s
                        
                        응답 모드에 따라 어조, 분석 방식, 말투를 조정해서 판결을 내려줘.
                        """,
                formatCategoryList(request.categoryList()), request.title(), request.content(), request.responseMode().getDescription());
    }

    private String formatCategoryList(List<WorryCategory> categoryList) {
        return categoryList.stream()
                .map(WorryCategory::getDescription)
                .collect(Collectors.joining(", "));
    }
}