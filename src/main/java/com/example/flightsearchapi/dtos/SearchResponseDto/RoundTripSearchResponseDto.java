package com.example.flightsearchapi.dtos.SearchResponseDto;

import com.example.flightsearchapi.models.FlightInfo;

import java.util.List;

public class RoundTripSearchResponseDto {
    private List<FlightInfo> outboundFlights;
    private List<FlightInfo> inboundFlights;

    public RoundTripSearchResponseDto() {
    }

    public RoundTripSearchResponseDto(List<FlightInfo> outboundFlights, List<FlightInfo> inboundFlights) {
        this.outboundFlights = outboundFlights;
        this.inboundFlights = inboundFlights;
    }

    public List<FlightInfo> getOutboundFlights() {
        return outboundFlights;
    }

    public void setOutboundFlights(List<FlightInfo> outboundFlights) {
        this.outboundFlights = outboundFlights;
    }

    public List<FlightInfo> getInboundFlights() {
        return inboundFlights;
    }

    public void setInboundFlights(List<FlightInfo> inboundFlights) {
        this.inboundFlights = inboundFlights;
    }
}
