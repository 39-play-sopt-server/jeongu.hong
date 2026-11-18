package org.sopt.global.auth.token;

import java.time.Instant;

public interface TokenBlacklist {

	void add(String tokenId, Instant expiresAt);

	boolean contains(String tokenId);
}
