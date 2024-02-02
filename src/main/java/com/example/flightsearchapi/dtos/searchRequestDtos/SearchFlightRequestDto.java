package com.example.flightsearchapi.dtos.searchRequestDtos;

import java.time.LocalDate;

public class SearchFlightRequestDto {
    private long originId;
    private long destinationId;
    private LocalDate departureDate;
    private LocalDate returnDate;

    public SearchFlightRequestDto() {
    }

    public SearchFlightRequestDto(long originId, long destinationId, LocalDate departureDate, LocalDate returnDate) {
        this.originId = originId;
        this.destinationId = destinationId;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
    }

    public long getOriginId() {
        return originId;
    }

    public void setOriginId(long originId) {
        this.originId = originId;
    }

    public long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(long destinationId) {
        this.destinationId = destinationId;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
