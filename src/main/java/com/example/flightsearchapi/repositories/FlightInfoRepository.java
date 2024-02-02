package com.example.flightsearchapi.repositories;

import com.example.flightsearchapi.models.Airport;
import com.example.flightsearchapi.models.FlightInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightInfoRepository extends JpaRepository<FlightInfo, Long> {
    List<FlightInfo> findByOriginAndDestinationAndDepartureDate(Airport origin, Airport destination, LocalDate date);

}
