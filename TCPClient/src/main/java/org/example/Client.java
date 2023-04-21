package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Client {
    //private static HashMap<Integer, Artikel> hashMap = new HashMap<>();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("ArtikelID suchen: ");

            int id = scanner.nextInt();

            Socket socket = new Socket("localhost", 7777);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeInt(id);
            objectOutputStream.flush();

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Map<Integer, Artikel> artikelMap = new HashMap<>();
            artikelMap.put(artikelMap.size() + 1, (Artikel) objectInputStream.readObject());
            Artikel artikel = artikelMap.get(artikelMap.size());
            if (artikel == null) {
                System.out.println("ArtikelID: " + id + " wurde vom Server nicht gefunden");
            } else {
                System.out.println("Lager: " + artikel.getId());
                System.out.println("Name: " + artikel.getName());
                System.out.println("Preis: " + artikel.getPreis());
            }

            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        }
    }
}
