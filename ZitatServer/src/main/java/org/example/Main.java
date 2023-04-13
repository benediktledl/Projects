import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Main {
    private static final int BUFSIZE = 508;

    public static void main(String[] args) throws Exception {
        int port = 50000;
        String code = "geheimcode";
        org.example.Message  message = null;
        try (DatagramSocket socket = new DatagramSocket(port)) {
            DatagramPacket packet = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);

            System.out.println("Listening on Port " + port + "...");

            while (true) {
                socket.receive(packet);
                String data = new String(packet.getData(), 0, packet.getLength());
                message = deserialize(data);
                if(message.getCode().equals(code)) {
                    System.out.println("Zeitpunkt: " + message.getTimestamp() + " Nachricht: " + message.getMessage());

                    String sendmessage = getZitat();
                    String sendzitat = sendmessage.substring(0, sendmessage.indexOf("#")-1);

                    org.example.Zitat zitat = new org.example.Zitat(getAuthor(sendmessage), LocalDateTime.now(), sendzitat);

                    String json = serialize(zitat);
                    try (DatagramSocket sendsocket = new DatagramSocket()) {
                        InetAddress addr = packet.getAddress();
                        DatagramPacket sendpacket = new DatagramPacket(new byte[BUFSIZE], BUFSIZE, addr, packet.getPort());
                        byte[] senddata = json.getBytes();
                        sendpacket.setData(senddata);
                        sendpacket.setLength(senddata.length);
                        sendsocket.send(sendpacket);

                        System.out.println("Zitat gesendet");
                    }
                }else{
                    System.out.println("Daten nicht genehmigt, falscher Code");
                    continue;
                }

            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    private static org.example.Message deserialize(String data) {
        ObjectMapper mapper = new ObjectMapper();
        org.example.Message message = new org.example.Message();
        try {
            message = mapper.readValue(data,org.example.Message.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return message;
    }

    private static String getZitat() throws IOException {
        Path path = Paths.get("zitate.txt");
        List<String> zeilen = Files.readAllLines(path);
        Random random = new Random();
        int index = random.nextInt(zeilen.size());

        return zeilen.get(index);
    }

    private static String getAuthor(String zitat) {
        int trennIndex = zitat.lastIndexOf("#");
        return zitat.substring(trennIndex + 1).trim();
    }

    private static String serialize(org.example.Zitat msg) {
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
