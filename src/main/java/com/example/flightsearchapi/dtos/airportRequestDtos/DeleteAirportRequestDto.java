package com.example.flightsearchapi.dtos.airportRequestDtos;

public class DeleteAirportRequestDto {
    long id;

    public DeleteAirportRequestDto(long id) {
        this.id = id;
    }

    public DeleteAirportRequestDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
