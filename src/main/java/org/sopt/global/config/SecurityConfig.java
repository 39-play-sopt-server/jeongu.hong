package org.sopt.global.config;

import org.sopt.global.auth.Role;
import org.sopt.global.auth.token.TokenBlacklist;
import org.sopt.global.auth.web.BearerTokenResolver;
import org.sopt.global.auth.web.RestAccessDeniedHandler;
import org.sopt.global.auth.web.TokenAuthenticationFilter;
import org.sopt.global.auth.web.TokenAuthenticationEntryPoint;
import org.sopt.global.auth.token.TokenVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private static final String[] SWAGGER_URLS = {
		"/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**"
	};

	private static final String[] PUBLIC_GET_URLS = {
		"/api/v1/posts", "/api/v1/posts/*"
	};
	private static final String[] PUBLIC_POST_URLS = {
		"/api/v1/auth/signup", "/api/v1/auth/login", "/api/v1/auth/reissue"
	};

	private static final String ADMIN_URLS = "/api/v1/admin/**";

	private final TokenVerifier tokenVerifier;
	private final BearerTokenResolver bearerTokenResolver;
	private final TokenBlacklist tokenBlacklist;
	private final TokenAuthenticationEntryPoint authenticationEntryPoint;
	private final RestAccessDeniedHandler accessDeniedHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			// CSRF 비활성화, Authorization 헤더로 보내면 CSRF의 전제 조건이 없기 때문
			.csrf(AbstractHttpConfigurer::disable)
			// 폼 로그인, HTTP Basic 비활성화
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			// 세션 STATELESS
			.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			// WebConfig의 CORS 설정을 Security 필터 단계에서도 적용 (Preflight 401 방지)
			.cors(Customizer.withDefaults())
			// URL별 접근 권한 설정
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.GET, PUBLIC_GET_URLS).permitAll()
				.requestMatchers(HttpMethod.POST, PUBLIC_POST_URLS).permitAll()
				.requestMatchers(SWAGGER_URLS).permitAll()
				.requestMatchers(ADMIN_URLS).hasRole(Role.ADMIN.name())
				.anyRequest().authenticated())
			// 인증 실패(401)와 권한 부족(403) 응답
			.exceptionHandling(e -> e
				.authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler(accessDeniedHandler))
			// JWT 인증 필터 추가
			.addFilterBefore(new TokenAuthenticationFilter(tokenVerifier, bearerTokenResolver, tokenBlacklist),
				UsernamePasswordAuthenticationFilter.class)
			.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
