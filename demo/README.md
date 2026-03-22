# GitHub Access Report

Spring Boot application that generates a GitHub org access report via API.

## Run

```
./mvnw clean package
./mvnw spring-boot:run
```

## API

`GET /api/report/{org}`

Requires basic auth from application.yml.
