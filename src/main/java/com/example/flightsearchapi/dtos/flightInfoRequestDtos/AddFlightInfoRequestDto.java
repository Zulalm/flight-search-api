package com.example.flightsearchapi.dtos.flightInfoRequestDtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddFlightInfoRequestDto {
    private long destinationAirportId;
    private long originAirportId;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;

    public AddFlightInfoRequestDto() {
    }

    public AddFlightInfoRequestDto(long destinationAirportId, long originAirportId, LocalDate departureDate, LocalTime departureTime, LocalDate arrivalDate, LocalTime arrivalTime) {
        this.destinationAirportId = destinationAirportId;
        this.originAirportId = originAirportId;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
    }

    public long getDestinationAirportId() {
        return destinationAirportId;
    }

    public void setDestinationAirportId(long destinationAirportId) {
        this.destinationAirportId = destinationAirportId;
    }

    public long getOriginAirportId() {
        return originAirportId;
    }

    public void setOriginAirportId(long originAirportId) {
        this.originAirportId = originAirportId;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
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
