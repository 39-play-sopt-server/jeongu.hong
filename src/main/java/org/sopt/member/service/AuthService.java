package org.sopt.member.service;

import java.time.LocalDateTime;

import org.sopt.global.auth.AuthMember;
import org.sopt.global.auth.token.TokenBlacklist;
import org.sopt.global.code.AuthErrorCode;
import org.sopt.global.error.BusinessException;
import org.sopt.global.auth.SessionProperties;
import org.sopt.global.auth.token.TokenPair;
import org.sopt.global.auth.token.TokenIssuer;
import org.sopt.global.auth.token.TokenVerifier;
import org.sopt.member.code.MemberErrorCode;
import org.sopt.member.domain.Member;
import org.sopt.member.domain.RefreshToken;
import org.sopt.member.dto.request.LoginRequest;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.repository.MemberRepository;
import org.sopt.member.repository.RefreshTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenVerifier tokenVerifier;
    private final TokenIssuer tokenIssuer;
    private final RefreshTokenRepository refreshTokenRepository;
    private final SessionProperties sessionProperties;
    private final TokenBlacklist tokenBlacklist;

    @Transactional
    public Long signup(MemberCreateRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new BusinessException(MemberErrorCode.DUPLICATE_EMAIL);
        }

        Member member = new Member(request.name(), request.email(), passwordEncoder.encode(request.password()));
        return memberRepository.save(member).getId();
    }

    @Transactional
    public TokenPair login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
            .filter(m -> !m.isDeleted())
            .orElseThrow(() -> new BusinessException(MemberErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new BusinessException(MemberErrorCode.INVALID_CREDENTIALS);
        }

        return startNewSession(member.getId());
    }

    private TokenPair startNewSession(Long memberId) {
        String accessToken = tokenIssuer.issueAccessToken(memberId);
        String refreshToken = tokenIssuer.issueRefreshToken(memberId);
        LocalDateTime sessionExpiresAt = LocalDateTime.now().plus(sessionProperties.maxLifetime());

        refreshTokenRepository.findByMemberId(memberId)
            .ifPresentOrElse(
                saved -> saved.renew(refreshToken, sessionExpiresAt),
                () -> refreshTokenRepository.save(RefreshToken.create(memberId, refreshToken, sessionExpiresAt))
            );

        return new TokenPair(accessToken, refreshToken);
    }

    @Transactional(noRollbackFor = BusinessException.class)
    public TokenPair reissue(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new BusinessException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        Long memberId = tokenVerifier.verifyRefreshToken(refreshToken).memberId();
        RefreshToken saved = refreshTokenRepository.findByMemberId(memberId)
            .orElseThrow(() -> new BusinessException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND));

        if (!saved.matches(refreshToken)) {
            refreshTokenRepository.delete(saved);
            throw new BusinessException(AuthErrorCode.REFRESH_TOKEN_REUSED);
        }

        if (saved.isSessionExpired(LocalDateTime.now())) {
            refreshTokenRepository.delete(saved);
            throw new BusinessException(AuthErrorCode.SESSION_EXPIRED);
        }

        if (memberRepository.findByIdAndDeletedAtIsNull(memberId).isEmpty()) {
            refreshTokenRepository.delete(saved);
            throw new BusinessException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        String newAccessToken = tokenIssuer.issueAccessToken(memberId);
        String newRefreshToken = tokenIssuer.issueRefreshToken(memberId);
        saved.rotate(newRefreshToken);

        return new TokenPair(newAccessToken, newRefreshToken);
    }

    @Transactional
    public void logout(AuthMember authMember) {
        refreshTokenRepository.deleteByMemberId(authMember.memberId());
        tokenBlacklist.add(authMember.tokenId(), authMember.tokenExpiresAt());
    }
}
