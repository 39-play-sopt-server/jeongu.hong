package org.sopt.member.service;

import org.sopt.global.auth.AuthMember;
import org.sopt.global.auth.token.TokenBlacklist;
import org.sopt.global.error.BusinessException;
import org.sopt.member.domain.Member;
import org.sopt.member.code.MemberErrorCode;
import org.sopt.member.repository.MemberRepository;
import org.sopt.member.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenBlacklist tokenBlacklist;

    @Transactional
    public void withdraw(AuthMember authMember) {
        Member member = getMember(authMember.memberId());
        member.withdraw();
        refreshTokenRepository.deleteByMemberId(authMember.memberId());
        tokenBlacklist.add(authMember.tokenId(), authMember.tokenExpiresAt());
    }

    @Transactional(readOnly = true)
    public Member getMember(Long id) {
        return memberRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
