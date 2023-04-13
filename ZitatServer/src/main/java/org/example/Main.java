package org.example;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Main {
    private static final int BUFSIZE = 508;

    public static void main(String[] args) {
        int port = 50000;
        Message  message = null;
        try (DatagramSocket socket = new DatagramSocket(port)) {
            DatagramPacket packet = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);

            while (true) {
                socket.receive(packet);
                String data = new String(packet.getData(), 0, packet.getLength());
                message = deserialize(data);
                System.out.println("Empfangene Daten: " + data);
                System.out.println("Daten des neuen Objektes: " + message);
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
    private static Message deserialize(String data) {
        ObjectMapper mapper = new ObjectMapper();
        Message message = new Message();
        try {
            message = mapper.readValue(data,Message.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return message;
    }


}
