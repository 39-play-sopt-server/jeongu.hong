package org.sopt.member.domain;

import org.sopt.global.domain.BaseEntity;
import org.sopt.global.oauth.SocialProvider;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(
	name = "uk_social_account_provider_provider_id",
	columnNames = {"provider", "provider_id"}))
public class SocialAccount extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private SocialProvider provider;

	@Column(name = "provider_id", nullable = false, length = 100)
	private String providerId;

	private SocialAccount(Member member, SocialProvider provider, String providerId) {
		this.member = member;
		this.provider = provider;
		this.providerId = providerId;
	}

	public static SocialAccount create(Member member, SocialProvider provider, String providerId) {
		return new SocialAccount(member, provider, providerId);
	}

	public void linkTo(Member member) {
		this.member = member;
	}
}
