# Hotel Management API

This Java-based microservice API manages hotel bookings with endpoints for storing bookings, finding available rooms by date, and retrieving bookings for guests.

## Features

- **Store Booking**: Endpoint to store a booking with guest name, room number, and date.
- **Find Available Rooms**: Endpoint to find available rooms for a given date.
- **Find Bookings for Guest**: Endpoint to find all bookings for a specific guest.

## Prerequisites

- Java Development Kit (JDK) version 17 installed.
- Maven for building the project.

## Installation

1. Clone the repository:
git clone https://github.com/dillibabuatn/hotel-management-api.git

2. Navigate to the project directory:
cd hotel-management-api


3. Build the project using Maven:
mvn clean install

## Usage

1. Run the main class `HotelBookingManagerController` to start the HTTP server:
java -cp target/classes com.hotelbooking.controller.HotelBookingManagerController
The server starts on port 8080 by default.

2. Use cURL or a tool like Postman to make HTTP requests to the API endpoints:

- **Store Booking**:
  - **Method**: `POST`
  - **Endpoint**: `/bookingRoom`
  - **Payload**: `guestName=Guest1&roomNumber=1&date=2024-06-10`
  - **Example**:
    ```
    curl -X POST -d "guestName=Guest1&roomNumber=1&date=2024-06-10" http://localhost:8080/bookingRoom
    ```

- **Find Available Rooms**:
  - **Method**: `GET`
  - **Endpoint**: `/findAvailablerooms`
  - **Query Parameter**: `date={date}`
  - **Example**:
    ```
    curl http://localhost:8080/findAvailablerooms?date=2024-06-10
    ```

- **Find Bookings for Guest**:
  - **Method**: `GET`
  - **Endpoint**: `/findBookingByGuest`
  - **Query Parameter**: `guestName={guestName}`
  - **Example**:
    ```
    curl http://localhost:8080/findBookingByGuest?guestName=Guest2
    ```

## Example Requests

- **Store Booking**:
 curl -X POST -d "guestName=Guest1&roomNumber=1&date=2024-06-10" http://localhost:8080/bookingRoom


- **Find Available Rooms**:
curl http://localhost:8080/findAvailablerooms?date=2024-06-10



- **Find Bookings for Guest**:
curl http://localhost:8080/findBookingByGuest?guestName=Guest2

