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
        if(requestDto.getArrivalDate() == null || requestDto.getDepartureDate() == null){
            return  new ResponseEntity<>("Arrival and departure dates must be provided.", HttpStatus.BAD_REQUEST);
        }
        FlightInfo flightInfo = flightInfoService.addNewFlight(destination,origin,requestDto.getArrivalDate(), requestDto.getDepartureDate());
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
        FlightInfo flightInfo = flightInfoService.updateFlightArrivalDate(requestDto.getFlightId(), requestDto.getArrivalDate());
        if(flightInfo == null){
            return  new ResponseEntity<>("Flight info is not found.", HttpStatus.NOT_FOUND);
        }
        if(requestDto.getArrivalDate() == null){
            return  new ResponseEntity<>("Arrival date must be provided.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(flightInfo, HttpStatus.OK);

    }
    @PutMapping("/update_flight_departure_date")
    public ResponseEntity<Object> updateFlightDepartureDate(@RequestBody UpdateFlightDepartureDateRequestDto requestDto){
        FlightInfo flightInfo = flightInfoService.updateFlightDepartureDate(requestDto.getFlightId(), requestDto.getDepartureDate());
        if(flightInfo == null){
            return  new ResponseEntity<>("Flight info is not found.", HttpStatus.NOT_FOUND);
        }
        if( requestDto.getDepartureDate() == null){
            return  new ResponseEntity<>("Departure date must be provided.", HttpStatus.BAD_REQUEST);
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
