package com.example.flightsearchapi.repositories;

import com.example.flightsearchapi.models.FlightInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightInfoRepository extends JpaRepository<FlightInfo, Long> {
}
