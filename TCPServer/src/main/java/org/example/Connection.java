package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class Connection extends Thread{
    final Socket socket;
    final Map<Integer, Artikel> artikelMap;
    int requestID;

    public Connection(Map<Integer, Artikel> artikelMap, Socket socket)
    {
        this.artikelMap = artikelMap;
        this.socket = socket;
    }
    @Override
    public void run() {
        System.out.println("ClientHandler Thread initialisiert");

        Map<Integer, Artikel> artikelMap = this.artikelMap;


        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int id = 0;
        try {
            id = objectInputStream.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (artikelMap.containsKey(id)) {
            Artikel artikel = artikelMap.get(id);
            try {
                objectOutputStream.writeObject(artikel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Client hat ArtikelID: " + id + " angefragt");
        } else {
            try {
                objectOutputStream.writeObject(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("ArtikelID: " + id + " nicht gefunden");
        }

        try {
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}


