package com.example.flightsearchapi.dtos.flightInfoRequestDtos;


import java.time.LocalDateTime;

public class UpdateFlightArrivalDateRequestDto {
    private long flightId;
    private LocalDateTime arrivalDate;

    public UpdateFlightArrivalDateRequestDto(long flightId, LocalDateTime arrivalDate) {
        this.flightId = flightId;
        this.arrivalDate = arrivalDate;
    }

    public UpdateFlightArrivalDateRequestDto() {
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
