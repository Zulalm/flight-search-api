package com.example.flightsearchapi.controllers;

import com.example.flightsearchapi.dtos.airportRequestDtos.AddAirportRequestDto;
import com.example.flightsearchapi.dtos.airportRequestDtos.DeleteAirportRequestDto;
import com.example.flightsearchapi.dtos.airportRequestDtos.UpdateAirportRequestDto;
import org.junit.jupiter.api.Test;

import com.example.flightsearchapi.models.Airport;
import com.example.flightsearchapi.services.AirportService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AirportControllerTest {

    @Mock
    private AirportService airportService;

    @InjectMocks
    private AirportController airportController;


    @Test
    void testGetAllAirports() {
        List<Airport> airports = Arrays.asList(new Airport(), new Airport());
        when(airportService.getAllAirports()).thenReturn(airports);

        ResponseEntity<List<Airport>> responseEntity = airportController.getAllAirports();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(airports, responseEntity.getBody());
    }


    @Test
    void testGetAirportById() {
        Long airportId = 1L;
        Airport airport = new Airport();
        when(airportService.getAirportById(airportId)).thenReturn(airport);

        ResponseEntity<Object> responseEntity = airportController.getAirportById(airportId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(airport, responseEntity.getBody());
    }

    @Test
    void testGetAirportByIdNotFound() {
        Long airportId = 1L;
        when(airportService.getAirportById(airportId)).thenReturn(null);

        ResponseEntity<Object> responseEntity = airportController.getAirportById(airportId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Airport not found.", responseEntity.getBody());
    }


    @Test
    void testAddAirport() {
        AddAirportRequestDto requestDto = new AddAirportRequestDto();
        requestDto.setCity("TestCity");

        Airport airport = new Airport();
        when(airportService.addNewAirport(requestDto.getCity())).thenReturn(airport);

        ResponseEntity<Airport> responseEntity = airportController.addAirport(requestDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(airport, responseEntity.getBody());
    }

    @Test
    void testUpdateAirportCity() {
        UpdateAirportRequestDto requestDto = new UpdateAirportRequestDto();
        requestDto.setId(1L);
        requestDto.setCity("UpdatedCity");

        Airport updatedAirport = new Airport();
        when(airportService.updateAirportCity(requestDto.getId(), requestDto.getCity())).thenReturn(updatedAirport);

        ResponseEntity<Object> responseEntity = airportController.updateAirportCity(requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedAirport, responseEntity.getBody());
    }

    @Test
    void testUpdateAirportCityNotFound() {
        UpdateAirportRequestDto requestDto = new UpdateAirportRequestDto();
        requestDto.setId(1L);
        requestDto.setCity("UpdatedCity");

        when(airportService.updateAirportCity(requestDto.getId(), requestDto.getCity())).thenReturn(null);

        ResponseEntity<Object> responseEntity = airportController.updateAirportCity(requestDto);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Airport not found.", responseEntity.getBody());
    }

    @Test
    void testDeleteAirport() {
        DeleteAirportRequestDto requestDto = new DeleteAirportRequestDto();
        requestDto.setId(1L);

        Airport deletedAirport = new Airport();
        when(airportService.deleteAirport(requestDto.getId())).thenReturn(deletedAirport);

        ResponseEntity<Object> responseEntity = airportController.deleteAirport(requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Airport deleted.", responseEntity.getBody());
    }

    @Test
    void testDeleteAirportNotFound() {
        DeleteAirportRequestDto requestDto = new DeleteAirportRequestDto();
        requestDto.setId(1L);

        when(airportService.deleteAirport(requestDto.getId())).thenReturn(null);

        ResponseEntity<Object> responseEntity = airportController.deleteAirport(requestDto);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Airport not found.", responseEntity.getBody());
    }

}