package com.company.githubreport.service;

import com.company.githubreport.client.GitHubApiClient;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserAccessService {

    private final GitHubApiClient client;

    public UserAccessService(GitHubApiClient client) {
        this.client = client;
    }

    public List<String> getAccessByRepo(String org, String repo) {
        List<Map<String, Object>> collaborators = client.getCollaborators(org, repo);
        List<String> logins = new ArrayList<>();
        for (Map<String, Object> collaborator : collaborators) {
            String login = (String) collaborator.get("login");
            if (login != null && !login.isBlank()) {
                logins.add(login);
            }
        }
        return logins;
    }

    public Map<String, List<String>> getUsersByRepoList(String org, List<String> repos) {
        Map<String, List<String>> result = new HashMap<>();
        for (String repo : repos) {
            result.put(repo, getAccessByRepo(org, repo));
        }
        return result;
    }
}
