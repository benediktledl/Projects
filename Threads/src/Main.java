import java.util.Scanner;

class CustomThread extends Thread {
    private String word;

    public CustomThread(String name, String word) {
        super(name);
        this.word = word;
    }

    @Override
    public void run() {
        System.out.println(getName() + " - " + word + " - " + System.currentTimeMillis());
    }
}

class CustomRunnable implements Runnable {
    private String name;
    private String word;

    public CustomRunnable(String name, String word) {
        this.name = name;
        this.word = word;
    }

    @Override
    public void run() {
        System.out.println(name + " - " + word + " - " + System.currentTimeMillis());
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while(choice != 3) {
            System.out.println("Menue: (1 fuer Thread-Klasse, 2 fuer Runnable-Interface, 3 fuer Beenden):");
            choice = scanner.nextInt();

            long startTime = System.currentTimeMillis();

            if (choice == 1) {
                CustomThread thread1 = new CustomThread("Thread 1", "LBS");
                CustomThread thread2 = new CustomThread("Thread 2", "vier");
                thread1.start();
                thread2.start();
                thread1.join();
                thread2.join();
            } else if (choice == 2) {
                Thread thread1 = new Thread(new CustomRunnable("Thread 1", "LBS"));
                Thread thread2 = new Thread(new CustomRunnable("Thread 2", "vier"));
                thread1.start();
                thread2.start();
                thread1.join();
                thread2.join();
            } else if (choice == 3){
                System.out.println("Okay, beenden...");
                System.exit(0);
            } else {
                System.out.println("Ungueltige Auswahl");
                System.exit(1);
            }

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("Ausfuehrungszeit: " + elapsedTime + " ms");

        }
    }
}
