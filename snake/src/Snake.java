import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Snake extends JPanel implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int DOT_SIZE = 10;
    private static final int MAX_DOTS = (WIDTH * HEIGHT) / DOT_SIZE;
    private static final int RAND_POS = WIDTH / DOT_SIZE;
    private static final int DELAY = 100;

    private LinkedList<Point> snake;
    private Point apple;
    private int dots;
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    private Thread gameThread;

    public Snake() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        initGame();
    }

    private void initGame() {
        snake = new LinkedList<>();
        addFirstDots();
        createApple();
        dots = 3;
        inGame = true;
        //repaint();
    }

    private void addFirstDots() {
        for (int i = 0; i < dots; i++) {
            snake.addLast(new Point((WIDTH / 2) - (i * DOT_SIZE), HEIGHT / 2));
        }
    }

    private void createApple() {
        Random rand = new Random();
        int x = rand.nextInt(RAND_POS) * DOT_SIZE;
        int y = rand.nextInt(RAND_POS) * DOT_SIZE;
        apple = new Point(x, y);
    }

    private void checkApple() {
        if (snake.getFirst().equals(apple)) {
            snake.addFirst(new Point(-1, -1));
            createApple();
            dots++;
        }
    }

    private void checkCollisions() {
        Point head = snake.getFirst();
        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT) {
            inGame = false;
        }
        for (Point p : snake.subList(1, snake.size())) {
            if (head.equals(p)) {
                inGame = false;
                break;
            }
        }
    }

    private void move() {
        Point head = snake.getFirst();
        Point newHead = new Point(head);
        if (leftDirection) {
            newHead.x -= DOT_SIZE;
        }
        if (rightDirection) {
            newHead.x += DOT_SIZE;
        }
        if (upDirection) {
            newHead.y -= DOT_SIZE;
        }
        if (downDirection) {
            newHead.y += DOT_SIZE;
        }
        snake.removeLast();
        snake.addFirst(newHead);
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font font = new Font("Verdana", Font.BOLD, 18);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (WIDTH - g.getFontMetrics().stringWidth(msg)) / 2, HEIGHT / 2);
        gameThread.interrupt();
    }

    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("paintComponent called");
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (inGame) {
            drawSnake(g);
            drawApple(g);
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    private void drawSnake(Graphics g) {
        g.setColor(Color.WHITE); // Beispiel: ändere die Farbe in weiß
        for (Point p : snake) {
            g.fillRect(p.x, p.y, DOT_SIZE, DOT_SIZE);
        }
    }

    private void drawApple(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(apple.x, apple.y, DOT_SIZE, DOT_SIZE);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!Thread.currentThread().isInterrupted()) {
            if (inGame) {
                checkApple();
                checkCollisions();
                move();
            }
            repaint();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
            leftDirection = true;
            upDirection = false;
            downDirection = false;
        }
        if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
            rightDirection = true;
            upDirection = false;
            downDirection = false;
        }
        if ((key == KeyEvent.VK_UP) && (!downDirection)) {
            upDirection = true;
            rightDirection = false;
            leftDirection = false;
        }
        if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
            downDirection = true;
            rightDirection = false;
            leftDirection = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // not used
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // not used
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new Snake());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
