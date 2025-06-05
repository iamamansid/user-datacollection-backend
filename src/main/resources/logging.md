# User Management API

This is a Spring Boot application that provides REST APIs to manage and search for users.

## Logging Features

The application includes comprehensive logging throughout the codebase:

- **Service Layer Logging**: Detailed logs for all service method calls and outcomes
- **Controller Layer Logging**: HTTP request/response logging with request IDs for tracking
- **Data Access Logging**: Database operation logging
- **Performance Monitoring**: Method execution time tracking via AOP
- **External API Logging**: Logging for external API calls
- **Global Exception Logging**: Centralized exception logging

### Log Levels

- `ERROR`: Errors that require immediate attention
- `WARN`: Warning situations that don't prevent the application from working
- `INFO`: General information about application flow
- `DEBUG`: Detailed debugging information (only included in development environments)
- `TRACE`: Most detailed logging (rarely used)

### Log Configuration

Logging is configured in `application.properties`:

```properties
# Logging Configuration
logging.level.root=INFO
logging.level.com.example.backend=DEBUG
logging.level.org.springframework.web=INFO
logging.level.org.hibernate=INFO
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
logging.file.name=logs/backend-application.log
logging.file.max-size=10MB
logging.file.max-history=30
```

### Log File Location

Log files are stored in the `logs` directory with automatic rotation when the file size reaches 10MB.
