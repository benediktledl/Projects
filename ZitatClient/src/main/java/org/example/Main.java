package org.example;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.*;
import java.time.LocalDateTime;
import java.util.Scanner;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Main {
    private static final int BUFSIZE = 508;

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 50000;
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("Zugangscode angeben");
            String code = sc.nextLine();
            System.out.println("Nachricht eingeben");
            String nachricht = sc.nextLine();
            Message msg = new Message(code, LocalDateTime.now(), nachricht);
            String message = serialize(msg);

            try (DatagramSocket socket = new DatagramSocket()) {
                InetAddress addr = InetAddress.getByName(host);
                DatagramPacket packet = new DatagramPacket(new byte[BUFSIZE], BUFSIZE, addr, port);
                byte[] data = message.getBytes();
                packet.setData(data);
                packet.setLength(data.length);
                socket.send(packet);

                System.out.println("Daten gesendet");

                // Empfangen der Antwort des Servers
                byte[] responseBuffer = new byte[BUFSIZE];
                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                socket.setSoTimeout(1000);
                try {
                    socket.receive(responsePacket);
                    String responseMessage = new String(responsePacket.getData(), 0, responsePacket.getLength());

                    // Ausgabe des Zitats
                    Zitat zitat = deserialize(responseMessage);
                    System.out.println("Zitat: " + zitat.getZitat());
                    System.out.println("Author: " + zitat.getAuthor());
                } catch (SocketTimeoutException e){
                    System.out.println("Response timeout");
                    continue;
                }
            }
        }
    }

    private static Zitat deserialize(String data) {
        ObjectMapper mapper = new ObjectMapper();
        Zitat message = new Zitat();
        try {
            message = mapper.readValue(data,Zitat.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return message;
    }

    private static String serialize(Message msg) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(msg);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}