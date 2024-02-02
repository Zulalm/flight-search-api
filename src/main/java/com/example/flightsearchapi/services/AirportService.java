package com.example.flightsearchapi.services;

import com.example.flightsearchapi.models.Airport;
import com.example.flightsearchapi.repositories.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AirportService {
    @Autowired
    private AirportRepository airportRepository;

    public List<Airport> getAllAirports(){
        return airportRepository.findAll();
    }

    public Airport addNewAirport(String city){
        Airport airport = new Airport();
        airport.setCity(city);
        airportRepository.save(airport);
        return airport;
    }

    public Airport updateAirportCity(Long id, String city){
        Optional<Airport> airportOptional = airportRepository.findById(id);
        if(airportOptional.isPresent()){
            Airport airport = airportOptional.get();
            airport.setCity(city);
            airportRepository.save(airport);
            return airport;
        }
        else{
            return null;
        }
    }

    public Airport deleteAirport(Long id){
        Optional<Airport> airportOptional = airportRepository.findById(id);
        if(airportOptional.isPresent()){
            Airport airport = airportOptional.get();
            airportRepository.delete(airport);
            return airport;
        }
        else{
            return null;
        }
    }
    public Airport getAirportById(Long id){
        Optional<Airport> airportOptional = airportRepository.findById(id);
        return airportOptional.orElse(null);
    }

}
