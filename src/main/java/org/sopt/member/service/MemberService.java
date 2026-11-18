package org.sopt.member.service;

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

    @Transactional
    public void withdraw(Long memberId) {
        Member member = getMember(memberId);
        member.withdraw();
        refreshTokenRepository.deleteByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public Member getMember(Long id) {
        return memberRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
