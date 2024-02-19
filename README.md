# Transferz development challenge
## By Tomas Sirio

---

## Getting Started

### Prerequisites

The following items should be installed and available in your system:

* JDK 17
* Maven 3.8+
* Docker 20.10+
* Docker Compose 1.27+
* PostgreSQL 

### Building the Project

Build the project using Maven:

```bash
./mvnw clean package
```

#### Docker Build

Build Docker image:

```bash 
docker build -t backend-app .
```

### Running the project

To run the project, you can use Maven's Wrapper to run the self-contained Spring-boot application:

```bash
./mvnw spring-boot:run 
```

#### Docker-compose Run

Here's how you can start the Spring Boot app with Docker Compose:

1. Define your environment variables in `.env` file:
2. Run Docker Compose:

```bash
docker-compose up
```

This will start all services defined in `docker-compose.yml`. The application will be reachable at `http://localhost:8080`.

#### Swagger-UI

Additionally, a Swagger interface was configured within the project, and it's accessible at http://localhost:8080/swagger-ui/index.html#/

---

### Configuration Properties

To change the application's configuration like `flight.maxPassengers`, you can do so by updating the corresponding environment variable in the .env file or Docker Compose file:

```bash 
FLIGHT_MAXPASSENGERS=200 docker-compose up -d --force-recreate
```

Else you can do it via Spring-boot by running the application with a different value:

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--flight.maxPassengers=200"
```

### Testing Endpoints

We provide a bash script to test the major functionalities of the service. You will need a bash shell to run it. Here's how:

1. Ensure you have the required permissions to run the script. You can add these permissions on a Unix-based system using:

    ```bash
    chmod +x src/main/resources/test_endpoints.sh
    ```

2. Run the script using:

    ```bash
    ./src/main/resources/test_endpoints.sh
    ```

Remember that the script requires the service to be up and running, so make sure to start the service before executing the script.

---

## 📞 Contact

For any questions or clarifications, reach out to the project owner at [tomassirio@gmail.com](mailto:tomassirio@gmail.com).
