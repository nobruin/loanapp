# REST API Documentation

## Swagger / OpenAPI

Swagger UI (interactive API docs):
```
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:
```
http://localhost:8080/v3/api-docs
```

## Base Path
All API endpoints are prefixed with `/v1/auth`

## Authentication Endpoints

### Register User

Creates a new user account in the system.

**Endpoint:** `POST /v1/auth/register`

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "StrongPassword123!"
}
```

**Success Response (201/200):**
```json
{
  "id": "<uuid>",
  "email": "user@example.com"
}
```

**Error Responses:**
- `400 Bad Request` - Invalid request data
- `409 Conflict` - Email already exists

**Example cURL:**
```bash
curl -X POST http://localhost:8080/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "StrongPassword123!"
  }'
```

## Security

### Public Endpoints
- `/v1/auth/register` - User registration
- `/v1/auth/login` - User authentication (future implementation)

### Protected Endpoints
All other endpoints require authentication (future features).

### Security Configuration
- CSRF protection is disabled for API endpoints
- BCrypt password encoding is used
- Spring Security handles authentication and authorization

## Error Handling

The API uses centralized error handling through `GlobalExceptionHandler.kt`:

- **Validation Errors**: Returns 400 with validation details
- **Business Logic Errors**: Returns appropriate HTTP status codes
- **System Errors**: Returns 500 with generic error message

## Future Endpoints

The following endpoints are planned for future implementation:

### Loan Management
- `POST /v1/loans/apply` - Submit loan application
- `GET /v1/loans/{id}` - Get loan details
- `GET /v1/loans` - List user's loans

### Payment Management
- `POST /v1/payments/schedule` - Schedule payment
- `GET /v1/payments/{id}` - Get payment details
- `GET /v1/payments` - List user's payments

### User Management
- `GET /v1/users/profile` - Get user profile
- `PUT /v1/users/profile` - Update user profile
