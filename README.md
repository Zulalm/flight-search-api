# Flight Search API
 The Flight Search API is a web service designed to provide users with access to information about flights, airports, and related data. It allows users to perform searches for available flights, get details about airports, and manage flight information.
 
## Endpoints
**Flight Search**

* GET /api/search/: Perform flight searches based on specified criteria.
  
**Flight Information**

* GET /api/flight/all_flights: Retrieve a list of all flights.
* POST /api/flight/add_new_flight: Add a new flight.
* PUT /api/flight/update_flight_destination: Update the destination of an existing flight.
* PUT /api/flight/update_flight_origin: Update the origin of an existing flight.
* PUT /api/flight/update_flight_arrival_date: Update the arrival date or time of an existing flight.
* PUT /api/flight/update_flight_departure_date: Update the departure date or time of an existing flight.
* DELETE /api/flight/delete_flight: Delete a flight.
  
**Airport Information**

* GET /api/airport/all_airports: Retrieve a list of all airports.
* GET /api/airport/airport: Get details about a specific airport.
* POST /api/airport/add_airport: Add a new airport.
* PUT /api/airport/update_airport_city: Update the city of an existing airport.
* DELETE /api/airport/delete_airport: Delete an airport.
  
**User Authentication**

* POST /api/auth/authenticate: Authenticate users and obtain an authentication token.
* POST /api/auth/register: Register a new user and obtain an authentication token.
  
## Authentication

* The API uses JWT (JSON Web Token) for authentication. Users need to include a valid JWT token in the Authorization header for protected endpoints.

## Technologies Used

* Spring Boot: The API is built using the Spring Boot framework.
* Swagger: API documentation is available using Swagger, allowing users to understand and interact with the endpoints easily.

  For detailed API documentation, visit the Swagger UI endpoint at /swagger-ui.html. It is also available on this [link](https://app.swaggerhub.com/apis/ZULALMOLLA_1/flight-search/v0)
