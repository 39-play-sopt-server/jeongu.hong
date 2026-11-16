package org.sopt.member.service;

import org.sopt.global.error.BusinessException;
import org.sopt.global.jwt.JwtProvider;
import org.sopt.member.code.MemberErrorCode;
import org.sopt.member.domain.Member;
import org.sopt.member.dto.request.LoginRequest;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.dto.response.LoginResponse;
import org.sopt.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public Long signup(MemberCreateRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new BusinessException(MemberErrorCode.DUPLICATE_EMAIL);
        }

        Member member = new Member(request.name(), request.email(), passwordEncoder.encode(request.password()));
        return memberRepository.save(member).getId();
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        // 이메일이 없는 경우와 비밀번호가 틀린 경우를 같은 에러로 응답 (가입 여부 노출 방지)
        Member member = memberRepository.findByEmail(request.email())
                .filter(m -> !m.isDeleted())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new BusinessException(MemberErrorCode.INVALID_CREDENTIALS);
        }

        return new LoginResponse(jwtProvider.createAccessToken(member.getId()));
    }
}
