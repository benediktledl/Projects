package org.example;

import java.time.format.DateTimeFormatter;

public class Zitat {
    private String zitat;
    private String author;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Zitat() {
    }

    public String getAuthor() { return this.author; }
    public String getZitat() { return this.zitat; }

}

