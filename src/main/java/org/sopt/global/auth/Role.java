package org.sopt.global.auth;

public enum Role {
	USER,
	ADMIN;

	private static final String AUTHORITY_PREFIX = "ROLE_";

	public String getAuthority() {
		return AUTHORITY_PREFIX + name();
	}
}
