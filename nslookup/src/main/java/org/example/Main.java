package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("NSLOOKUP\n");
        while(true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter hostname or IP-address, multiple seperated with space");
            String input = sc.nextLine();

            if(input.contains(" ")){
                String[] inputarray = input.split(" ");

                for(int i = 0; i< inputarray.length; i++){
                    try {
                        System.out.println(nslookup.nslookup(inputarray[i]));
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