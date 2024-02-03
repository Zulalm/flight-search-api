package com.example.flightsearchapi.services;

import com.example.flightsearchapi.repositories.AirportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.flightsearchapi.models.Airport;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirportServiceTest {
    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AirportService airportService;
    @Test
    void getAllAirports() {
        List<Airport> airports = List.of(new Airport(), new Airport(), new Airport());

        when(airportRepository.findAll()).thenReturn(airports);

        List<Airport> result = airportService.getAllAirports();

        assertEquals(airports, result);
        verify(airportRepository, times(1)).findAll();
    }

    @Test
    void addNewAirport() {
        String city = "TestCity";
        Airport airport = new Airport();
        airport.setCity(city);

        when(airportRepository.save(any())).thenReturn(airport);
        Airport result = airportService.addNewAirport(city);

        assertEquals(airport.getCity(), result.getCity());
        verify(airportRepository, times(1)).save(any());
    }

    @Test
    void updateAirportCity_ValidId_ReturnsUpdatedAirport() {
        long airportId = 1L;
        String newCity = "NewCity";
        Airport existingAirport = new Airport();
        existingAirport.setAirportId(airportId);

        when(airportRepository.findById(airportId)).thenReturn(Optional.of(existingAirport));
        when(airportRepository.save(existingAirport)).thenReturn(existingAirport);

        Airport updatedAirport = airportService.updateAirportCity(airportId, newCity);

        assertEquals(newCity, updatedAirport.getCity());
        verify(airportRepository, times(1)).findById(airportId);
        verify(airportRepository, times(1)).save(existingAirport);
    }

    @Test
    void updateAirportCity_InvalidId_ReturnsNull() {
        long invalidAirportId = 999L;
        String newCity = "NewCity";

        when(airportRepository.findById(invalidAirportId)).thenReturn(Optional.empty());

        Airport result = airportService.updateAirportCity(invalidAirportId, newCity);

        assertNull(result);
        verify(airportRepository, times(1)).findById(invalidAirportId);
        verify(airportRepository, never()).save(any());
    }

    @Test
    void deleteAirport_ValidId_ReturnsDeletedAirport() {
        long airportId = 1L;
        Airport existingAirport = new Airport();
        existingAirport.setAirportId(airportId);

        when(airportRepository.findById(airportId)).thenReturn(Optional.of(existingAirport));

        Airport deletedAirport = airportService.deleteAirport(airportId);

        assertEquals(existingAirport, deletedAirport);
        verify(airportRepository, times(1)).findById(airportId);
        verify(airportRepository, times(1)).delete(existingAirport);
    }

    @Test
    void deleteAirport_InvalidId_ReturnsNull() {
        long invalidAirportId = 999L;

        when(airportRepository.findById(invalidAirportId)).thenReturn(Optional.empty());

        Airport result = airportService.deleteAirport(invalidAirportId);

        assertNull(result);
        verify(airportRepository, times(1)).findById(invalidAirportId);
        verify(airportRepository, never()).delete(any());
    }

    @Test
    void getAirportById_ValidId_ReturnsAirport() {
        long airportId = 1L;
        Airport existingAirport = new Airport();
        existingAirport.setAirportId(airportId);

        when(airportRepository.findById(airportId)).thenReturn(Optional.of(existingAirport));

        Airport result = airportService.getAirportById(airportId);

        assertEquals(existingAirport, result);
        verify(airportRepository, times(1)).findById(airportId);
    }

    @Test
    void getAirportById_InvalidId_ReturnsNull() {
        long invalidAirportId = 999L;

        when(airportRepository.findById(invalidAirportId)).thenReturn(Optional.empty());

        Airport result = airportService.getAirportById(invalidAirportId);

        assertNull(result);
        verify(airportRepository, times(1)).findById(invalidAirportId);
    }
}