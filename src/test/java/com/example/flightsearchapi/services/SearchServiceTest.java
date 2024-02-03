package com.example.flightsearchapi.services;

import com.example.flightsearchapi.models.Airport;
import com.example.flightsearchapi.models.FlightInfo;
import com.example.flightsearchapi.repositories.FlightInfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class SearchServiceTest {
    @Mock
    private FlightInfoRepository flightInfoRepository;

    @InjectMocks
    private SearchService searchService;

    @Test
    void searchFlightByOriginAndDestinationAndDepartureDate() {
        Airport origin = new Airport();
        Airport destination = new Airport();
        LocalDate departureDate = LocalDate.now();
        FlightInfo flightInfo = new FlightInfo();
        List<FlightInfo> mockFlights = Collections.singletonList(flightInfo);

        Mockito.when(flightInfoRepository.findByOriginAndDestinationAndDepartureDate(origin, destination, departureDate))
                .thenReturn(mockFlights);

        List<FlightInfo> resultFlights = searchService.searchFlightByOriginAndDestinationAndDepartureDate(origin, destination, departureDate);

        assertEquals(mockFlights, resultFlights);
        verify(flightInfoRepository, times(1))
                .findByOriginAndDestinationAndDepartureDate(origin, destination, departureDate);

    }
}