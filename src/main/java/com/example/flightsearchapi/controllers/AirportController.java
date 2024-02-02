package com.example.flightsearchapi.controllers;

import com.example.flightsearchapi.dtos.airportRequestDtos.AddAirportRequestDto;
import com.example.flightsearchapi.dtos.airportRequestDtos.DeleteAirportRequestDto;
import com.example.flightsearchapi.dtos.airportRequestDtos.UpdateAirportRequestDto;
import com.example.flightsearchapi.models.Airport;
import com.example.flightsearchapi.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airport")
public class AirportController {
    @Autowired
    private AirportService airportService;

    @GetMapping("/all_airports")
    public ResponseEntity<List<Airport>> getAllAirports(){
        return new ResponseEntity<>(airportService.getAllAirports(), HttpStatus.OK);
    }
    @GetMapping("/airport")
    public ResponseEntity<Object> getAirportById(Long id){
        Airport airport = airportService.getAirportById(id);
        if(airport !=null){
            return new ResponseEntity<>(airport, HttpStatus.OK);
        }else{
            return  new ResponseEntity<>("Airport is not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add_airport")
    public ResponseEntity<Airport> addAirport(@RequestBody AddAirportRequestDto requestDto){
        Airport airport = airportService.addNewAirport(requestDto.getCity());
        return  new ResponseEntity<>(airport, HttpStatus.CREATED);
    }
    @PutMapping("/update_airport_city")
    public ResponseEntity<Object> updateAirportCity(@RequestBody UpdateAirportRequestDto requestDto){
        Airport airport = airportService.updateAirportCity(requestDto.getId(), requestDto.getCity());
        if(airport != null){
            return  new ResponseEntity<>(airport, HttpStatus.OK);
        }
        else{
            return  new ResponseEntity<>("Airport is not found.", HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete_airport")
    public ResponseEntity<Object> deleteAirport(@RequestBody DeleteAirportRequestDto requestDto){
        Airport airport = airportService.deleteAirport(requestDto.getId());
        if(airport != null){
            return  new ResponseEntity<>("Airport is deleted.", HttpStatus.OK);
        }
        else{
            return  new ResponseEntity<>("Airport is not found.", HttpStatus.NOT_FOUND);
        }
    }

}
