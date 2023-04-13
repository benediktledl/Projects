package org.example;



import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private String code;
    private String timestamp;
    private String message;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public Message(String code, LocalDateTime timestamp, String message) throws ParseException {
        this.code = code;
        this.timestamp = timestamp.format(formatter);
        this.message = message;
    }



    public String getCode() {
        return this.code;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public String getMessage() {
        return message;
    }


}

