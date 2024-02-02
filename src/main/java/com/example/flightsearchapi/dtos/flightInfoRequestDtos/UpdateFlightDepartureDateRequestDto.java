package com.example.flightsearchapi.dtos.flightInfoRequestDtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class UpdateFlightDepartureDateRequestDto {
    private long flightId;
    private LocalDate departureDate;
    private LocalTime departureTime;

    public UpdateFlightDepartureDateRequestDto(long flightId, LocalDate departureDate, LocalTime departureTime) {
        this.flightId = flightId;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
    }

    public UpdateFlightDepartureDateRequestDto() {
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
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
}
