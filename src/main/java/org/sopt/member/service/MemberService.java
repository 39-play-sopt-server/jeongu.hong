package org.sopt.member.service;

import org.sopt.global.error.BusinessException;
import org.sopt.member.domain.Member;
import org.sopt.member.code.MemberErrorCode;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void createMember(MemberCreateRequest memberCreateRequest) {
        if (memberRepository.existsByEmail(memberCreateRequest.email())) {
            throw new BusinessException(MemberErrorCode.DUPLICATE_EMAIL);
        }
        memberRepository.save(new Member(memberCreateRequest.name(), memberCreateRequest.email()));
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = getMember(memberId);
        member.withdraw();
    }

    @Transactional(readOnly = true)
    public Member getMember(Long id) {
        return memberRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
