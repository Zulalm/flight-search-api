package com.example.flightsearchapi.dtos.flightInfoRequestDtos;

import java.time.LocalDateTime;

public class UpdateFlightDepartureDateRequestDto {
    private long flightId;
    private LocalDateTime departureDate;

    public UpdateFlightDepartureDateRequestDto(long flightId, LocalDateTime departureDate) {
        this.flightId = flightId;
        this.departureDate = departureDate;
    }

    public UpdateFlightDepartureDateRequestDto() {
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }
}
