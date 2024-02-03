package com.example.flightsearchapi.services;

import com.example.flightsearchapi.models.FlightInfo;
import com.example.flightsearchapi.repositories.FlightInfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.flightsearchapi.models.Airport;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightInfoServiceTest {
    @Mock
    private FlightInfoRepository flightInfoRepository;

    @InjectMocks
    private FlightInfoService flightInfoService;


    @Test
    void getAllFlights() {
        FlightInfo mockFlight = new FlightInfo();
        when(flightInfoRepository.findAll()).thenReturn(List.of(mockFlight));

        var resultFlights = flightInfoService.getAllFlights();

        assertEquals(List.of(mockFlight), resultFlights);
        verify(flightInfoRepository, times(1)).findAll();
    }

    @Test
    void getFlightById() {
        long flightId = 1L;
        FlightInfo mockFlight = new FlightInfo();
        Optional<FlightInfo> optionalFlight = Optional.of(mockFlight);
        when(flightInfoRepository.findById(flightId)).thenReturn(optionalFlight);

        var resultFlight = flightInfoService.getFlightById(flightId);

        assertEquals(mockFlight, resultFlight);
        verify(flightInfoRepository, times(1)).findById(flightId);

    }

    @Test
    void addNewFlight() {
        Airport destination = new Airport();
        Airport origin = new Airport();
        LocalDate arrivalDate = LocalDate.now();
        LocalTime arrivalTime = LocalTime.now();
        LocalDate departureDate = LocalDate.now().plusDays(1);
        LocalTime departureTime = LocalTime.now().plusHours(1);


        FlightInfo resultFlight = flightInfoService.addNewFlight(destination, origin, arrivalDate, arrivalTime, departureDate, departureTime);

        assertEquals(arrivalDate, resultFlight.getArrivalDate());
        assertEquals(arrivalTime, resultFlight.getArrivalTime());
        assertEquals(destination, resultFlight.getDestination());
        assertEquals(departureDate, resultFlight.getDepartureDate());
        assertEquals(departureTime, resultFlight.getDepartureTime());
        assertEquals(origin, resultFlight.getOrigin());

        verify(flightInfoRepository, times(1)).save(resultFlight);

    }

    @Test
    void updateFlightDestination_ValidId_ReturnsUpdatedFlightInfo() {
        long flightId = 1L;
        Airport newDestination = new Airport();
        FlightInfo existingFlight = new FlightInfo();
        existingFlight.setFlightId(flightId);

        when(flightInfoRepository.findById(flightId)).thenReturn(Optional.of(existingFlight));
        when(flightInfoRepository.save(existingFlight)).thenReturn(existingFlight);

        FlightInfo updatedFlight = flightInfoService.updateFlightDestination(flightId, newDestination);

        assertEquals(newDestination, updatedFlight.getDestination());
        verify(flightInfoRepository, times(1)).findById(flightId);
        verify(flightInfoRepository, times(1)).save(existingFlight);
    }

    @Test
    void updateFlightDestination_InvalidId_ReturnsNull() {
        long invalidFlightId = 999L;
        Airport newDestination = new Airport();

        when(flightInfoRepository.findById(invalidFlightId)).thenReturn(Optional.empty());

        FlightInfo result = flightInfoService.updateFlightDestination(invalidFlightId, newDestination);

        assertNull(result);
        verify(flightInfoRepository, times(1)).findById(invalidFlightId);
        verify(flightInfoRepository, Mockito.never()).save(any());
    }

    @Test
    void updateFlightOrigin_ValidId_ReturnsUpdatedFlightInfo() {

        long flightId = 1L;
        Airport newOrigin = new Airport();
        FlightInfo existingFlight = new FlightInfo();
        existingFlight.setFlightId(flightId);

        when(flightInfoRepository.findById(flightId)).thenReturn(Optional.of(existingFlight));
        when(flightInfoRepository.save(existingFlight)).thenReturn(existingFlight);

        FlightInfo updatedFlight = flightInfoService.updateFlightOrigin(flightId, newOrigin);

        assertEquals(newOrigin, updatedFlight.getOrigin());
        verify(flightInfoRepository, times(1)).findById(flightId);
        verify(flightInfoRepository, times(1)).save(existingFlight);
    }

    @Test
    void updateFlightOrigin_InvalidId_ReturnsNull() {
        long invalidFlightId = 999L;
        Airport newOrigin = new Airport();

        when(flightInfoRepository.findById(invalidFlightId)).thenReturn(Optional.empty());

        FlightInfo result = flightInfoService.updateFlightOrigin(invalidFlightId, newOrigin);

        assertNull(result);
        verify(flightInfoRepository, times(1)).findById(invalidFlightId);
        verify(flightInfoRepository, Mockito.never()).save(any());
    }

    @Test
    void updateFlightArrivalDate_ValidId_ReturnsUpdatedFlightInfo() {
        long flightId = 1L;
        LocalDate newArrivalDate = LocalDate.now();
        FlightInfo existingFlight = new FlightInfo();
        existingFlight.setFlightId(flightId);

        when(flightInfoRepository.findById(flightId)).thenReturn(Optional.of(existingFlight));
        when(flightInfoRepository.save(existingFlight)).thenReturn(existingFlight);

        FlightInfo updatedFlight = flightInfoService.updateFlightArrivalDate(flightId, newArrivalDate);

        assertEquals(newArrivalDate, updatedFlight.getArrivalDate());
        verify(flightInfoRepository, times(1)).findById(flightId);
        verify(flightInfoRepository, times(1)).save(existingFlight);
    }

    @Test
    void updateFlightArrivalDate_InvalidId_ReturnsNull() {
        long invalidFlightId = 999L;
        LocalDate newArrivalDate = LocalDate.now();

        when(flightInfoRepository.findById(invalidFlightId)).thenReturn(Optional.empty());

        FlightInfo result = flightInfoService.updateFlightArrivalDate(invalidFlightId, newArrivalDate);

        assertNull(result);
        verify(flightInfoRepository, times(1)).findById(invalidFlightId);
        verify(flightInfoRepository, never()).save(any());
    }

    @Test
    void updateFlightArrivalTime_ValidId_ReturnsUpdatedFlightInfo() {
        long flightId = 1L;
        LocalTime newArrivalTime = LocalTime.now();
        FlightInfo existingFlight = new FlightInfo();
        existingFlight.setFlightId(flightId);

        when(flightInfoRepository.findById(flightId)).thenReturn(Optional.of(existingFlight));
        when(flightInfoRepository.save(existingFlight)).thenReturn(existingFlight);

        FlightInfo updatedFlight = flightInfoService.updateFlightArrivalTime(flightId, newArrivalTime);

        assertEquals(newArrivalTime, updatedFlight.getArrivalTime());
        verify(flightInfoRepository, times(1)).findById(flightId);
        verify(flightInfoRepository, times(1)).save(existingFlight);
    }

    @Test
    void updateFlightArrivalTime_InvalidId_ReturnsNull() {
        long invalidFlightId = 999L;
        LocalTime newArrivalTime = LocalTime.now();

        when(flightInfoRepository.findById(invalidFlightId)).thenReturn(Optional.empty());

        FlightInfo result = flightInfoService.updateFlightArrivalTime(invalidFlightId, newArrivalTime);

        assertNull(result);
        verify(flightInfoRepository, times(1)).findById(invalidFlightId);
        verify(flightInfoRepository, never()).save(any());
    }

    @Test
    void updateFlightDepartureDate_ValidId_ReturnsUpdatedFlightInfo() {
        long flightId = 1L;
        LocalDate newDepartureDate = LocalDate.now();
        FlightInfo existingFlight = new FlightInfo();
        existingFlight.setFlightId(flightId);

        when(flightInfoRepository.findById(flightId)).thenReturn(Optional.of(existingFlight));
        when(flightInfoRepository.save(existingFlight)).thenReturn(existingFlight);

        FlightInfo updatedFlight = flightInfoService.updateFlightDepartureDate(flightId, newDepartureDate);

        assertEquals(newDepartureDate, updatedFlight.getDepartureDate());
        verify(flightInfoRepository, times(1)).findById(flightId);
        verify(flightInfoRepository, times(1)).save(existingFlight);
    }

    @Test
    void updateFlightDepartureDate_InvalidId_ReturnsNull() {
        long invalidFlightId = 999L;
        LocalDate newDepartureDate = LocalDate.now();

        when(flightInfoRepository.findById(invalidFlightId)).thenReturn(Optional.empty());

        FlightInfo result = flightInfoService.updateFlightDepartureDate(invalidFlightId, newDepartureDate);

        assertNull(result);
        verify(flightInfoRepository, times(1)).findById(invalidFlightId);
        verify(flightInfoRepository, never()).save(any());
    }

    @Test
    void updateFlightDepartureTime_ValidId_ReturnsUpdatedFlightInfo() {
        long flightId = 1L;
        LocalTime newDepartureTime = LocalTime.now();
        FlightInfo existingFlight = new FlightInfo();
        existingFlight.setFlightId(flightId);

        when(flightInfoRepository.findById(flightId)).thenReturn(Optional.of(existingFlight));
        when(flightInfoRepository.save(existingFlight)).thenReturn(existingFlight);

        FlightInfo updatedFlight = flightInfoService.updateFlightDepartureTime(flightId, newDepartureTime);

        assertEquals(newDepartureTime, updatedFlight.getDepartureTime());
        verify(flightInfoRepository, times(1)).findById(flightId);
        verify(flightInfoRepository, times(1)).save(existingFlight);
    }

    @Test
    void updateFlightDepartureTime_InvalidId_ReturnsNull() {
        long invalidFlightId = 999L;
        LocalTime newDepartureTime = LocalTime.now();

        when(flightInfoRepository.findById(invalidFlightId)).thenReturn(Optional.empty());

        FlightInfo result = flightInfoService.updateFlightDepartureTime(invalidFlightId, newDepartureTime);

        assertNull(result);
        verify(flightInfoRepository, times(1)).findById(invalidFlightId);
        verify(flightInfoRepository, never()).save(any());
    }
    @Test
    void deleteFlightInfo_ValidId_ReturnsDeletedFlightInfo() {
        long flightId = 1L;
        FlightInfo existingFlight = new FlightInfo();
        existingFlight.setFlightId(flightId);

        when(flightInfoRepository.findById(flightId)).thenReturn(Optional.of(existingFlight));

        FlightInfo deletedFlight = flightInfoService.deleteFlightInfo(flightId);

        assertEquals(existingFlight, deletedFlight);
        verify(flightInfoRepository, times(1)).findById(flightId);
        verify(flightInfoRepository, times(1)).delete(existingFlight);
    }

    @Test
    void deleteFlightInfo_InvalidId_ReturnsNull() {
        long invalidFlightId = 999L;

        when(flightInfoRepository.findById(invalidFlightId)).thenReturn(Optional.empty());

        FlightInfo result = flightInfoService.deleteFlightInfo(invalidFlightId);

        assertNull(result);
        verify(flightInfoRepository, times(1)).findById(invalidFlightId);
        verify(flightInfoRepository, never()).delete(any());
    }
}