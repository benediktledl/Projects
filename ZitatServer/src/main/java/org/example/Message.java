package org.example;

public class Message {
    private String code;
    private String message;
    private String author;
    private String timestamp;

    public Message() {
    }

    public String getCode() {
        return this.code;
    }
    public String getTimestamp() {
        return this.timestamp;
    }
    public String getMessage() {
        return this.message;
    }

    public void setAuthor(String author) {
        this.author = author;
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

