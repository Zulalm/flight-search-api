package com.example.flightsearchapi.controllers;

import com.example.flightsearchapi.dtos.flightInfoRequestDtos.*;
import com.example.flightsearchapi.models.Airport;
import com.example.flightsearchapi.models.FlightInfo;
import com.example.flightsearchapi.services.AirportService;
import com.example.flightsearchapi.services.FlightInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight")
public class FlightInfoController {
    @Autowired
    private FlightInfoService flightInfoService;
    @Autowired
    private AirportService airportService;

    @GetMapping("/all_flights")
    public ResponseEntity<List<FlightInfo>> getAllFlights(){
        return new ResponseEntity<>(flightInfoService.getAllFlights(), HttpStatus.OK);
    }
    @PostMapping("/add_new_flight")
    public ResponseEntity<Object> addNewFlight(@RequestBody AddFlightInfoRequestDto requestDto){
        Airport destination = airportService.getAirportById(requestDto.getDestinationAirportId());
        if(destination == null){
            return  new ResponseEntity<>("Destination airport is not found.", HttpStatus.NOT_FOUND);
        }
        Airport origin = airportService.getAirportById(requestDto.getOriginAirportId());
        if(origin == null){
            return  new ResponseEntity<>("Origin airport is not found.", HttpStatus.NOT_FOUND);
        }
        if(requestDto.getArrivalDate() == null || requestDto.getDepartureDate() == null || requestDto.getDepartureTime() == null || requestDto.getArrivalTime() == null){
            return  new ResponseEntity<>("Arrival and departure dates must be provided.", HttpStatus.BAD_REQUEST);
        }
        FlightInfo flightInfo = flightInfoService.addNewFlight(destination,origin,requestDto.getArrivalDate(), requestDto.getArrivalTime(), requestDto.getDepartureDate(),requestDto.getDepartureTime());
        return new ResponseEntity<>(flightInfo, HttpStatus.CREATED);
    }

    @PutMapping("/update_flight_destination")
    public ResponseEntity<Object> updateFlightDestination(@RequestBody UpdateFlightDestinationRequestDto requestDto){

        Airport destination = airportService.getAirportById(requestDto.getDestinationAirportId());
        if(destination == null){
            return  new ResponseEntity<>("Destination airport is not found.", HttpStatus.NOT_FOUND);
        }
        FlightInfo flightInfo = flightInfoService.updateFlightDestination(requestDto.getFlightId(), destination);
        if(flightInfo == null){
            return  new ResponseEntity<>("Flight info is not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(flightInfo, HttpStatus.OK);

    }
    @PutMapping("/update_flight_origin")
    public ResponseEntity<Object> updateFlightOrigin(@RequestBody UpdateFlightOriginRequestDto requestDto){

        Airport origin = airportService.getAirportById(requestDto.getOriginAirportId());
        if(origin == null){
            return  new ResponseEntity<>("Origin airport is not found.", HttpStatus.NOT_FOUND);
        }
        FlightInfo flightInfo = flightInfoService.updateFlightOrigin(requestDto.getFlightId(), origin);
        if(flightInfo == null){
            return  new ResponseEntity<>("Flight info is not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(flightInfo, HttpStatus.OK);
    }
    @PutMapping("/update_flight_arrival_date")
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
            return  new ResponseEntity<>("Flight info is not found.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(flightInfo, HttpStatus.OK);

    }
    @PutMapping("/update_flight_departure_date")
    public ResponseEntity<Object> updateFlightDepartureDate(@RequestBody UpdateFlightDepartureDateRequestDto requestDto){
        if( requestDto.getDepartureDate() == null || requestDto.getDepartureTime() == null){
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
            return  new ResponseEntity<>("Flight info is not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(flightInfo, HttpStatus.OK);

    }

    @DeleteMapping("/delete_flight")
    public ResponseEntity<String> deleteFlightInfo(@RequestBody DeleteFlightInfoRequestDto requestDto){
        FlightInfo flightInfo = flightInfoService.deleteFlightInfo(requestDto.getId());
        if(flightInfo !=null){
            return new ResponseEntity<>("Flight info is deleted.", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Flight is not found.",HttpStatus.NOT_FOUND);
        }
    }




}
