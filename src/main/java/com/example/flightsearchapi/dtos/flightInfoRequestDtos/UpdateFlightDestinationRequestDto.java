package com.example.flightsearchapi.dtos.flightInfoRequestDtos;

public class UpdateFlightDestinationRequestDto {
    private long flightId;
    private long destinationAirportId;

    public UpdateFlightDestinationRequestDto(long flightId, long destinationAirportId) {
        this.flightId = flightId;
        this.destinationAirportId = destinationAirportId;
    }

    public UpdateFlightDestinationRequestDto() {
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public long getDestinationAirportId() {
        return destinationAirportId;
    }

    public void setDestinationAirportId(long destinationAirportId) {
        this.destinationAirportId = destinationAirportId;
    }
}
