package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Message {
    private String user;
    private String timestamp;
    private String message;

    public Message() {
    }

    @JsonProperty("user")
    public void setUser(String user) {
        this.user = user;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString(){
        return "user : "+user + " timestamp : "+timestamp + " message : "+message;
    }

}

