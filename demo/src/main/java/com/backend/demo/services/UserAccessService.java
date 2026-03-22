package com.backend.demo.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserAccessService {

    private final GitHubApiClient client;

    public UserAccessService(GitHubApiClient client) {
        this.client = client;
    }

    public Map<String, List<String>> getUserAccess(String org, List<String> repos) {
        Map<String, List<String>> accessMap = new HashMap<>();

        repos.parallelStream().forEach(repo -> {
            List<Map<String, Object>> collaborators = client.getCollaborators(org, repo);
            for (Map<String, Object> user : collaborators) {
                String login = (String) user.get("login");
                accessMap.computeIfAbsent(login, k -> new ArrayList<>()).add(repo);
            }
        });

        return accessMap;
    }
}

