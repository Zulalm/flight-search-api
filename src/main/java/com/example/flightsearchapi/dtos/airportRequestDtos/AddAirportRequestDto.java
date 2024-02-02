package com.example.flightsearchapi.dtos.airportRequestDtos;

public class AddAirportRequestDto {
    String city;

    public AddAirportRequestDto(String city) {
        this.city = city;
    }

    public AddAirportRequestDto() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
