package com.harucourt.shared.config;

import com.harucourt.infrastructure.auth.JwtFilter;
import com.harucourt.infrastructure.auth.JwtProvider;
import com.harucourt.shared.config.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerExceptionResolver;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, PathMatcher pathMatcher, HandlerExceptionResolver handlerExceptionResolver) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(
                        new JwtFilter(jwtProvider, jwtProperties, pathMatcher, handlerExceptionResolver),
                        UsernamePasswordAuthenticationFilter.class
                )
                .authorizeHttpRequests(request ->
                        request.anyRequest().permitAll()
                );

        return http.build();
    }
}
