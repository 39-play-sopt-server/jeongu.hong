package org.sopt.global.auth.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.sopt.global.auth.AuthMember;
import org.sopt.global.auth.token.TokenVerifier;
import org.sopt.global.error.BusinessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    public static final String ERROR_CODE_ATTRIBUTE = "authErrorCode";

    private final TokenVerifier tokenVerifier;
    private final BearerTokenResolver bearerTokenResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        bearerTokenResolver.resolve(request)
            .ifPresent(token -> authenticate(token, request));

        filterChain.doFilter(request, response);
    }

    private void authenticate(String token, HttpServletRequest request) {
        try {
            AuthMember authMember = AuthMember.from(tokenVerifier.verifyAccessToken(token));

            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(authMember, null, List.of());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BusinessException e) {
            request.setAttribute(ERROR_CODE_ATTRIBUTE, e.getErrorCode());
        }
    }
}
