# Apesech Hotel Restaurant Backend

This repository contains the backend code for the Apesech Hotel Restaurant project. The backend is built using Spring Boot and provides RESTful APIs to manage hotel and restaurant operations such as bookings, menu items, and customer services.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Database Configuration](#database-configuration)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Introduction

The Apesech Hotel Restaurant backend is a Spring Boot application that handles the core business logic and database interactions for the hotel and restaurant management system. It exposes a set of RESTful APIs for the frontend to interact with.

## Features

- **Booking Management**: APIs to create, update, and retrieve room and table bookings.
- **Menu Management**: APIs to manage restaurant menu items.
- **Customer Service**: APIs to handle customer service requests and feedback.
- **Secure Data Handling**: Ensures secure data storage and access.

## Project Structure

```
/apesech-hotel-restaurant-backend
├── .gitignore                       # Git ignore file
├── HELP.md                          # Project help documentation
├── mvnw                             # Maven wrapper script for Unix
├── mvnw.cmd                         # Maven wrapper script for Windows
├── pom.xml                          # Maven project file
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/apesech/hotel/   # Main application package
│   │   │       ├── ApesechHotelRestaurantApplication.java  # Main application class
│   │   │       ├── controller/      # REST API controllers
│   │   │       ├── model/           # Entity classes
│   │   │       ├── repository/      # Data repository interfaces
│   │   │       └── service/         # Business logic services
│   │   └── resources/
│   │       ├── application.properties  # Application configuration
│   │       └── data.sql              # Sample data for initial setup
│   └── test/
│       └── java/                     # Test packages
│           └── com/apesech/hotel/    # Test classes
└── target/                           # Build output directory
```

## Getting Started

### Prerequisites

To run this project locally, you’ll need:

- **Java JDK 11** or later
- **Maven 3.6+**
- **MySQL** or **H2** database
- **Git**

### Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/your-username/apesech-hotel-restaurant-backend.git
   ```

2. **Navigate to the project directory**:

   ```bash
   cd apesech-hotel-restaurant-backend
   ```

3. **Build the project**:

   ```bash
   ./mvnw clean install
   ```

4. **Run the application**:

   ```bash
   ./mvnw spring-boot:run
   ```

   The application will start on `http://localhost:8080`.

## API Endpoints

Here are some of the key API endpoints provided by the backend:

### Booking Management

- **GET /api/bookings/{date}**: Retrieve bookings for a specific date.
- **POST /api/bookings**: Create a new booking.

### Menu Management

- **GET /api/menu**: Retrieve all menu items.
- **POST /api/menu**: Add a new menu item.

### Example Code Snippet

**Booking Controller:**

```java
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/{date}")
    public List<Booking> getBookings(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return bookingService.getBookingsByDate(date);
    }

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.saveBooking(booking);
    }
}
```

## Database Configuration

The application can be configured to use either an H2 in-memory database for development or a MySQL database for production.

### H2 Database (Default for Development)

The H2 database is configured by default in the `application.properties` file:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

### MySQL Database

To switch to MySQL, update the `application.properties` with your MySQL configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/apesech_hotel
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL5Dialect
```

## Usage

### Running the Application

After starting the application, you can access the RESTful API at `http://localhost:8080`.

Use tools like **Postman** or **cURL** to test the API endpoints.

### Example Request

**Creating a New Booking:**

```bash
curl -X POST http://localhost:8080/api/bookings \
-H "Content-Type: application/json" \
-d '{
  "customerName": "John Doe",
  "bookingDate": "2024-08-01",
  "roomType": "Deluxe",
  "tableNumber": "5"
}'
```

## Contributing

Contributions are welcome! If you’d like to contribute, please fork the repository and submit a pull request.
