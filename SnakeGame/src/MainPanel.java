import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class MainPanel extends JPanel implements ActionListener {
    final int SCREEN_WIDTH;
    final int SCREEN_HEIGHT;
    final int UNIT_SIZE = 80;
    final int GAME_UNITS;
    final int REFRESH_DELAY = 10;
    boolean running = false;
    Timer timer;
    public MainPanel(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        this.SCREEN_WIDTH = SCREEN_WIDTH - 3;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT - 3;
        this.GAME_UNITS = this.SCREEN_HEIGHT * this.SCREEN_WIDTH / this.UNIT_SIZE / this.UNIT_SIZE + 1;
        this.setPreferredSize(new Dimension(this.SCREEN_WIDTH, this.SCREEN_HEIGHT));
        this.setBackground(new Color(36, 19, 52));
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(this.SCREEN_WIDTH, this.SCREEN_HEIGHT));
        this.setBackground(new Color(36, 19, 52));
        this.setFocusable(true);
        startGame();

    }
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    private void draw(Graphics graphics) {
        new _Draw(graphics).drawGrid();
        // Creating a new buttons
        Font font = new Font("Minecraft", Font.BOLD, 50);

        JButton easy = new JButton("Easy");
        easy.setPreferredSize(new Dimension((int) (SCREEN_WIDTH / 2.5), SCREEN_HEIGHT / 4));
        easy.setFont(font);
        easy.setBackground(Color.GREEN);
        easy.addActionListener(new EasyButton());

        JButton normal = new JButton("Normal");
        normal.setPreferredSize(new Dimension((int) (SCREEN_WIDTH / 2.5), SCREEN_HEIGHT / 4));
        normal.setFont(font);
        normal.setBackground(Color.ORANGE);
        normal.addActionListener(new NormalButton());

        JButton hard = new JButton("Hard");
        hard.setPreferredSize(new Dimension((int) (SCREEN_WIDTH / 2.5), SCREEN_HEIGHT / 4));
        hard.setFont(font);
        hard.setBackground(Color.RED);
        hard.addActionListener(new HardButton());
        // Creating a panel to add buttons
        JPanel p = this;

        // Adding buttons and textfield to panel
        // using add() method
        p.add(easy);
        p.add(normal);
        p.add(hard);
    }

    public void startGame() {
        running = true;
        timer = new Timer(REFRESH_DELAY, this);
        timer.start();
    }
    private class EasyButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new GameFrame(SCREEN_HEIGHT, SCREEN_WIDTH, 150, 100).start();
            JComponent comp = (JComponent) e.getSource();
            Window win = SwingUtilities.getWindowAncestor(comp);
            win.dispose();
        }
    }
    private class NormalButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new GameFrame(SCREEN_HEIGHT, SCREEN_WIDTH, 110, 80).start();
            JComponent comp = (JComponent) e.getSource();
            Window win = SwingUtilities.getWindowAncestor(comp);
            win.dispose();
        }
    }
    private class HardButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new GameFrame(SCREEN_HEIGHT, SCREEN_WIDTH, 75, 50).start();
            JComponent comp = (JComponent) e.getSource();
            Window win = SwingUtilities.getWindowAncestor(comp);
            win.dispose();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
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
                graphics.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
        }
    }
}
