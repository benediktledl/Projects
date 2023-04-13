package org.example;

import java.io.*;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        Vector<LookupResult> lookupResults = new Vector<LookupResult>();

        // load lookup results from file if it exists
        File file = new File("lookup_results.ser");
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                lookupResults = (Vector<LookupResult>) in.readObject();
                System.out.println("Loaded " + lookupResults.size() + " lookup results from file.");
                for (LookupResult result : lookupResults) {
                    System.out.println(result.getInput() + " -> " + result.getOutput());
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading lookup results: " + e.getMessage());
            }
        }

        System.out.println("NSLOOKUP\n");
        while(true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter hostname or IP-address, multiple seperated with space");
            String input = sc.nextLine();

            if(input.contains(" ")){
                String[] inputarray = input.split(" ");

                for(int i = 0; i< inputarray.length; i++){
                    try {
                        String result = nslookup.nslookup(inputarray[i]);
                        System.out.println(result);
                        lookupResults.add(new LookupResult(inputarray[i], result));
                    } catch (Exception e) {
                        System.out.println("Error: " + e);
                    }
                    System.out.println("");
                }

                continue;
            }

            try {
                String result = nslookup.nslookup(input);
                System.out.println(result);
                lookupResults.add(new LookupResult(input, result));
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            System.out.println("");

            // save lookup results to file
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                out.writeObject(lookupResults);
                System.out.println("Saved " + lookupResults.size() + " lookup results to file.");
            } catch (IOException e) {
                System.out.println("Error saving lookup results: " + e.getMessage());
            }
        }

    }
}
