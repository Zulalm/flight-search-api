package com.example.flightsearchapi.services;

import com.example.flightsearchapi.models.Airport;
import com.example.flightsearchapi.models.FlightInfo;
import com.example.flightsearchapi.repositories.FlightInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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

    public FlightInfo addNewFlight(Airport destination, Airport origin, LocalDate arrivalDate, LocalTime arrivalTime,LocalDate departureDate, LocalTime departureTime){
        FlightInfo flightInfo = new FlightInfo();
        flightInfo.setArrivalDate(arrivalDate);
        flightInfo.setArrivalTime(arrivalTime);
        flightInfo.setDestination(destination);
        flightInfo.setDepartureTime(departureTime);
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
    public FlightInfo updateFlightArrivalDate(Long id, LocalDate arrivalDate){
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
    public FlightInfo updateFlightArrivalTime(Long id, LocalTime arrivalTime){
        Optional<FlightInfo> flightInfoOptional = flightInfoRepository.findById(id);
        if(flightInfoOptional.isPresent()){
            FlightInfo flightInfo = flightInfoOptional.get();
            flightInfo.setArrivalTime(arrivalTime);
            flightInfoRepository.save(flightInfo);
            return flightInfo;
        }
        else{
            return null;
        }
    }
    public FlightInfo updateFlightDepartureDate(Long id, LocalDate departureDate){
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
    public FlightInfo updateFlightDepartureTime(Long id, LocalTime departureTime){
        Optional<FlightInfo> flightInfoOptional = flightInfoRepository.findById(id);
        if(flightInfoOptional.isPresent()){
            FlightInfo flightInfo = flightInfoOptional.get();
            flightInfo.setDepartureTime(departureTime);
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
