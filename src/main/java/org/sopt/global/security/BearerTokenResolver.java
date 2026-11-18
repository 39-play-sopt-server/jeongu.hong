package org.sopt.global.security;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class BearerTokenResolver {

	private static final String BEARER_PREFIX = "Bearer ";

	public Optional<String> resolve(HttpServletRequest request) {
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (header != null && header.startsWith(BEARER_PREFIX)) {
			return Optional.of(header.substring(BEARER_PREFIX.length()));
		}
		return Optional.empty();
	}
}