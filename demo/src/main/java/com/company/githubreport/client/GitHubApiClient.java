package com.company.githubreport.client;

import com.company.githubreport.service.GitHubAuthService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GitHubApiClient {

    private final RestTemplate restTemplate;
    private final GitHubAuthService authService;

    public GitHubApiClient(GitHubAuthService authService) {
        this.restTemplate = new RestTemplate();
        this.authService = authService;
    }

    private HttpEntity<Void> createEntity() {
        HttpHeaders headers = new HttpHeaders();
        String token = authService.getToken();
        if (token != null && !token.isBlank()) {
            headers.setBearerAuth(token);
        }
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(headers);
    }

    public List<Map<String, Object>> getRepositories(String org) {
        List<Map<String, Object>> allRepos = new ArrayList<>();
        String url = "https://api.github.com/orgs/" + org + "/repos?per_page=100";
        int page = 1;
        while (url != null) {
            try {
                ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, createEntity(), Object.class);
                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new RuntimeException("GitHub API error: " + response.getStatusCode() + " for " + url);
                }
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> repos = (List<Map<String, Object>>) response.getBody();
                if (repos != null) {
                    allRepos.addAll(repos);
                }
                url = getNextPageUrl(response.getHeaders());
                page++;
            } catch (RestClientException e) {
                throw new RuntimeException("Failed to fetch repositories for org: " + org, e);
            }
        }
        return allRepos;
    }

    public List<Map<String, Object>> getCollaborators(String org, String repo) {
        List<Map<String, Object>> allCollabs = new ArrayList<>();
        String url = "https://api.github.com/repos/" + org + "/" + repo + "/collaborators?per_page=100";
        while (url != null) {
            try {
                ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, createEntity(), Object.class);
                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new RuntimeException("GitHub API error: " + response.getStatusCode() + " for " + url);
                }
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> collabs = (List<Map<String, Object>>) response.getBody();
                if (collabs != null) {
                    allCollabs.addAll(collabs);
                }
                url = getNextPageUrl(response.getHeaders());
            } catch (RestClientException e) {
                throw new RuntimeException("Failed to fetch collaborators for repo: " + org + "/" + repo, e);
            }
        }
        return allCollabs;
    }

    private String getNextPageUrl(HttpHeaders headers) {
        String link = headers.getFirst("Link");
        if (link != null) {
            String[] links = link.split(",");
            for (String l : links) {
                if (l.contains("rel=\"next\"")) {
                    int start = l.indexOf('<') + 1;
                    int end = l.indexOf('>');
                    if (start > 0 && end > start) {
                        return l.substring(start, end);
                    }
                }
            }
        }
        return null;
    }
}
