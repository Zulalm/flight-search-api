package com.example.flightsearchapi.services;
import com.example.flightsearchapi.models.FlightInfo;
import com.example.flightsearchapi.repositories.FlightInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FlightScheduledJobsService {
    @Autowired
    private FlightInfoRepository flightInfoRepository;

    public void retrieveFlightData(){
        String url = "mock.api.call";
        RestTemplate restTemplate = new RestTemplate();
        FlightInfo[] result = null;
        try{
            result = restTemplate.getForObject(url, FlightInfo[].class);
            if(result != null){
                for(FlightInfo flight : result){
                    flightInfoRepository.save(flight);
                }
            }
        } catch(Exception e ) {
            System.out.println(e.getMessage());
        }

    }

}
