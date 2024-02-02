package com.example.flightsearchapi.dtos.flightInfoRequestDtos;

public class UpdateFlightOriginRequestDto {
    private long flightId;
    private long originAirportId;

    public UpdateFlightOriginRequestDto(long flightId, long originAirportId) {
        this.flightId = flightId;
        this.originAirportId = originAirportId;
    }

    public UpdateFlightOriginRequestDto() {
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public long getOriginAirportId() {
        return originAirportId;
    }

    public void setOriginAirportId(long originAirportId) {
        this.originAirportId = originAirportId;
    }
}
