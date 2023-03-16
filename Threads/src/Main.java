class CustomThread extends Thread {

    private String word;

    public CustomThread(String name, String word) {
        super(name);
        this.word = word;
    }

    @Override
    public void run() {
        int i = 0;
        while (i<10) {
            System.out.println(getName() + " - " + word + " - " + System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        CustomThread thread1 = new CustomThread("Thread 1", "LBS");
        CustomThread thread2 = new CustomThread("Thread 2", "vier");
        thread1.start();
        thread2.start();
    }
}
