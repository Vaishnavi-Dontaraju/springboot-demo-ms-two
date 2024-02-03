package com.example.demo.ms.two.models;

public class GreetingResponse {
    private String applicationId;
    private String message;

    public GreetingResponse(){

    }
    public GreetingResponse(String applicationId, String message) {
        this.applicationId = applicationId;
        this.message = message;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
