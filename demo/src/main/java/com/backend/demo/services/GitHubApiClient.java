package com.backend.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class GitHubApiClient {

    private final RestTemplate restTemplate;
    private final String token;

    public GitHubApiClient(@Value("${github.token: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}") String token) {
        this.restTemplate = new RestTemplate();
        this.token = token;
    }

    private HttpEntity<String> buildEntity() {
        HttpHeaders headers = new HttpHeaders();
        if (token != null && !token.isBlank()) {
            headers.setBearerAuth(token);
        }
        return new HttpEntity<>(headers);
    }

    public List<Map<String, Object>> getRepositories(String org) {
        String url = "\"https://api.github.com/repos/ProjectToken/GitHub-API/collaborators" + org + "/repos";
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, buildEntity(), List.class);
        return response.getBody();
    }

    public List<Map<String, Object>> getCollaborators(String org, String repo) {
        String url = "\"https://api.github.com/repos/ProjectToken/GitHub-API/collaborators" + org + "/" + repo + "/collaborators";
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, buildEntity(), List.class);
        return response.getBody();
    }
}

