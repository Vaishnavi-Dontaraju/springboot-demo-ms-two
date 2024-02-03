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
    @Value("${demo.ms.one.url}")
    private String demoMsOneUrl;

    @Value("${spring.application.name}")
    private String applicationId;

    @GetMapping("/data")
    public DataResponse getData() {
        log.info("Received request to /data");
        String url = demoMsOneUrl + "/one/greeting?name=" + applicationId;
        GreetingResponse msOneResponse = new RestTemplate().getForObject(url, GreetingResponse.class);


        DataResponse finalResponse = new DataResponse(applicationId, msOneResponse);

        return finalResponse;
    }


}
