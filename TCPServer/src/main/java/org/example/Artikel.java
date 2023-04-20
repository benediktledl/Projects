package org.example;

import java.io.Serializable;

public class Artikel implements Serializable {
    private String positionID;
    private String name;
    private double preis;

    public Artikel(String id, String name, double preis) {
        this.positionID = id;
        this.name = name;
        this.preis = preis;
    }

    public String getId() {
        return positionID;
    }

    public String getName() {
        return name;
    }

    public double getPreis() {
        return preis;
    }
}
