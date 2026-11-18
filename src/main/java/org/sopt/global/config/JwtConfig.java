package org.sopt.global.config;

import javax.crypto.SecretKey;

import org.sopt.global.jwt.JwtProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtConfig {

	@Bean
	public SecretKey jwtSecretKey(JwtProperties jwtProperties) {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secret()));
	}

	@Bean
	public JwtParser jwtParser(SecretKey jwtSecretKey) {
		return Jwts.parser()
			.verifyWith(jwtSecretKey)
			.build();
	}
}