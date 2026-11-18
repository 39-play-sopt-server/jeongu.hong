package org.sopt.global.jwt;

import lombok.Getter;

@Getter
public enum TokenType {
	ACCESS("access"),
	REFRESH("refresh");

	public static final String CLAIM_NAME = "type";

	private final String value;

	TokenType(String value) {
		this.value = value;
	}

	public boolean matches(String value) {
		return this.value.equals(value);
	}
}
