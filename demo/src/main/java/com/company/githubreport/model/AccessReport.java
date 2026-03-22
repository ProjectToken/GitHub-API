package com.company.githubreport.model;

import java.util.List;

public record AccessReport(String organization, List<Repository> repositories) { }
