package org.example;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class Message {
    private String user;
    private String timestamp;
    private String message;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public Message(String user, LocalDateTime timestamp, String message) throws ParseException {
        this.user = user;
        this.timestamp = timestamp.format(formatter);
        this.message = message;
    }



    public String getUser() {
        return user;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public String getMessage() {
        return message;
    }


}

