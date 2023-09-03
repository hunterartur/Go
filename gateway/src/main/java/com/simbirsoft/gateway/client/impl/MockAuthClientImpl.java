package com.simbirsoft.gateway.client.impl;

import com.simbirsoft.gateway.client.AuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "application.auth-provider", havingValue = "mock")
public class MockAuthClientImpl implements AuthClient {

    @Value("${application.services.authorization.url}")
    private String authUrl;

    private final RestTemplate restTemplate;

    @Override
    public String getVerifyingKey() {
        return restTemplate.getForObject(
                UriComponentsBuilder.fromUriString(authUrl)
                        .path("/verifying-key")
                        .build()
                        .toUri(),
                String.class
        );
    }
}
