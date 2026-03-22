# API Specification

## GET /api/report/{org}

- Path parameters:
  - org (string) - GitHub organization name

- Response (200):
  ```json
  {
    "organization": "org",
    "repositories": [],
    "userAccess": {}
  }
  ```
