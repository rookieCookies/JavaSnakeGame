import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    final int SCREEN_WIDTH;
    final int SCREEN_HEIGHT;
    int UNIT_SIZE = 80;
    final int GAME_UNITS;
    int DELAY = 135;
    final int[] snakeLocationX;
    final int[] snakeLocationY;
    int snakeBodyParts = 1;
    char snakeDirection = 'R';
    int score = 1;
    int appleLocationX;
    int appleLocationY;
    boolean running = false;
    boolean win = false;
    Timer timer;
    Random random;
    GameFrame frame;


    public GamePanel(int screenWidth, int screenHeight, int delay, int unitSize, GameFrame frame) {
        this.SCREEN_WIDTH = screenWidth - 3;
        this.SCREEN_HEIGHT = screenHeight - 3;
        this.DELAY = delay;
        this.UNIT_SIZE = unitSize;
        this.GAME_UNITS = this.SCREEN_HEIGHT * this.SCREEN_WIDTH / this.UNIT_SIZE / this.UNIT_SIZE + 1;
        this.snakeLocationX = new int[GAME_UNITS];
        this.snakeLocationY = new int[GAME_UNITS];
        random = new Random();
        this.setPreferredSize(new Dimension(this.SCREEN_WIDTH, this.SCREEN_HEIGHT));
        this.setBackground(new Color(36, 19, 52));
        this.setFocusable(true);
        this.addKeyListener(new _KeyAdapter());
        this.frame = frame;
        startGame();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics) {
        if (running) {
            _Draw draw = new _Draw(graphics);
            //draw.drawGrid();
            draw.drawSnake();
            draw.drawApple();
            draw.drawScore(score);
        } else if (win) {
            victoryEndGame(graphics);
        } else gameOver(graphics);
    }

    public void generateApple() {
        int oldAppleLocationX = appleLocationX;
        int oldAppleLocationY = appleLocationY;
        appleLocationX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleLocationY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        for (int i = snakeBodyParts; i > 0; i--) {
            if((snakeLocationX[i] == appleLocationX && snakeLocationY[i] == appleLocationY) || (appleLocationY == oldAppleLocationY && appleLocationX == oldAppleLocationX))
                generateApple();
        }
    }

    public void moveSnake() {
        for (int i = snakeBodyParts; i > 0; i--) {
            snakeLocationX[i] = snakeLocationX[i - 1];
            snakeLocationY[i] = snakeLocationY[i - 1];
        }

        switch (snakeDirection) {
            case 'U' -> snakeLocationY[0] -= UNIT_SIZE;
            case 'D' -> snakeLocationY[0] += UNIT_SIZE;
            case 'R' -> snakeLocationX[0] += UNIT_SIZE;
            case 'L' -> snakeLocationX[0] -= UNIT_SIZE;
        }

        checkCollisions();
    }

    public void checkApple() {
        if (snakeLocationX[0] == appleLocationX && snakeLocationY[0] == appleLocationY) {
            score++;
            if (score == GAME_UNITS) {
                running = false;
                win = true;
            }
            snakeBodyParts++;
            generateApple();
        }
    }

    public void checkCollisions() {
        for (int i = snakeBodyParts; i > 0; i--) {
            if (snakeLocationX[0] == snakeLocationX[i] && snakeLocationY[0] == snakeLocationY[i]) {
                running = false;
                break;
            }
        } // Collision with body parts
        if (snakeLocationX[0] < 0) {
            running = false;
        }
        if (snakeLocationX[0] > SCREEN_WIDTH) {
            running = false;
        }
        if (snakeLocationY[0] < 0) {
            running = false;
        }
        if (snakeLocationY[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
            repaint();
        }
    }

    public void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        generateApple();
    }

    public void gameOver(Graphics graphics) {
        // Game Over
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Minecraft", Font.BOLD, 75));
        FontMetrics fontMetrics = getFontMetrics(graphics.getFont());
        String gameOverText = "YOU DIED!";
        graphics.drawString(gameOverText, (SCREEN_WIDTH - fontMetrics.stringWidth(gameOverText)) / 2, SCREEN_HEIGHT / 2);


        // Display Score!
        graphics.setColor(Color.YELLOW);
        graphics.setFont(new Font("Minecraft", Font.BOLD, 30));
        fontMetrics = getFontMetrics(graphics.getFont());
        String scoreText = "Your score: (" + score + ") if you still care that is";
        graphics.drawString(scoreText, (SCREEN_WIDTH - fontMetrics.stringWidth(scoreText)) / 2, (int) (SCREEN_HEIGHT / 1.8));

        // Restart Info Text!
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Minecraft", Font.BOLD, 15));
        fontMetrics = getFontMetrics(graphics.getFont());
        String mainMenuText = "Press X to go to main menu!";
        graphics.drawString(mainMenuText, (SCREEN_WIDTH - fontMetrics.stringWidth(mainMenuText)) / 2,  (int) (Math.abs(-SCREEN_HEIGHT) - 50));
        this.setBackground(Color.black);

        // Display Score!
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Minecraft", Font.BOLD, 15));
        fontMetrics = getFontMetrics(graphics.getFont());
        String restartText = "Press space to restart!";
        graphics.drawString(restartText, (SCREEN_WIDTH - fontMetrics.stringWidth(restartText)) / 2,  (int) (Math.abs(-SCREEN_HEIGHT) - 25));
        this.setBackground(Color.black);
    }
    public void victoryEndGame(Graphics graphics) {
        // You win!
        graphics.setColor(Color.GREEN);
        graphics.setFont(new Font("Minecraft", Font.BOLD, 75));
        FontMetrics fontMetrics = getFontMetrics(graphics.getFont());
        String youWinText = "YOU WIN!";
        graphics.drawString(youWinText, (SCREEN_WIDTH - fontMetrics.stringWidth(youWinText)) / 2, SCREEN_HEIGHT / 2);
        // Good job on wasting time!
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Minecraft", Font.BOLD, 30));
        fontMetrics = getFontMetrics(graphics.getFont());
        String subText = "Good job on wasting time! lmao idiot";
        graphics.drawString(subText, (SCREEN_WIDTH - fontMetrics.stringWidth(subText)) / 2, (int) (SCREEN_HEIGHT / 1.8));
        this.setBackground(Color.black);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            moveSnake();
            checkApple();
        }
        repaint();
    }

    class _KeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            JComponent comp = (JComponent) e.getSource();
            Window win = SwingUtilities.getWindowAncestor(comp);
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (snakeDirection != 'R') snakeDirection = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if (snakeDirection != 'L') snakeDirection = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if (snakeDirection != 'D') snakeDirection = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if (snakeDirection != 'U') snakeDirection = 'D';
                    break;
                case KeyEvent.VK_SPACE:
                    if (running) return;
                    new GameFrame(SCREEN_WIDTH, SCREEN_HEIGHT, DELAY, UNIT_SIZE).start();
                    win.dispose();
                    break;
                case KeyEvent.VK_X:
                    if (running) return;
                    win.dispose();
                    new MainFrame().start();
                    break;
            }
        }
    }
    class _Draw {
        Graphics graphics;
        public _Draw(Graphics g) {
            this.graphics = g;
        }
        public void drawGrid() {
            graphics.setColor(new Color(30, 30, 30));
            graphics.setColor(Color.white);
            for (int i = 0; i < SCREEN_HEIGHT; i++) {
                graphics.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT + 3);
                graphics.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH + 3, i * UNIT_SIZE);
            }
        }
        public void drawSnake() {
            for (float i = 0; i < snakeBodyParts; i++) {
                int x = (int) i;
                int size = (int) Math.round(UNIT_SIZE * (1.2 - i / snakeBodyParts));
                int color = (int) Math.round(204 * (1.2 - i / snakeBodyParts));
                graphics.setColor(new Color(color, color, 180));
                graphics.fillRect(snakeLocationX[x] + (UNIT_SIZE - size) / 2, snakeLocationY[x] + (UNIT_SIZE - size) / 2, size, size);
                graphics.setColor(Color.black);
                if (i == 0) graphics.setColor(Color.white);
                graphics.fillRect(snakeLocationX[x] + (UNIT_SIZE - size - 15) / 2, snakeLocationY[x] + (UNIT_SIZE - size - 15) / 2, size, size);
            }
        }
        public void drawApple() {
            graphics.setColor(Color.GREEN);
            graphics.fillOval(appleLocationX, appleLocationY + 5, UNIT_SIZE, UNIT_SIZE);
        }
        public void drawScore(float score) {
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("Minecraft", Font.BOLD, 50));
            FontMetrics fontMetrics = getFontMetrics(graphics.getFont());
            String scoreText = score + "/" + GAME_UNITS;
            graphics.drawString(scoreText, (SCREEN_WIDTH - fontMetrics.stringWidth(scoreText)) / 2, SCREEN_HEIGHT / 16);
        }
    }
}
