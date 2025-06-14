# Spring Boot Backend User API

A RESTful API for managing user data built with Spring Boot. This application loads user data from an external source (dummyjson.com) and provides endpoints to search, filter, and retrieve user information.

## Features

- Load user data from external API into in-memory H2 database
- REST API endpoints for user dataaccess
- Comprehensive logging with SLF4J and Logback
- Exception handling for all API endpoints
- Bean validation for data integrity
- Externalized configuration for API and database settings
- Unit and integration tests

## Tech Stack

- **Spring Boot 2.7.18**: Framework for building the application
- **Spring Data JPA**: For database access and ORM functionality
- **H2 Database**: In-memory database for data storage
- **Lombok**: To reduce boilerplate code
- **Jackson**: JSON processing
- **Spring AOP**: For logging method execution times
- **Spring Validation**: For input validation

## Architecture

The application follows a layered architecture:

- **Controllers**: Handle HTTP requests and responses
- **Services**: Implement business logic
- **Repositories**: Handle data access
- **Entities/Models**: Represent database entities
- **DTOs**: Data Transfer Objects for API responses/requests
- **Mappers**: Convert between Entities and DTOs
- **Configuration**: Application setup and externalized configuration

## API Endpoints

| Method | Endpoint          | Description                         | Parameters                  |
|--------|-------------------|-------------------------------------|----------------------------|
| GET    | `/api/users`      | Get all users                       | None                       |
| GET    | `/api/users/{id}` | Get user by ID                      | `id`: User's ID            |
| GET    | `/api/users/email`| Get user by email address           | `email`: User's email      |
| GET    | `/api/users/search`| Search users by keyword            | `q`: Search term           |
| GET    | `/api/health`     | Get system health and config status | None                       |

## Configuration Properties

The application's behavior can be configured through properties in `application.properties`:

### API Configuration

- `app.api.users-url`: External API URL for fetching user data (default: https://dummyjson.com/users)
- `app.api.connection-timeout`: Connection timeout in milliseconds (default: 5000)
- `app.api.read-timeout`: Read timeout in milliseconds (default: 10000)

### Database Configuration

- `app.database.autoload-data`: Enable/disable automatic data loading on startup (default: true)
- `app.database.max-records`: Maximum number of records to load from the API (default: 100)

## Logging

The application has detailed logging using SLF4J with the following features:

- Request/response logging via custom interceptor
- Method execution time logging via AOP
- Different log levels for different packages
- File-based logging with rotation
- Console logging with colored output

Log files are stored in the `logs` directory with the filename `backend-application.log`.

## Getting Started

### Prerequisites

- JDK 11 or higher
- Maven 3.6 or higher

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application with Maven:


mvn spring-boot:run

The application will start on port 8080 by default. The H2 console is available at `http://localhost:8080/h2-console`.

### Running Tests

To run the tests:


mvn test


## Building for Production

To build the application for production:

mvn clean package

This will generate a JAR file in the `target` directory.