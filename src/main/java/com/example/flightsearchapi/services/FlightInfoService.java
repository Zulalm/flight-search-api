package com.example.flightsearchapi.services;

import com.example.flightsearchapi.models.Airport;
import com.example.flightsearchapi.models.FlightInfo;
import com.example.flightsearchapi.repositories.FlightInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FlightInfoService {

    @Autowired
    private FlightInfoRepository flightInfoRepository;

    public List<FlightInfo> getAllFlights(){
        return flightInfoRepository.findAll();
    }
    public FlightInfo getFlightById(long  id){
        Optional<FlightInfo> flightInfoOptional = flightInfoRepository.findById(id);
        if(flightInfoOptional.isPresent()){
            FlightInfo flightInfo = flightInfoOptional.get();
            return flightInfo;
        }
        else{
            return null;
        }
    }

    public FlightInfo addNewFlight(Airport destination, Airport origin, LocalDateTime arrivalDate, LocalDateTime departureDate){
        FlightInfo flightInfo = new FlightInfo();
        flightInfo.setArrivalDate(arrivalDate);
        flightInfo.setDestination(destination);
        flightInfo.setDepartureDate(departureDate);
        flightInfo.setOrigin(origin);
        flightInfoRepository.save(flightInfo);
        return flightInfo;
    }

    public FlightInfo updateFlightDestination(Long id, Airport destination){
        Optional<FlightInfo> flightInfoOptional = flightInfoRepository.findById(id);
        if(flightInfoOptional.isPresent()){
            FlightInfo flightInfo = flightInfoOptional.get();
            flightInfo.setDestination(destination);
            flightInfoRepository.save(flightInfo);
            return flightInfo;
        }
        else{
            return null;
        }
    }
    public FlightInfo updateFlightOrigin(Long id, Airport origin){
        Optional<FlightInfo> flightInfoOptional = flightInfoRepository.findById(id);
        if(flightInfoOptional.isPresent()){
            FlightInfo flightInfo = flightInfoOptional.get();
            flightInfo.setOrigin(origin);
            flightInfoRepository.save(flightInfo);
            return flightInfo;
        }
        else{
            return null;
        }
    }
    public FlightInfo updateFlightArrivalDate(Long id, LocalDateTime arrivalDate){
        Optional<FlightInfo> flightInfoOptional = flightInfoRepository.findById(id);
        if(flightInfoOptional.isPresent()){
            FlightInfo flightInfo = flightInfoOptional.get();
            flightInfo.setArrivalDate(arrivalDate);
            flightInfoRepository.save(flightInfo);
            return flightInfo;
        }
        else{
            return null;
        }
    }
    public FlightInfo updateFlightDepartureDate(Long id, LocalDateTime departureDate){
        Optional<FlightInfo> flightInfoOptional = flightInfoRepository.findById(id);
        if(flightInfoOptional.isPresent()){
            FlightInfo flightInfo = flightInfoOptional.get();
            flightInfo.setDepartureDate(departureDate);
            flightInfoRepository.save(flightInfo);
            return flightInfo;
        }
        else{
            return null;
        }
    }

    public FlightInfo deleteFlightInfo(Long id){
        Optional<FlightInfo> flightInfoOptional = flightInfoRepository.findById(id);
        if(flightInfoOptional.isPresent()){
            FlightInfo flightInfo = flightInfoOptional.get();
            flightInfoRepository.delete(flightInfo);
            return flightInfo;
        }
        else{
            return null;
        }
    }
}
