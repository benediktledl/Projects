import java.util.Vector;
import java.util.Random;

/**
 * Erzeuger-Klasse, erzeugt Zufallszahlen die vom Verbraucher verwendet werden
 */
class Erzeuger implements Runnable {
    private Vector<Integer> zahlen;
    private Object lock;
    private boolean pause;
    private static final int MAX_ZAHLEN = 25;

    /**
     * Erstellt einen neuen Erzeuger.
     *
     * @param zahlen Vektor zum Speichern der Zahlen
     * @param lock Das Lockobjekt
     */
    public Erzeuger(Vector<Integer> zahlen, Object lock) {
        this.zahlen = zahlen;
        this.lock = lock;
        this.pause = false;
    }

    /**
     * Hauptmethode
     */
    public void run() {
        Random random = new Random();
        while (true) {
            synchronized (lock) {
                if (!pause && zahlen.size() < MAX_ZAHLEN) {
                    zahlen.add(random.nextInt(101));
                    lock.notifyAll();
                } else if (!pause) {
                    System.out.println("Lager voll, Produktion muss manuell gestartet werden.");
                    System.out.println();
                    pause = true;
                }
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Pause toggeln
     */
    public void togglePause() {
        pause = !pause;
        if(pause) {
            System.out.println("Produktion pausiert");
        }else{
            System.out.println("Produktion laeuft wieder");
        }
    }

    /**
     * Gibt aus ob gepaust ist
     * @return bool pause
     */
    public boolean isPaused() {
        return pause;
    }
}