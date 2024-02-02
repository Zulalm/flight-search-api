package com.example.flightsearchapi.dtos.flightInfoRequestDtos;

import java.time.LocalDateTime;

public class AddFlightInfoRequestDto {
    private long destinationAirportId;
    private long originAirportId;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;

    public AddFlightInfoRequestDto() {
    }

    public AddFlightInfoRequestDto(long destinationAirportId, long originAirportId, LocalDateTime departureDate, LocalDateTime arrivalDate) {
        this.destinationAirportId = destinationAirportId;
        this.originAirportId = originAirportId;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
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

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
