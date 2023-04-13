package org.example;


import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Zitat {
    private String timestamp;
    private String zitat;
    private String author;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Zitat(String author, LocalDateTime timestamp, String zitat) throws ParseException {
        this.author = author;
        this.timestamp = timestamp.format(formatter);
        this.zitat = zitat;
    }

    public String getAuthor() { return this.author; }
    public String getZitat() { return this.zitat; }
    /*
    @Override
    public String toString(){
        return "author : "+author + " zitat : "+zitat;
    }*/

}

