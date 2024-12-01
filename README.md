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


## API Documentation
The API documentation is available at 

### API Usage
1. Create an event
```shell
curl --request POST 'http://localhost:8080/events/create?name=Pre%20Christmas%20Carols&description=Christmas%20Carols%20at%20Phoenix%20MarketCity&event_start_date=2024-12-26&event_end_date=2024-12-28&total_tickets=23&available_tickets=23&ticket_price=30.56&created_by=AD001'```
```

