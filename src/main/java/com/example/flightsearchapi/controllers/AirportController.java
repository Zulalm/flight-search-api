package com.example.flightsearchapi.controllers;

import com.example.flightsearchapi.dtos.airportRequestDtos.AddAirportRequestDto;
import com.example.flightsearchapi.dtos.airportRequestDtos.UpdateAirportRequestDto;
import com.example.flightsearchapi.models.Airport;
import com.example.flightsearchapi.services.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airport")
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class AirportController {
    @Autowired
    private AirportService airportService;

    @Operation(summary = "Get all airports", security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping("/all_airports")
    @ApiResponse(responseCode = "200", description = "List of all airports",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "text/plain"))
    public ResponseEntity<List<Airport>> getAllAirports(){
        return new ResponseEntity<>(airportService.getAllAirports(), HttpStatus.OK);
    }
    @Operation(summary = "Get airport by ID", security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping("/airport")
    @ApiResponse(responseCode = "200", description = "Airport details",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "Airport not found",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "text/plain"))
    public ResponseEntity<Object> getAirportById(Long id){
        Airport airport = airportService.getAirportById(id);
        if(airport !=null){
            return new ResponseEntity<>(airport, HttpStatus.OK);
        }else{
            return  new ResponseEntity<>("Airport not found.", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a new airport", security = {@SecurityRequirement(name = "bearerAuth")})
    @PostMapping("/add_airport")
    @ApiResponse(responseCode = "201", description = "Airport added successfully",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "text/plain"))
    public ResponseEntity<Airport> addAirport(@RequestBody AddAirportRequestDto requestDto){
        Airport airport = airportService.addNewAirport(requestDto.getCity());
        return  new ResponseEntity<>(airport, HttpStatus.CREATED);
    }
    @Operation(summary = "Update airport city", security = {@SecurityRequirement(name = "bearerAuth")})
    @PutMapping("/update_airport_city")
    @ApiResponse(responseCode = "200", description = "Airport city updated successfully",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "Airport not found",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "text/plain"))
    public ResponseEntity<Object> updateAirportCity(@RequestBody UpdateAirportRequestDto requestDto){
        Airport airport = airportService.updateAirportCity(requestDto.getId(), requestDto.getCity());
        if(airport != null){
            return  new ResponseEntity<>(airport, HttpStatus.OK);
        }
        else{
            return  new ResponseEntity<>("Airport not found.", HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Delete airport by ID", security = {@SecurityRequirement(name = "bearerAuth")})
    @DeleteMapping("/delete_airport")
    @ApiResponse(responseCode = "200", description = "Airport deleted successfully",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "404", description = "Airport not found",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "text/plain"))
    public ResponseEntity<Object> deleteAirport(Long airportId){
        Airport airport = airportService.deleteAirport(airportId);
        if(airport != null){
            return  new ResponseEntity<>("Airport deleted.", HttpStatus.OK);
        }
        else{
            return  new ResponseEntity<>("Airport not found.", HttpStatus.NOT_FOUND);
        }
    }

}
