package com.example.flightsearchapi.dtos.flightInfoRequestDtos;

public class DeleteFlightInfoRequestDto {
    private long id;

    public DeleteFlightInfoRequestDto(long id) {
        this.id = id;
    }

    public DeleteFlightInfoRequestDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
