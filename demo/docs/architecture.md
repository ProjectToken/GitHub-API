# Architecture

- REST controller: `ReportController`
- Service layer:
  - `ReportService`
  - `RepositoryService`
  - `UserAccessService`
  - `GitHubAuthService`
- HTTP client: `GitHubApiClient`
- Domain model: `Repository`, `UserAccess`, `AccessReport`
- Security: basic auth with `SecurityConfig`
