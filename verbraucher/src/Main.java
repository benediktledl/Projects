import java.util.Vector;
import java.util.Scanner;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Drücke die [Enter]-Taste, um die Produktion anzuhalten/fortzusetzen.");
        Vector<Integer> zahlen = new Vector<>();
        Object lock = new Object();

        Erzeuger erzeuger = new Erzeuger(zahlen, lock);
        Thread erzeugerThread = new Thread(erzeuger);
        Thread verbraucher = new Thread(new Verbraucher(zahlen, lock, erzeuger));

        erzeugerThread.start();
        verbraucher.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            scanner.nextLine();
            erzeuger.togglePause();
        }
    }

}