package com.harucourt.infrastructure.auth;

import com.harucourt.domain.auth.exception.EmptyTokenException;
import com.harucourt.shared.auth.CustomUserDetails;
import com.harucourt.shared.config.properties.JwtProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;
    private final PathMatcher pathMatcher;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        if (token != null && !token.isBlank()) {
            UserDetails userDetails = new CustomUserDetails(jwtProvider.getUuid(token), jwtProvider.getEmail(token));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            handlerExceptionResolver.resolveException(request, response, null, new EmptyTokenException());
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        return (HttpMethod.POST.name().equals(method) && pathMatcher.match("/auth/google", uri)) ||
                (HttpMethod.PATCH.name().equals(method) && pathMatcher.match("/auth", uri));
    }

    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && !header.isBlank() && header.startsWith(jwtProperties.getPrefix())) {
            return header.replace(jwtProperties.getPrefix(), "").trim();
        }

        return null;
    }
}

