package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("NSLOOKUP\n");
        while(true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter hostname or IP-address");
            String input = sc.nextLine();

            if(input.contains(" ")){
                input.split(" ");
                for(int i = 0; i< input.split(" ").length; i++){
                    try {
                        System.out.println(nslookup.nslookup(input.split(" ")[i]));
                    } catch (Exception e) {
                        System.out.println("Error: " + e);
                    }
                    System.out.println("");
                }
                continue;
            }

            try {
                System.out.println(nslookup.nslookup(input));
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            System.out.println("");
        }
    }
}