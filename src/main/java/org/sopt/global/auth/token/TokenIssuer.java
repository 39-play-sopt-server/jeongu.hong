package org.sopt.global.auth.token;

import org.sopt.global.auth.Role;

public interface TokenIssuer {

	String issueAccessToken(Long memberId, Role role);

	String issueRefreshToken(Long memberId);
}
