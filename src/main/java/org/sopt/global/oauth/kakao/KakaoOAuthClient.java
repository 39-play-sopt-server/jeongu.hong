package org.sopt.global.oauth.kakao;

import java.time.Duration;

import org.sopt.global.code.AuthErrorCode;
import org.sopt.global.error.BusinessException;
import org.sopt.global.oauth.OAuthClient;
import org.sopt.global.oauth.OAuthUserInfo;
import org.sopt.global.oauth.SocialProvider;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KakaoOAuthClient implements OAuthClient {

	private static final String DEFAULT_NICKNAME = "카카오 사용자";

	private final RestClient restClient;
	private final KakaoOAuthProperties properties;

	public KakaoOAuthClient(RestClient.Builder restClientBuilder, KakaoOAuthProperties properties) {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(Duration.ofSeconds(3));
		requestFactory.setReadTimeout(Duration.ofSeconds(5));

		this.restClient = restClientBuilder.requestFactory(requestFactory).build();
		this.properties = properties;
	}

	@Override
	public SocialProvider provider() {
		return SocialProvider.KAKAO;
	}

	@Override
	public OAuthUserInfo fetchUserInfo(String authorizationCode) {
		try {
			String accessToken = requestAccessToken(authorizationCode);
			KakaoUserResponse user = requestUser(accessToken);
			return toUserInfo(user);
		} catch (HttpClientErrorException e) {
			log.warn("카카오 인증 실패 [{}] {}", e.getStatusCode(), e.getResponseBodyAsString());
			throw new BusinessException(AuthErrorCode.SOCIAL_LOGIN_FAILED);
		} catch (RestClientException e) {
			log.error("카카오 서버 통신 실패", e);
			throw new BusinessException(AuthErrorCode.SOCIAL_SERVER_ERROR);
		}
	}

	private String requestAccessToken(String authorizationCode) {
		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add("grant_type", "authorization_code");
		form.add("client_id", properties.clientId());
		form.add("client_secret", properties.clientSecret());
		form.add("redirect_uri", properties.redirectUri());
		form.add("code", authorizationCode);

		KakaoTokenResponse response = restClient.post()
			.uri(properties.tokenUri())
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.body(form)
			.retrieve()
			.body(KakaoTokenResponse.class);

		if (response == null || response.accessToken() == null) {
			throw new BusinessException(AuthErrorCode.SOCIAL_LOGIN_FAILED);
		}
		return response.accessToken();
	}

	private KakaoUserResponse requestUser(String accessToken) {
		KakaoUserResponse response = restClient.get()
			.uri(properties.userInfoUri())
			.headers(headers -> headers.setBearerAuth(accessToken))
			.retrieve()
			.body(KakaoUserResponse.class);

		if (response == null || response.id() == null) {
			throw new BusinessException(AuthErrorCode.SOCIAL_LOGIN_FAILED);
		}
		return response;
	}

	private OAuthUserInfo toUserInfo(KakaoUserResponse user) {
		return new OAuthUserInfo(SocialProvider.KAKAO, String.valueOf(user.id()), extractNickname(user));
	}

	private String extractNickname(KakaoUserResponse user) {
		if (user.kakaoAccount() == null
			|| user.kakaoAccount().profile() == null
			|| user.kakaoAccount().profile().nickname() == null) {
			return DEFAULT_NICKNAME;
		}
		return user.kakaoAccount().profile().nickname();
	}
}