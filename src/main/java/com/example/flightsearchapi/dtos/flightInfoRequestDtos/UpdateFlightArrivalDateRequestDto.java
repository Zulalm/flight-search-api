package com.example.flightsearchapi.dtos.flightInfoRequestDtos;


import java.time.LocalDate;
import java.time.LocalTime;

public class UpdateFlightArrivalDateRequestDto {
    private long flightId;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;

    public UpdateFlightArrivalDateRequestDto(long flightId, LocalDate arrivalDate, LocalTime arrivalTime) {
        this.flightId = flightId;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
    }

    public UpdateFlightArrivalDateRequestDto() {
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
