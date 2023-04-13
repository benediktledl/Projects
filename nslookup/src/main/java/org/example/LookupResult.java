package org.example;

import java.io.Serializable;

public class LookupResult implements Serializable {
    private String input;
    private String output;

    public LookupResult(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return input + " -> " + output;
    }
}
