package com.example.flightsearchapi.dtos.airportRequestDtos;

public class UpdateAirportRequestDto {
    long id;
    String city;

    public UpdateAirportRequestDto(long id, String city) {
        this.id = id;
        this.city = city;
    }

    public UpdateAirportRequestDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
