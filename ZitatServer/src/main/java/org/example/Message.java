package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Message {
    private String code;
    private String timestamp;
    private String message;

    public Message() {
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString(){
        return "code : "+code + " timestamp : "+timestamp + " message : "+message;
    }

}

