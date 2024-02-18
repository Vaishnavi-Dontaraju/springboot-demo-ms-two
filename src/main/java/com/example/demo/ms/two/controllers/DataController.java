package com.example.demo.ms.two.controllers;

import com.example.demo.ms.two.models.DataResponse;
import com.example.demo.ms.two.models.GreetingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class DataController {

    private final String demoMsOneUrl;
    private final String applicationId;
    private final RestTemplate restTemplate;

    public DataController(@Value("${demo.ms.one.url}") String demoMsOneUrl,
                          @Value("${spring.application.name}") String applicationId,
                          RestTemplate restTemplate) {
        this.demoMsOneUrl = demoMsOneUrl;
        this.applicationId = applicationId;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/data")
    public DataResponse getData() {
        log.info("Received request to /data");
        String url = demoMsOneUrl + "/one/greeting?name=" + applicationId;
        GreetingResponse msOneResponse = restTemplate.getForObject(url, GreetingResponse.class);


        DataResponse finalResponse = new DataResponse(applicationId, msOneResponse);

        return finalResponse;
    }


}
