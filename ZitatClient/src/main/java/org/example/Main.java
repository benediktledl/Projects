package org.example;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalDateTime;

public class Main {
    private static final int BUFSIZE = 508;

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 50000;
        Message msg = new Message("LBS4-User", LocalDateTime.now(), "Das ist ein Test.");
        String message = serialize(msg);

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress addr = InetAddress.getByName(host);
            DatagramPacket packet = new DatagramPacket(new byte[BUFSIZE], BUFSIZE, addr, port);
            byte[] data = message.getBytes();
            packet.setData(data);
            packet.setLength(data.length);
            socket.send(packet);
        }
    }

    private static String serialize(Message msg) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(msg);
            System.out.println(json);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}

