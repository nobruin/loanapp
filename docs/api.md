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

## Authentication & User Management
- **Authentication is handled externally by Okta via OAuth2.** This backend does not provide endpoints for password-based login or registration.
- User profile-related endpoints (such as `/users/profile`) may exist for local profile information, but not for credential or session management.

## Example Public/Protected Endpoints
- Public or protected endpoints will require a valid OAuth2 token from Okta.
- Any user registration or login should be performed via Okta's identity interface, not via this backend API.

## Security
- CSRF protection is disabled for API endpoints
- Authentication and authorization are delegated to Okta using OAuth2/JWT tokens
- Spring Security is configured for resource server mode, validating Okta-issued tokens

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
