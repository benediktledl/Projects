package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
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
            Artikel artikel = (Artikel) objectInputStream.readObject();
            if (artikel == null) {
                System.out.println("ArtikelID: " + id + " wurde vom Server nicht gefunden");
            } else {
                System.out.println("ID: " + artikel.getId());
                System.out.println("Name: " + artikel.getName());
                System.out.println("Preis: " + artikel.getPreis());
            }

            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        }
    }
}
