import java.util.Vector;

/**
 * Verbraucher-Klasse, gibt die Zahlen vom Erzeuger auzf der Konsole aus.
 */
class Verbraucher implements Runnable {
    private Vector<Integer> zahlen;
    private Object lock;
    private Erzeuger erzeuger;

    /**
     * Erstellt einen neuen Verbraucher.
     *
     * @param zahlen Vektor der Zahlen
     * @param lock Lock-objekt
     */
    public Verbraucher(Vector<Integer> zahlen, Object lock, Erzeuger erzeuger) {
        this.zahlen = zahlen;
        this.lock = lock;
        this.erzeuger = erzeuger;
    }

    /**
     * Hauptmethode
     */
    public void run() {
        int counter = 0;
        while (true) {
            synchronized (lock) {
                while (zahlen.isEmpty()) {
                    try {
                        if (erzeuger.isPaused()) {
                            System.out.println("Zahlenvorrat aufgebraucht. Bitte starte die Produktion wieder.");
                        }
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int zahl = zahlen.remove(0);
                System.out.print(zahl + " ");
                counter++;
                if (counter == 10) {
                    counter = 0;
                    System.out.println();
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}