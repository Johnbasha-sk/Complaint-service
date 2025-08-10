package com.municipal.complaint.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserServiceClient {

    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDetailsResponse validateTokenAndGetUser(String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, bearerToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        // Adjust the path if your User Service exposes a different validation endpoint
        ResponseEntity<UserDetailsResponse> response = restTemplate.exchange(
                "http://user-service/auth/validate", HttpMethod.GET, entity, UserDetailsResponse.class);
        return response.getBody();
    }
}