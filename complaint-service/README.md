# Complaint Service

Spring Boot microservice for municipality complaint management.

## Tech Stack
- Spring Boot 3
- Spring Data JPA (MySQL)
- Spring Security (OAuth2 Resource Server for JWT)
- Maven

## Run locally

1. Set environment variables for MySQL:
   - `MYSQL_HOST` (default: `localhost`)
   - `MYSQL_PORT` (default: `3306`)
   - `MYSQL_USER` (default: `root`)
   - `MYSQL_PASSWORD` (default: `password`)

2. Configure JWT validation using one of:
   - `JWT_JWK_SET_URI` pointing to the User Service JWKs endpoint, or
   - `JWT_PUBLIC_KEY_LOCATION` classpath location (e.g., `classpath:public.pem`)

3. Build and run:

```bash
mvn -DskipTests package
java -jar target/complaint-service-0.0.1-SNAPSHOT.jar
```

Service runs on port `8082`.

## Roles and Access
- `/citizen/**`: `CITIZEN`
- `/staff/**`: `STAFF` or `ADMIN`
- `/admin/**`: `ADMIN`

JWT must include a claim `roles` with values like `["CITIZEN","STAFF","ADMIN"]`.

## Endpoints

- POST `/citizen/complaints` — file a complaint
- GET `/citizen/complaints` — list user's complaints
- POST `/citizen/complaints/{id}/comments` — add comment to own complaint

- GET `/staff/complaints` — list all complaints
- PUT `/staff/complaints/{id}/assign` — assign department
- PUT `/staff/complaints/{id}/status` — update status
- POST `/staff/complaints/{id}/comments` — add comment

## Database
Two tables will be created in `municipal_base` database:
- `complaints`
- `comments`

## Notes
- This service does not manage user registration or login. It validates JWTs issued by the User Service.
- `createdBy` is taken from `userId` claim if present, otherwise from JWT `sub`.