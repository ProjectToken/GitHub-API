# GitHub-API

##  How to Run the Project

# 1. Create Organization 

### 2. Configure GitHub Token

Set your GitHub Personal Access Token as an environment variable:

## run main class from IDE

### 3. Access the application

```
http://localhost:8080
```

## Authentication Configuration

The application uses a **GitHub Personal Access Token (PAT)** for authentication.

* Token is passed in request headers:

```http
Authorization: Bearer <your_token>
```

* Required permissions:

  * `repo`
  * `read:org`

In code:

```java
headers.setBearerAuth(token);
```

The token is securely read from environment variables:

```java
String token = System.getenv("GITHUB_TOKEN");
```

---

##  API Endpoint

### GET Report

```http
GET /api/report/{organization}
```

### Example:

```http
GET http://localhost:8080/api/report/ProjectToken
```

---

##  Sample Response

```json
{
  "organization": "ProjectToken",
  "repositories": [
    {
      "repoName": "GitHub-API",
      "users": ["pritee2705"]
    }
  ]
}
```


##  Assumptions & Design Decisions

* Only **repository collaborators** are considered for access
* Token-based authentication is used instead of OAuth for simplicity
* Public repositories can be accessed without token, but collaborators API requires authentication
* Error handling is minimal (can be improved for production use)
* Response is simplified for clarity (only repo name and users)

---

