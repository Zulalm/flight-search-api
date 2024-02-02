package com.example.flightsearchapi.controllers;

import com.example.flightsearchapi.dtos.SearchResponseDto.RoundTripSearchResponseDto;
import com.example.flightsearchapi.dtos.searchRequestDtos.SearchFlightRequestDto;
import com.example.flightsearchapi.models.Airport;
import com.example.flightsearchapi.models.FlightInfo;
import com.example.flightsearchapi.services.AirportService;
import com.example.flightsearchapi.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @Autowired
    private AirportService airportService;

    @GetMapping("/")
    public ResponseEntity<Object> searchFlight(@RequestBody SearchFlightRequestDto requestDto){
        if(requestDto.getDepartureDate() == null){
            return new ResponseEntity<>("Departure date must be provided.", HttpStatus.BAD_REQUEST);
        }
        Airport origin = airportService.getAirportById(requestDto.getOriginId());
        if(origin == null ){
            return new ResponseEntity<>("Origin airport is not found.", HttpStatus.NOT_FOUND);
        }
        Airport destination = airportService.getAirportById(requestDto.getDestinationId());
        if(destination == null ){
            return new ResponseEntity<>("Destination airport is not found.", HttpStatus.NOT_FOUND);
        }
        if(requestDto.getReturnDate() != null){
            // round trip
            if(requestDto.getReturnDate().isBefore(requestDto.getDepartureDate())){
                return new ResponseEntity<>("Return date must be later than the departure date.", HttpStatus.BAD_REQUEST);
            }
            List<FlightInfo> outboundFlights = searchService.searchFlightByOriginAndDestinationAndDepartureDate(origin,destination,requestDto.getDepartureDate());
            List<FlightInfo> inboundFlights = searchService.searchFlightByOriginAndDestinationAndDepartureDate(destination,origin,requestDto.getReturnDate());
            RoundTripSearchResponseDto responseDto = new RoundTripSearchResponseDto(outboundFlights,inboundFlights);
            if(outboundFlights.isEmpty() && inboundFlights.isEmpty()){
                return new ResponseEntity<>("Cannot find any flight with the given parameters.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(responseDto,HttpStatus.OK);
        }else{
            // one way flight
            List<FlightInfo> outboundFlights = searchService.searchFlightByOriginAndDestinationAndDepartureDate(origin,destination,requestDto.getDepartureDate());
            if(outboundFlights.isEmpty()){
                return new ResponseEntity<>("Cannot find any flight with the given parameters.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(outboundFlights,HttpStatus.OK);
        }

    }


}
