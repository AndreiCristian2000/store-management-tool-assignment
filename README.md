# Store Management Tool

A simple Spring Boot application for managing a store's products.  
This project is backend-only, built with Maven, and includes security, logging, and unit tests.  
The application structure is divided into model, service, repository, controller, exception, and config packages.

## Technical Details

- Add, retrieve, and update products
- Role-based access control using Spring Security
- Basic authentication with in-memory users
- REST endpoints secured based on two user roles (`ADMIN`, `USER`)
- Global error handling using RestControllerAdvice
- Logging via SLF4J
- Unit and controller tests using JUnit 5 and Mockito
- Credentials must be sent using the `Authorization: Basic ...` header.

## API Endpoints

| HTTP Method | Endpoint               | Description                 | Roles Allowed      |
|-------------|------------------------|-----------------------------|--------------------|
| GET         | `/products`            | Get all products            | `USER`, `ADMIN`    |
| GET         | `/products/{id}`       | Get product by ID           | `USER`, `ADMIN`    |
| POST        | `/products`            | Add new product             | `ADMIN`            |
| PUT         | `/products/{id}/price` | Update product price        | `ADMIN`            |
