package com.company.githubreport.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GitHubAuthService {

    private final String token;

    public GitHubAuthService(@Value("${github.token}") String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
