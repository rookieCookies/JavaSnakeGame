import javax.swing.*;
import java.awt.*;

public class MainFrame extends JDialog {
    int SCREEN_WIDTH = 800;
    int SCREEN_HEIGHT = 800;
    public int start() {
        MainPanel panel = new MainPanel(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.add(panel);
        this.setTitle("Snake Game");
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        return 2;
    }
}
