### Municipality Complaint Management - Eureka & Complaint Service

This workspace contains:
- `eureka-server` (port 8761): Spring Cloud Netflix Eureka Server
- `complaint-service` (port 8082): Complaint microservice

A separate `user-service` is assumed to exist, exposing a JWT validation endpoint. Adjust the path in `UserServiceClient` if needed.

#### Prerequisites
- Java 17+
- Maven 3.9+
- Docker (for MySQL via Docker Compose)

#### Run MySQL
```bash
docker compose -f ./docker-compose.yml up -d
```

#### Start Eureka Server
```bash
cd eureka-server && mvn spring-boot:run
```
Visit `http://localhost:8761`.

#### Start Complaint Service
```bash
cd complaint-service && mvn spring-boot:run
```

Ensure `user-service` is running and registered with Eureka as `user-service`, exposing `GET /auth/validate` which returns JSON like:
```json
{ "userId": 123, "roles": ["CITIZEN"] }
```

#### Endpoints
- Citizen
  - POST `/citizen/complaints`
  - GET `/citizen/complaints`
  - POST `/citizen/complaints/{id}/comments`
- Staff/Admin
  - GET `/staff/complaints`
  - PUT `/staff/complaints/{id}/assign`
  - PUT `/staff/complaints/{id}/status`
  - POST `/staff/complaints/{id}/comments`
- Admin
  - DELETE `/admin/complaints/{id}`

All endpoints require `Authorization: Bearer <jwt>`; the token is validated by `user-service` via Eureka.