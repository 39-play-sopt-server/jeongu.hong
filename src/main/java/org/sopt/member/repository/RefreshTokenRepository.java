package org.sopt.member.repository;

import java.util.Optional;

import org.sopt.member.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByMemberId(Long memberId);

	void deleteByMemberId(Long memberId);
}
