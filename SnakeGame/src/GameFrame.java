import javax.swing.*;

public class GameFrame extends JDialog {
    int screenWidth = 800;
    int screenHeight = 800;
    int delay;
    int unitSize;
    GameFrame(int screenWidth, int screenHeight, int delay, int unitSize) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.delay = delay;
        this.unitSize = unitSize;
    }
    public void start() {
        GamePanel panel = new GamePanel(screenWidth, screenHeight, delay, unitSize, this);
        this.add(panel);
        this.setTitle("Snake Game");
        this.setSize(screenWidth, screenHeight);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.pack();
    }
}
