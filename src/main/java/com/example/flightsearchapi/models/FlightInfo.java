package com.example.flightsearchapi.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class FlightInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

    @ManyToOne
    @JoinColumn(name = "destinationAirportId", referencedColumnName = "airportId")
    private Airport destination;
    @ManyToOne
    @JoinColumn(name = "originAirportId", referencedColumnName = "airportId")
    private Airport origin;

    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;

    public FlightInfo() {
    }

    public FlightInfo(Long flightId, Airport destination, Airport origin, LocalDateTime departureDate, LocalDateTime arrivalDate) {
        this.flightId = flightId;
        this.destination = destination;
        this.origin = origin;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public Airport getOrigin() {
        return origin;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
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
