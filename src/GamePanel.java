import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_HEIGHT * SCREEN_WIDTH) / UNIT_SIZE;
    static final int DELAY = 75;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten = 0;
    int appleX = 0;
    int appleY = 0;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random rnd;


    GamePanel() {
        rnd = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        startGame();

    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {
        if (running) {
          /*  for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

           */
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.ORANGE);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);


                }

            }
            //SCORE COUNTER
            g.setColor(Color.ORANGE);
            g.setFont(new Font("Sans Serif",Font.BOLD,30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            String score = "Score: "+applesEaten;
            g.drawString(score,(SCREEN_WIDTH-metrics.stringWidth(score))/2,30);
        } else {
             gameOver(g);

        }
    }

    public void newApple() {
        appleX = rnd.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = rnd.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];

        }
        switch (direction) {
            case 'U':          //snake to go up
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':          //snake to go down
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':          //snake to go down
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':          //snake to go down
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            applesEaten++;
            bodyParts++;
            newApple();
        }

    }

    public void checkCollisions() {
        //To check if head bites body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        //if head touches left border
        if (x[0] < 0) {
            running = false;
        }
        //if head touches right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        //if head touches bottom border
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        //if head touches top border
        if (y[0] < 0) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        //Game Over text
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Sans Serif",Font.BOLD,75));
FontMetrics metrics = getFontMetrics(g.getFont());
g.drawString("Game Over",(SCREEN_WIDTH-metrics.stringWidth("Game over"))/2,SCREEN_HEIGHT/2);
        g.setFont(new Font("Sans Serif",Font.BOLD,20));
        g.drawString("YOUR SCORE:  "+applesEaten,((SCREEN_WIDTH-metrics.stringWidth("Game over")/2+UNIT_SIZE)/2),SCREEN_HEIGHT/2+50);
      /*  for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }

       */
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();


    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }

        }
    }
}
