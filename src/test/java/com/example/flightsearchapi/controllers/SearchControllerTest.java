package com.example.flightsearchapi.controllers;

import com.example.flightsearchapi.dtos.SearchResponseDto.RoundTripSearchResponseDto;
import com.example.flightsearchapi.dtos.searchRequestDtos.SearchFlightRequestDto;
import com.example.flightsearchapi.models.Airport;
import com.example.flightsearchapi.models.FlightInfo;
import com.example.flightsearchapi.services.AirportService;
import com.example.flightsearchapi.services.SearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchControllerTest {

    @Mock
    private SearchService searchService;

    @Mock
    private AirportService airportService;

    @InjectMocks
    private SearchController searchController;


    @Test
    void testSearchFlightWithRoundTrip() {
        FlightInfo flightInfo = new FlightInfo();
        FlightInfo returnFlightInfo = new FlightInfo();
        SearchFlightRequestDto requestDto = new SearchFlightRequestDto();
        requestDto.setDepartureDate(LocalDate.now());
        requestDto.setReturnDate(LocalDate.now().plusDays(1));
        Airport origin = new Airport();
        Airport destination = new Airport();
        List<FlightInfo> outboundFlights = new ArrayList<>();
        List<FlightInfo> inboundFlights = new ArrayList<>();
        outboundFlights.add(flightInfo);
        inboundFlights.add(returnFlightInfo);
        RoundTripSearchResponseDto expectedResponseDto = new RoundTripSearchResponseDto(outboundFlights, inboundFlights);

        when(airportService.getAirportById(anyLong())).thenReturn(origin, destination);
        when(searchService.searchFlightByOriginAndDestinationAndDepartureDate(origin, destination, requestDto.getDepartureDate()))
                .thenReturn(outboundFlights);
        when(searchService.searchFlightByOriginAndDestinationAndDepartureDate(destination, origin, requestDto.getReturnDate()))
                .thenReturn(inboundFlights);

        ResponseEntity<Object> responseEntity = searchController.searchFlight(requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(expectedResponseDto.getInboundFlights().containsAll(((RoundTripSearchResponseDto) Objects.requireNonNull(responseEntity.getBody())).getInboundFlights()));
        assertTrue(expectedResponseDto.getOutboundFlights().containsAll(((RoundTripSearchResponseDto) Objects.requireNonNull(responseEntity.getBody())).getOutboundFlights()));
    }

    @Test
    void testSearchFlightWithMissingDepartureDate() {
        SearchFlightRequestDto requestDto = new SearchFlightRequestDto();

        ResponseEntity<Object> responseEntity = searchController.searchFlight(requestDto);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Departure date must be provided.", responseEntity.getBody());
    }

    @Test
    void testSearchFlightWithInvalidOriginAirport() {
        SearchFlightRequestDto requestDto = new SearchFlightRequestDto();
        requestDto.setDepartureDate(LocalDate.now());
        requestDto.setOriginId(1L);

        when(airportService.getAirportById(anyLong())).thenReturn(null);

        ResponseEntity<Object> responseEntity = searchController.searchFlight(requestDto);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Origin airport not found.", responseEntity.getBody());
    }

    @Test
    void testSearchFlightWithValidData() {
        SearchFlightRequestDto requestDto = new SearchFlightRequestDto();
        FlightInfo flightInfo = new FlightInfo();
        requestDto.setDepartureDate(LocalDate.now());
        requestDto.setOriginId(1L);
        requestDto.setDestinationId(2L);

        Airport origin = new Airport();
        Airport destination = new Airport();
        List<FlightInfo> outboundFlights = new ArrayList<>();
        outboundFlights.add(flightInfo);

        when(airportService.getAirportById(anyLong())).thenReturn(origin, destination);
        when(searchService.searchFlightByOriginAndDestinationAndDepartureDate(origin, destination, requestDto.getDepartureDate()))
                .thenReturn(outboundFlights);

        ResponseEntity<Object> responseEntity = searchController.searchFlight(requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(outboundFlights.containsAll(((ArrayList<FlightInfo>) Objects.requireNonNull(responseEntity.getBody()))));
    }

}