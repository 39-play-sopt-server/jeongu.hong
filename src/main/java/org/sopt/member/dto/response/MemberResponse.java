package org.sopt.member.dto.response;

import java.time.LocalDateTime;

import org.sopt.member.domain.Member;

public record MemberResponse(
	Long id,
	String name,
	String email,
	String role,
	boolean withdrawn,
	LocalDateTime createdAt
) {

	public static MemberResponse from(Member member) {
		return new MemberResponse(
			member.getId(),
			member.getName(),
			member.getEmail(),
			member.getRole().name(),
			member.isDeleted(),
			member.getCreatedAt()
		);
	}
}
