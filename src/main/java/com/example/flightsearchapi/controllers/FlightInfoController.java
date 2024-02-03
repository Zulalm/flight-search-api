package com.example.flightsearchapi.controllers;

import com.example.flightsearchapi.dtos.flightInfoRequestDtos.*;
import com.example.flightsearchapi.models.Airport;
import com.example.flightsearchapi.models.FlightInfo;
import com.example.flightsearchapi.services.AirportService;
import com.example.flightsearchapi.services.FlightInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight")
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class FlightInfoController {
    @Autowired
    private FlightInfoService flightInfoService;
    @Autowired
    private AirportService airportService;

    @Operation(summary = "Get all flights", security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping("/all_flights")
    @ApiResponse(responseCode = "200", description = "List of all flights",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "text/plain"))
    public ResponseEntity<List<FlightInfo>> getAllFlights(){
        return new ResponseEntity<>(flightInfoService.getAllFlights(), HttpStatus.OK);
    }
    @Operation(summary = "Add a new flight", security = {@SecurityRequirement(name = "bearerAuth")})
    @PostMapping("/add_new_flight")
    @ApiResponse(responseCode = "201", description = "Flight added successfully",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Arrival and departure dates must be provided.",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "404", description = "Origin or destination airport not found",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "text/plain"))
    public ResponseEntity<Object> addNewFlight(@RequestBody AddFlightInfoRequestDto requestDto){
        Airport destination = airportService.getAirportById(requestDto.getDestinationAirportId());
        if(destination == null){
            return  new ResponseEntity<>("Destination airport not found.", HttpStatus.NOT_FOUND);
        }
        Airport origin = airportService.getAirportById(requestDto.getOriginAirportId());
        if(origin == null){
            return  new ResponseEntity<>("Origin airport not found.", HttpStatus.NOT_FOUND);
        }
        if(requestDto.getArrivalDate() == null || requestDto.getDepartureDate() == null || requestDto.getDepartureTime() == null || requestDto.getArrivalTime() == null){
            return  new ResponseEntity<>("Arrival and departure dates must be provided.", HttpStatus.BAD_REQUEST);
        }
        FlightInfo flightInfo = flightInfoService.addNewFlight(destination,origin,requestDto.getArrivalDate(), requestDto.getArrivalTime(), requestDto.getDepartureDate(),requestDto.getDepartureTime());
        return new ResponseEntity<>(flightInfo, HttpStatus.CREATED);
    }
    @Operation(summary = "Update flight destination", security = {@SecurityRequirement(name = "bearerAuth")})
    @PutMapping("/update_flight_destination")
    @ApiResponse(responseCode = "200", description = "Flight destination updated successfully",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "Flight info or destination airport not found",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "text/plain"))
    public ResponseEntity<Object> updateFlightDestination(@RequestBody UpdateFlightDestinationRequestDto requestDto){

        Airport destination = airportService.getAirportById(requestDto.getDestinationAirportId());
        if(destination == null){
            return  new ResponseEntity<>("Destination airport not found.", HttpStatus.NOT_FOUND);
        }
        FlightInfo flightInfo = flightInfoService.updateFlightDestination(requestDto.getFlightId(), destination);
        if(flightInfo == null){
            return  new ResponseEntity<>("Flight info not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(flightInfo, HttpStatus.OK);

    }
    @Operation(summary = "Update flight origin", security = {@SecurityRequirement(name = "bearerAuth")})
    @PutMapping("/update_flight_origin")
    @ApiResponse(responseCode = "200", description = "Flight origin updated successfully",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "Flight info or origin airport not found",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "text/plain"))
    public ResponseEntity<Object> updateFlightOrigin(@RequestBody UpdateFlightOriginRequestDto requestDto){

        Airport origin = airportService.getAirportById(requestDto.getOriginAirportId());
        if(origin == null){
            return  new ResponseEntity<>("Origin airport not found.", HttpStatus.NOT_FOUND);
        }
        FlightInfo flightInfo = flightInfoService.updateFlightOrigin(requestDto.getFlightId(), origin);
        if(flightInfo == null){
            return  new ResponseEntity<>("Flight info not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(flightInfo, HttpStatus.OK);
    }
    @Operation(summary = "Update flight arrival date or time", security = {@SecurityRequirement(name = "bearerAuth")})
    @PutMapping("/update_flight_arrival_date")
    @ApiResponse(responseCode = "200", description = "Flight arrival date or time updated successfully",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Arrival date or time must be provided.",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "404", description = "Flight info not found",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "text/plain"))
    public ResponseEntity<Object> updateFlightArrivalDate(@RequestBody UpdateFlightArrivalDateRequestDto requestDto){
        if(requestDto.getArrivalDate() == null && requestDto.getArrivalTime() == null){
            return  new ResponseEntity<>("Arrival date or time must be provided.", HttpStatus.BAD_REQUEST);
        }
        FlightInfo flightInfo = null;
        if(requestDto.getArrivalDate() != null){
            flightInfo = flightInfoService.updateFlightArrivalDate(requestDto.getFlightId(), requestDto.getArrivalDate());
        }
        if(requestDto.getArrivalTime() != null){
            flightInfo = flightInfoService.updateFlightArrivalTime(requestDto.getFlightId(), requestDto.getArrivalTime());
        }

        if(flightInfo == null){
            return  new ResponseEntity<>("Flight info not found.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(flightInfo, HttpStatus.OK);

    }
    @Operation(summary = "Update flight departure date or time", security = {@SecurityRequirement(name = "bearerAuth")})
    @PutMapping("/update_flight_departure_date")
    @ApiResponse(responseCode = "200", description = "Flight departure date or time updated successfully",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Departure date or time must be provided.",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "404", description = "Flight info not found",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "text/plain"))
    public ResponseEntity<Object> updateFlightDepartureDate(@RequestBody UpdateFlightDepartureDateRequestDto requestDto){
        if( requestDto.getDepartureDate() == null && requestDto.getDepartureTime() == null){
            return  new ResponseEntity<>("Departure date or time must be provided.", HttpStatus.BAD_REQUEST);
        }
        FlightInfo flightInfo = null;
        if(requestDto.getDepartureDate() != null){
            flightInfo = flightInfoService.updateFlightDepartureDate(requestDto.getFlightId(), requestDto.getDepartureDate());
        }
        if(requestDto.getDepartureTime() != null){
            flightInfo = flightInfoService.updateFlightDepartureTime(requestDto.getFlightId(), requestDto.getDepartureTime());
        }
        if(flightInfo == null){
            return  new ResponseEntity<>("Flight info not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(flightInfo, HttpStatus.OK);

    }

    @Operation(summary = "Delete flight info by ID", security = {@SecurityRequirement(name = "bearerAuth")})
    @DeleteMapping("/delete_flight")
    @ApiResponse(responseCode = "200", description = "Flight info deleted successfully",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "404", description = "Flight info not found",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "text/plain"))
    public ResponseEntity<String> deleteFlightInfo(@RequestBody DeleteFlightInfoRequestDto requestDto){
        FlightInfo flightInfo = flightInfoService.deleteFlightInfo(requestDto.getId());
        if(flightInfo !=null){
            return new ResponseEntity<>("Flight info deleted.", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Flight not found.",HttpStatus.NOT_FOUND);
        }
    }




}
