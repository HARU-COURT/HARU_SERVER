package com.harucourt.domain.worry.domain;

import com.harucourt.domain.user.domain.User;
import com.harucourt.domain.worry.domain.type.WorryCategory;
import com.harucourt.domain.worry.domain.type.ResponseMode;
import com.harucourt.domain.worry.domain.value.Judge;
import com.harucourt.shared.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_worry")
@Entity
public class Worry extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "tbl_worry_category", joinColumns = @JoinColumn(name = "worry_id"))
    private List<WorryCategory> categoryList;


    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT", length = 1000)
    private String content;

    @Enumerated(EnumType.STRING)
    private ResponseMode responseMode;

    @Embedded
    private Judge judge;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public Worry(List<WorryCategory> categoryList, String title, String content, ResponseMode responseMode, Judge judge, User user) {
        this.categoryList = categoryList;
        this.title = title;
        this.content = content;
        this.responseMode = responseMode;
        this.judge = judge;
        this.user = user;
    }
}
