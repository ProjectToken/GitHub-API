package com.company.githubreport.service;

import com.company.githubreport.client.GitHubApiClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepositoryService {

    private final GitHubApiClient client;

    public RepositoryService(GitHubApiClient client) {
        this.client = client;
    }

    public List<String> listRepositoryNames(String org) {
        return client.getRepositories(org).stream()
                .map(repo -> (String) repo.get("name"))
                .collect(Collectors.toList());
    }
}
