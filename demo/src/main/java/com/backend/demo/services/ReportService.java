package com.backend.demo.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final RepositoryService repoService;
    private final UserAccessService accessService;

    public ReportService(RepositoryService repoService, UserAccessService accessService) {
        this.repoService = repoService;
        this.accessService = accessService;
    }

    public Map<String, List<String>> generateReport(String org) {
        List<String> repos = repoService.getRepositoryNames(org);
        return accessService.getUserAccess(org, repos);
    }
}

