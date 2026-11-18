package org.sopt.global.auth.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.sopt.global.auth.AuthMember;
import org.sopt.global.auth.token.TokenBlacklist;
import org.sopt.global.auth.token.TokenVerifier;
import org.sopt.global.code.AuthErrorCode;
import org.sopt.global.error.BusinessException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    public static final String ERROR_CODE_ATTRIBUTE = "authErrorCode";

    private final TokenVerifier tokenVerifier;
    private final BearerTokenResolver bearerTokenResolver;
    private final TokenBlacklist tokenBlacklist;

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

            if (isRevoked(authMember.tokenId())) {
                throw new BusinessException(AuthErrorCode.REVOKED_TOKEN);
            }

            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(authMember, null, List.of());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BusinessException e) {
            request.setAttribute(ERROR_CODE_ATTRIBUTE, e.getErrorCode());
        }
    }

    private boolean isRevoked(String tokenId) {
        try {
            return tokenBlacklist.contains(tokenId);
        } catch (DataAccessException e) {
            log.error("토큰 블랙리스트 조회 실패 - 확인 없이 통과(fail-open)", e);
            return false;
        }
    }
}
