# Deployment Guide: Java Test App for Cloud Foundry

This guide explains how to deploy the Java test app to Cloud Foundry.

## Prerequisites

1. Cloud Foundry CLI installed
2. Access to a Cloud Foundry environment
3. Java 17 or higher installed locally
4. Maven 3.6 or higher installed locally

## Files Included

- `src/main/java/com/example/app/JavaTestApplication.java`: Main Spring Boot application
- `src/main/java/com/example/app/controller/MainController.java`: Web controller for handling requests
- `src/main/java/com/example/app/service/DataService.java`: Service for generating histogram data and system info
- `src/main/resources/templates/index.html`: Thymeleaf template for the web UI
- `src/main/resources/application.properties`: Spring Boot configuration
- `pom.xml`: Maven project configuration with dependencies
- `manifest.yml`: Cloud Foundry deployment configuration

## Building the Application

Before deploying, you need to build the application:

```bash
mvn clean package
```

This will create a JAR file in the `target/` directory.

## Deployment Steps

1. Clone or download this repository to your local machine
2. Open a terminal and navigate to the project directory
3. Build the application:
   ```bash
   mvn clean package
   ```
4. Log in to your Cloud Foundry environment:
   ```bash
   cf login -a <API_ENDPOINT> -o <ORG> -s <SPACE>
   ```
5. Deploy the application:
   ```bash
   cf push
   ```
6. Once deployment is complete, you can access your application at the provided URL

## Application Features

This Java application provides similar functionality to the R template:

- **Interactive Histogram**: Generate and display histograms with configurable bin counts (1-50)
- **Data Visualization**: Uses Chart.js for interactive charts
- **System Information**: Displays Java runtime, OS, memory, and other system details
- **Responsive UI**: Bootstrap-based interface that works on desktop and mobile
- **Health Monitoring**: Spring Boot Actuator endpoints for health checks

## Customizing the Application

### Modifying the UI
- Edit `src/main/resources/templates/index.html` to change the web interface
- Modify `src/main/java/com/example/app/controller/MainController.java` to add new endpoints

### Changing Data Generation
- Update `src/main/java/com/example/app/service/DataService.java` to modify histogram generation or system info

### Configuration
- Adjust memory and instance settings in `manifest.yml` based on your requirements
- Modify `src/main/resources/application.properties` for Spring Boot settings

### Dependencies
- Add new dependencies in `pom.xml` as needed
- Run `mvn clean package` after adding dependencies

## Testing

Run the included tests:
```bash
mvn test
```

Run the application locally for testing:
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`

## Troubleshooting

If you encounter issues during deployment:

1. Check the logs:
   ```bash
   cf logs java-test-app --recent
   ```

2. Verify that the Java buildpack is available:
   ```bash
   cf buildpacks
   ```

3. Make sure the JAR file was built successfully:
   ```bash
   ls -la target/java-test-app-1.0.0.jar
   ```

4. Check application health:
   ```bash
   cf app java-test-app
   ```

5. Access health endpoints (if app is running):
   - `<app-url>/actuator/health`
   - `<app-url>/actuator/info`

## Production Considerations

This is a test application. For production use, consider adding:

- **Security**: Add Spring Security for authentication and authorization
- **Database**: Integrate with a database for persistent storage
- **Monitoring**: Enhanced logging and monitoring with tools like Micrometer
- **Error Handling**: Comprehensive error handling and user-friendly error pages
- **API Documentation**: Add Swagger/OpenAPI documentation
- **Performance**: Connection pooling, caching, and performance optimization
- **Configuration Management**: Externalized configuration with Spring Cloud Config

## Technology Stack

- **Java 17**: Programming language
- **Spring Boot 3.2.1**: Application framework
- **Spring Web**: Web framework and REST APIs
- **Thymeleaf**: Server-side templating engine
- **Spring Boot Actuator**: Health monitoring and metrics
- **Chart.js**: Client-side charting library
- **Bootstrap 5**: CSS framework for responsive design
- **Maven**: Build tool and dependency management
- **JUnit 5**: Testing framework