package com.example.oauth2_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Map;

@SpringBootApplication
@EnableWebFluxSecurity
public class Oauth2ClientApplication {

	ReactiveOAuth2AuthorizedClientService authorizedClientService;

	Oauth2ClientApplication (ReactiveOAuth2AuthorizedClientService authorizedClientService) {
		this.authorizedClientService = authorizedClientService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Oauth2ClientApplication.class, args);
	}

	@Bean
	RouterFunction<ServerResponse> routes() {
		return RouterFunctions.route()
				.GET("/webflux", request -> ReactiveSecurityContextHolder.getContext()
						.map(SecurityContext::getAuthentication)
						.map(Authentication::getName)
						.flatMap(name -> authorizedClientService.loadAuthorizedClient("webflux", name).map(OAuth2AuthorizedClient::getAccessToken))
						.flatMap(ServerResponse.ok()::bodyValue)
				)
				.build();
	}
}

@RestController
class Oauth2ClientController {

	@GetMapping("/")
	public Map<String, Object> getAuthorized(@RegisteredOAuth2AuthorizedClient("spring") OAuth2AuthorizedClient authorizedClient) {
		return Map.of(
				"clientName", authorizedClient.getClientRegistration().getClientName(),
				"accessToken", authorizedClient.getAccessToken().getTokenValue()
		);
	}
}

