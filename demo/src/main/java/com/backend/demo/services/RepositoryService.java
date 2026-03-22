package com.backend.demo.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RepositoryService {

    private final GitHubApiClient client;

    public RepositoryService(GitHubApiClient client) {
        this.client = client;
    }

    public List<String> getRepositoryNames(String org) {
        List<Map<String, Object>> repos = client.getRepositories(org);
        return repos.stream()
                .map(repo -> (String) repo.get("name"))
                .toList();
    }
}

