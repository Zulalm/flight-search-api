package com.example.flightsearchapi.components;

import com.example.flightsearchapi.services.FlightScheduledJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class FlightScheduledJobs {

    @Autowired
    private FlightScheduledJobsService flightScheduledJobsService;

    @Scheduled(cron = "0 0 0 * * *", zone = "UTC")
    public void retrieveFlightData(){
        flightScheduledJobsService.retrieveFlightData();

    }

}
