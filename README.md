## Event Ticketing System

This is a simple event ticketing system that is built using Kotlin and Micronaut.
The database is PostgreSQL and the ORM is JPA.

Java 21 is required to run this application.

### How to run the application
1. Clone the repository
2. Spin up the database using the following command
```shell
docker-compose -f docker-compose.yml up -d
```
2. Run the following command to start the application
```shell
./gradlew run
```
3. The application will be running on `http://localhost:8080`

