package com.company.githubreport.service;

import com.company.githubreport.model.AccessReport;
import com.company.githubreport.model.Repository;
import com.company.githubreport.model.UserAccess;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final RepositoryService repositoryService;
    private final UserAccessService userAccessService;

    public ReportService(RepositoryService repositoryService, UserAccessService userAccessService) {
        this.repositoryService = repositoryService;
        this.userAccessService = userAccessService;
    }

    public AccessReport buildAccessReport(String org) {
        List<String> repoNames = repositoryService.listRepositoryNames(org);
        Map<String, List<String>> usersByRepo = userAccessService.getUsersByRepoList(org, repoNames);
        List<Repository> repositories = repoNames.stream()
                .map(repoName -> new Repository(repoName, usersByRepo.getOrDefault(repoName, Collections.emptyList())))
                .collect(Collectors.toList());
        return new AccessReport(org, repositories);
    }
}
