package com.example.demo.ms.two.controllers;

import com.example.demo.ms.two.models.DataResponse;
import com.example.demo.ms.two.models.GreetingResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest(DataController.class)
class DataControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RestTemplate mockRestTemplate;

    @Test
    void testGetData() throws Exception {

        final GreetingResponse msOneMockResponse = new GreetingResponse("testApplicationId", "testMessage");
        when(mockRestTemplate.getForObject(anyString(), any())).thenReturn(msOneMockResponse);

        final MockHttpServletResponse response = mockMvc.perform(get("/data")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        DataResponse dataResponse = objectMapper.readValue(
                response.getContentAsString(), DataResponse.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(dataResponse.getApplicationId()).startsWith("demo-ms-two-");
        assertThat(dataResponse.getGreetingResponse().getApplicationId()).isEqualTo(msOneMockResponse.getApplicationId());
        assertThat(dataResponse.getGreetingResponse().getMessage()).isEqualTo(msOneMockResponse.getMessage());
    }

    @Test
    void testGetData_RestTemplateReturnsNull() throws Exception {
        // Setup
        when(mockRestTemplate.getForObject(anyString(), any())).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/data")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        DataResponse dataResponse = objectMapper.readValue(
                response.getContentAsString(), DataResponse.class);

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(dataResponse.getApplicationId()).startsWith("demo-ms-two-");
        assertThat(dataResponse.getGreetingResponse()).isNull();
    }
}
