package com.example.flightsearchapi.services;

import com.example.flightsearchapi.models.Airport;
import com.example.flightsearchapi.models.FlightInfo;
import com.example.flightsearchapi.repositories.FlightInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private FlightInfoRepository flightInfoRepository;
    public List<FlightInfo> searchFlightByOriginAndDestinationAndDepartureDate(Airport origin, Airport destination, LocalDate departureDate){
        return flightInfoRepository.findByOriginAndDestinationAndDepartureDate(origin, destination, departureDate);
    }

}
