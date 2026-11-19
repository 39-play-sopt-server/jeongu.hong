package org.sopt.global.oauth;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.sopt.global.code.AuthErrorCode;
import org.sopt.global.error.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class OAuthClients {

	private final Map<SocialProvider, OAuthClient> clients = new EnumMap<>(SocialProvider.class);

	public OAuthClients(List<OAuthClient> clients) {
		for (OAuthClient client : clients) {
			if (this.clients.putIfAbsent(client.provider(), client) != null) {
				throw new IllegalStateException("중복된 OAuthClient: " + client.provider());
			}
		}
	}

	public OAuthClient get(SocialProvider provider) {
		OAuthClient client = clients.get(provider);
		if (client == null) {
			throw new BusinessException(AuthErrorCode.UNSUPPORTED_SOCIAL_PROVIDER);
		}
		return client;
	}
}
