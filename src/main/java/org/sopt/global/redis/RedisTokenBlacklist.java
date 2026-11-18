package org.sopt.global.redis;

import java.time.Duration;
import java.time.Instant;

import org.sopt.global.auth.token.TokenBlacklist;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisTokenBlacklist implements TokenBlacklist {

	private static final String KEY_PREFIX = "blacklist:";
	private static final String VALUE = "1";

	private final StringRedisTemplate redisTemplate;

	@Override
	public void add(String tokenId, Instant expiresAt) {
		Duration ttl = Duration.between(Instant.now(), expiresAt);
		if (!ttl.isPositive()) {
			return;
		}
		redisTemplate.opsForValue().set(key(tokenId), VALUE, ttl);
	}

	@Override
	public boolean contains(String tokenId) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(key(tokenId)));
	}

	private String key(String tokenId) {
		return KEY_PREFIX + tokenId;
	}
}
