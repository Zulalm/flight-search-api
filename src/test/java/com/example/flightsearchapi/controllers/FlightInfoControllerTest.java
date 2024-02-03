package com.example.flightsearchapi.controllers;

import com.example.flightsearchapi.dtos.flightInfoRequestDtos.*;
import com.example.flightsearchapi.services.FlightInfoService;
import org.junit.jupiter.api.Test;
import com.example.flightsearchapi.models.Airport;
import com.example.flightsearchapi.models.FlightInfo;
import com.example.flightsearchapi.services.AirportService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightInfoControllerTest {

    @Mock
    private FlightInfoService flightInfoService;
    @Mock
    private AirportService airportService;

    @InjectMocks
    private FlightInfoController flightInfoController;

    @Test
    void testGetAllFlightsWhenFlightsAvailable() {
        List<FlightInfo> flights = new ArrayList<>();
        flights.add(new FlightInfo());
        flights.add(new FlightInfo());

        when(flightInfoService.getAllFlights()).thenReturn(flights);

        ResponseEntity<List<FlightInfo>> responseEntity = flightInfoController.getAllFlights();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(flights, responseEntity.getBody());
    }


    @Test
    void testAddNewFlightSuccess() {
        AddFlightInfoRequestDto requestDto = new AddFlightInfoRequestDto();
        requestDto.setDestinationAirportId(1L);
        requestDto.setOriginAirportId(2L);
        requestDto.setArrivalDate(LocalDate.now());
        requestDto.setArrivalTime(LocalTime.now());
        requestDto.setDepartureDate(LocalDate.now().plusDays(1));
        requestDto.setDepartureTime(LocalTime.now().plusHours(1));

        Airport destinationAirport = new Airport();
        Airport originAirport = new Airport();
        when(airportService.getAirportById(1L)).thenReturn(destinationAirport);
        when(airportService.getAirportById(2L)).thenReturn(originAirport);

        FlightInfo flightInfo = new FlightInfo();
        when(flightInfoService.addNewFlight(destinationAirport, originAirport, requestDto.getArrivalDate(),
                requestDto.getArrivalTime(), requestDto.getDepartureDate(), requestDto.getDepartureTime()))
                .thenReturn(flightInfo);

        ResponseEntity<Object> responseEntity = flightInfoController.addNewFlight(requestDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(flightInfo, responseEntity.getBody());
    }

    @Test
    void testAddNewFlightDestinationNotFound() {
        AddFlightInfoRequestDto requestDto = new AddFlightInfoRequestDto();
        requestDto.setDestinationAirportId(1L);
        requestDto.setOriginAirportId(2L);

        when(airportService.getAirportById(1L)).thenReturn(null);

        ResponseEntity<Object> responseEntity = flightInfoController.addNewFlight(requestDto);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Destination airport not found.", responseEntity.getBody());
    }

    @Test
    void testAddNewFlightOriginNotFound() {
        AddFlightInfoRequestDto requestDto = new AddFlightInfoRequestDto();
        requestDto.setDestinationAirportId(1L);
        requestDto.setOriginAirportId(2L);

        Airport destinationAirport = new Airport();
        when(airportService.getAirportById(1L)).thenReturn(destinationAirport);
        when(airportService.getAirportById(2L)).thenReturn(null);

        ResponseEntity<Object> responseEntity = flightInfoController.addNewFlight(requestDto);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Origin airport not found.", responseEntity.getBody());
    }

    @Test
    void testAddNewFlightMissingDatesAndTimes() {
        AddFlightInfoRequestDto requestDto = new AddFlightInfoRequestDto();
        requestDto.setDestinationAirportId(1L);
        requestDto.setOriginAirportId(2L);
        Mockito.when(airportService.getAirportById(anyLong())).thenReturn(new Airport());
        ResponseEntity<Object> responseEntity = flightInfoController.addNewFlight(requestDto);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Arrival and departure dates must be provided.", responseEntity.getBody());
    }

    @Test
    void testUpdateFlightDestinationSuccess() {
        UpdateFlightDestinationRequestDto requestDto = new UpdateFlightDestinationRequestDto();
        requestDto.setFlightId(1L);
        requestDto.setDestinationAirportId(2L);

        Airport destinationAirport = new Airport();
        when(airportService.getAirportById(2L)).thenReturn(destinationAirport);

        FlightInfo updatedFlightInfo = new FlightInfo();
        when(flightInfoService.updateFlightDestination(1L, destinationAirport)).thenReturn(updatedFlightInfo);

        ResponseEntity<Object> responseEntity = flightInfoController.updateFlightDestination(requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedFlightInfo, responseEntity.getBody());
    }

    @Test
    void testUpdateFlightDestinationDestinationNotFound() {
        UpdateFlightDestinationRequestDto requestDto = new UpdateFlightDestinationRequestDto();
        requestDto.setFlightId(1L);
        requestDto.setDestinationAirportId(2L);

        when(airportService.getAirportById(2L)).thenReturn(null);

        ResponseEntity<Object> responseEntity = flightInfoController.updateFlightDestination(requestDto);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Destination airport not found.", responseEntity.getBody());
    }

    @Test
    void testUpdateFlightDestinationFlightInfoNotFound() {
        UpdateFlightDestinationRequestDto requestDto = new UpdateFlightDestinationRequestDto();
        requestDto.setFlightId(1L);
        requestDto.setDestinationAirportId(2L);

        Airport destinationAirport = new Airport();
        when(airportService.getAirportById(2L)).thenReturn(destinationAirport);
        when(flightInfoService.updateFlightDestination(1L, destinationAirport)).thenReturn(null);

        ResponseEntity<Object> responseEntity = flightInfoController.updateFlightDestination(requestDto);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Flight info not found.", responseEntity.getBody());
    }

    @Test
    void testUpdateFlightOriginSuccess() {
        UpdateFlightOriginRequestDto requestDto = new UpdateFlightOriginRequestDto();
        requestDto.setFlightId(1L);
        requestDto.setOriginAirportId(2L);

        Airport originAirport = new Airport();
        when(airportService.getAirportById(2L)).thenReturn(originAirport);

        FlightInfo updatedFlightInfo = new FlightInfo();
        when(flightInfoService.updateFlightOrigin(1L, originAirport)).thenReturn(updatedFlightInfo);

        ResponseEntity<Object> responseEntity = flightInfoController.updateFlightOrigin(requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedFlightInfo, responseEntity.getBody());
    }

    @Test
    void testUpdateFlightOriginOriginNotFound() {
        UpdateFlightOriginRequestDto requestDto = new UpdateFlightOriginRequestDto();
        requestDto.setFlightId(1L);
        requestDto.setOriginAirportId(2L);

        when(airportService.getAirportById(2L)).thenReturn(null);

        ResponseEntity<Object> responseEntity = flightInfoController.updateFlightOrigin(requestDto);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Origin airport not found.", responseEntity.getBody());
    }

    @Test
    void testUpdateFlightOriginFlightInfoNotFound() {
        UpdateFlightOriginRequestDto requestDto = new UpdateFlightOriginRequestDto();
        requestDto.setFlightId(1L);
        requestDto.setOriginAirportId(2L);

        Airport originAirport = new Airport();
        when(airportService.getAirportById(2L)).thenReturn(originAirport);
        when(flightInfoService.updateFlightOrigin(1L, originAirport)).thenReturn(null);

        ResponseEntity<Object> responseEntity = flightInfoController.updateFlightOrigin(requestDto);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Flight info not found.", responseEntity.getBody());
    }

    @Test
    void testUpdateFlightArrivalDateSuccess() {
        UpdateFlightArrivalDateRequestDto requestDto = new UpdateFlightArrivalDateRequestDto();
        requestDto.setFlightId(1L);
        requestDto.setArrivalDate(LocalDate.of(2022, 8, 1));

        FlightInfo updatedFlightInfo = new FlightInfo();
        when(flightInfoService.updateFlightArrivalDate(1L, requestDto.getArrivalDate())).thenReturn(updatedFlightInfo);

        ResponseEntity<Object> responseEntity = flightInfoController.updateFlightArrivalDate(requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedFlightInfo, responseEntity.getBody());
    }

    @Test
    void testUpdateFlightArrivalDateAndTimeSuccess() {
        UpdateFlightArrivalDateRequestDto requestDto = new UpdateFlightArrivalDateRequestDto();
        requestDto.setFlightId(1L);
        requestDto.setArrivalDate(LocalDate.of(2022, 8, 1));
        requestDto.setArrivalTime(LocalTime.of(12, 30));

        FlightInfo updatedFlightInfo = new FlightInfo();
        when(flightInfoService.updateFlightArrivalTime(1L, requestDto.getArrivalTime())).thenReturn(updatedFlightInfo);

        ResponseEntity<Object> responseEntity = flightInfoController.updateFlightArrivalDate(requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedFlightInfo, responseEntity.getBody());
    }

    @Test
    void testUpdateFlightArrivalDateNoDateOrTimeProvided() {
        UpdateFlightArrivalDateRequestDto requestDto = new UpdateFlightArrivalDateRequestDto();
        requestDto.setFlightId(1L);

        ResponseEntity<Object> responseEntity = flightInfoController.updateFlightArrivalDate(requestDto);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Arrival date or time must be provided.", responseEntity.getBody());
    }

    @Test
    void testUpdateFlightArrivalDateFlightInfoNotFound() {
        UpdateFlightArrivalDateRequestDto requestDto = new UpdateFlightArrivalDateRequestDto();
        requestDto.setFlightId(1L);
        requestDto.setArrivalDate(LocalDate.of(2022, 8, 1));

        when(flightInfoService.updateFlightArrivalDate(1L, requestDto.getArrivalDate())).thenReturn(null);

        ResponseEntity<Object> responseEntity = flightInfoController.updateFlightArrivalDate(requestDto);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Flight info not found.", responseEntity.getBody());
    }

    @Test
    void testUpdateFlightDepartureDateSuccess() {
        UpdateFlightDepartureDateRequestDto requestDto = new UpdateFlightDepartureDateRequestDto();
        requestDto.setFlightId(1L);
        requestDto.setDepartureDate(LocalDate.of(2022, 8, 1));
        FlightInfo updatedFlightInfo = new FlightInfo();
        when(flightInfoService.updateFlightDepartureDate(1L, requestDto.getDepartureDate())).thenReturn(updatedFlightInfo);

        ResponseEntity<Object> responseEntity = flightInfoController.updateFlightDepartureDate(requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedFlightInfo, responseEntity.getBody());
    }

    @Test
    void testUpdateFlightDepartureDateAndTimeSuccess() {
        UpdateFlightDepartureDateRequestDto requestDto = new UpdateFlightDepartureDateRequestDto();
        requestDto.setFlightId(1L);
        requestDto.setDepartureDate(LocalDate.of(2022, 8, 1));
        requestDto.setDepartureTime(LocalTime.of(12, 30));

        FlightInfo updatedFlightInfo = new FlightInfo();
        when(flightInfoService.updateFlightDepartureTime(1L, requestDto.getDepartureTime())).thenReturn(updatedFlightInfo);

        ResponseEntity<Object> responseEntity = flightInfoController.updateFlightDepartureDate(requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedFlightInfo, responseEntity.getBody());
    }

    @Test
    void testUpdateFlightDepartureDateNoDateOrTimeProvided() {
        UpdateFlightDepartureDateRequestDto requestDto = new UpdateFlightDepartureDateRequestDto();
        requestDto.setFlightId(1L);

        ResponseEntity<Object> responseEntity = flightInfoController.updateFlightDepartureDate(requestDto);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Departure date or time must be provided.", responseEntity.getBody());
    }

    @Test
    void testUpdateFlightDepartureDateFlightInfoNotFound() {
        UpdateFlightDepartureDateRequestDto requestDto = new UpdateFlightDepartureDateRequestDto();
        requestDto.setFlightId(1L);
        requestDto.setDepartureDate(LocalDate.of(2022, 8, 1));

        when(flightInfoService.updateFlightDepartureDate(1L, requestDto.getDepartureDate())).thenReturn(null);

        ResponseEntity<Object> responseEntity = flightInfoController.updateFlightDepartureDate(requestDto);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Flight info not found.", responseEntity.getBody());
    }

    @Test
    void testDeleteFlightInfoSuccess() {
        DeleteFlightInfoRequestDto requestDto = new DeleteFlightInfoRequestDto();
        requestDto.setId(1L);

        FlightInfo deletedFlightInfo = new FlightInfo();
        when(flightInfoService.deleteFlightInfo(1L)).thenReturn(deletedFlightInfo);

        ResponseEntity<String> responseEntity = flightInfoController.deleteFlightInfo(requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Flight info deleted.", responseEntity.getBody());
    }

    @Test
    void testDeleteFlightInfoNotFound() {
        DeleteFlightInfoRequestDto requestDto = new DeleteFlightInfoRequestDto();
        requestDto.setId(1L);

        when(flightInfoService.deleteFlightInfo(1L)).thenReturn(null);

        ResponseEntity<String> responseEntity = flightInfoController.deleteFlightInfo(requestDto);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Flight not found.", responseEntity.getBody());
    }
}