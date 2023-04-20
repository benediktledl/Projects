package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ZitatClient client = new ZitatClient();
        client.startConnection("127.0.0.1", 6666);

        Scanner sc = new Scanner(System.in);
        String domain;
        String response;
        while (true) {
            System.out.println("Domainnamen eingeben.");
            domain = sc.nextLine();
            response = client.sendMessage(domain);
            System.out.println(response);
        }
    }
}