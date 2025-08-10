# Municipality Complaint Management System

A microservice-based complaint management system for municipalities built with Spring Boot, featuring service discovery with Eureka, JWT authentication, and role-based access control.

## System Architecture

The system consists of three main components:

1. **Eureka Server** (Port 8761) - Service Discovery Server
2. **User Service** (Port 8081) - Authentication, authorization, and user management
3. **Complaint Service** (Port 8082) - Complaint and comment management

## Features

### Citizen Capabilities
- File complaints with categories (WATER, SANITATION, ROADS)
- View own complaints
- Add comments to own complaints

### Staff/Admin Capabilities
- View all complaints
- Assign complaints to relevant departments
- Update complaint status (PENDING, IN_PROGRESS, RESOLVED)
- Add comments to any complaint

### Admin-Only Capabilities
- Delete complaints

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Cloud Netflix Eureka** - Service Discovery
- **Spring Security** - JWT Authentication & Authorization
- **Spring Data JPA** - Database Operations
- **MySQL** - Database
- **Maven** - Build Tool

## Database Schema

### Complaints Table
```sql
CREATE TABLE complaints (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(50) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    assigned_department VARCHAR(100),
    created_by BIGINT NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Comments Table
```sql
CREATE TABLE comments (
    comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    complaint_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    comment_text VARCHAR(1000) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (complaint_id) REFERENCES complaints(id)
);
```

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

### Database Setup
1. Create MySQL database:
```sql
CREATE DATABASE municipal_base;
```

2. Update database credentials in `complaint-service/src/main/resources/application.yml`

### Running the Services

#### 1. Start Eureka Server
```bash
cd eureka-server
mvn spring-boot:run
```
Access Eureka Dashboard: http://localhost:8761

#### 2. Start User Service (if available)
```bash
cd user-service
mvn spring-boot:run
```

#### 3. Start Complaint Service
```bash
cd complaint-service
mvn spring-boot:run
```

## API Endpoints

### Citizen Endpoints (Requires CITIZEN role)

#### File a Complaint
```http
POST /citizen/complaints
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
    "category": "WATER",
    "description": "Water pipe burst on Main Street"
}
```

#### Get Own Complaints
```http
GET /citizen/complaints
Authorization: Bearer <jwt-token>
```

#### Add Comment to Own Complaint
```http
POST /citizen/complaints/{id}/comments
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
    "commentText": "This is urgent, please address soon"
}
```

### Staff Endpoints (Requires STAFF or ADMIN role)

#### Get All Complaints
```http
GET /staff/complaints
Authorization: Bearer <jwt-token>
```

#### Assign Department
```http
PUT /staff/complaints/{id}/assign
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
    "assignedDepartment": "Water Department"
}
```

#### Update Status
```http
PUT /staff/complaints/{id}/status
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
    "status": "IN_PROGRESS"
}
```

#### Add Comment to Any Complaint
```http
POST /staff/complaints/{id}/comments
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
    "commentText": "Working on this issue"
}
```

### Admin Endpoints (Requires ADMIN role)

#### Delete Complaint
```http
DELETE /admin/complaints/{id}
Authorization: Bearer <jwt-token>
```

## JWT Token Format

The JWT token should contain:
```json
{
    "sub": "username",
    "userId": 123,
    "roles": ["CITIZEN"],
    "exp": 1234567890
}
```

## Error Handling

The API returns standard HTTP status codes:
- `200` - Success
- `201` - Created
- `400` - Bad Request (validation errors)
- `401` - Unauthorized
- `403` - Forbidden
- `404` - Not Found
- `500` - Internal Server Error

## Health Check

Check service health:
```http
GET /health
```

Response:
```json
{
    "status": "UP",
    "service": "complaint-service"
}
```

## Project Structure

```
complaint-service/
├── src/main/java/com/municipality/complaintservice/
│   ├── config/          # Configuration classes
│   ├── controller/      # REST controllers
│   ├── dto/            # Data Transfer Objects
│   ├── entity/         # JPA entities
│   ├── exception/      # Exception handlers
│   ├── repository/     # Data repositories
│   ├── security/       # Security configuration
│   └── service/        # Business logic
└── src/main/resources/
    └── application.yml  # Application configuration
```

## Development Notes

- No Lombok used - all getters, setters, constructors, and toString() methods are written manually
- JWT validation is handled by the Complaint Service
- Inter-service communication uses Eureka service discovery
- Database tables are auto-created via JPA with `ddl-auto: update`