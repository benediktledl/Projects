package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Map<Integer, Artikel> artikelMap = new HashMap<>();
        artikelMap.put(1, new Artikel("L3R43", "Gaming-Maus", 19.90));
        artikelMap.put(2, new Artikel("L3R44", "Monitor", 319.99));
        artikelMap.put(3, new Artikel("L2R34", "Docking-Station", 39.99));
        artikelMap.put(4, new Artikel("L3R38", "IPhone 13", 1490.00));
        artikelMap.put(5, new Artikel("L3R38", "Android", 650.90));

        ServerSocket serverSocket = new ServerSocket(7777);
        System.out.println("Lauschen auf Port 7777...");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client-Verbindung aufgebaut");

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            int id = objectInputStream.readInt();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            if (artikelMap.containsKey(id)) {
                Artikel artikel = artikelMap.get(id);
                objectOutputStream.writeObject(artikel);
                System.out.println("Client hat ArtikelID: " + id + " angefragt");
            } else {
                objectOutputStream.writeObject(null);
                System.out.println("ArtikelID: " + id + " nicht gefunden");
            }

            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        }
    }
}
