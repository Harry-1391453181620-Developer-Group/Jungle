import javax.swing.SwingUtilities;

public class Main {
    static void main() {
        SwingUtilities.invokeLater(() -> {
            GameFrame gameFrame = new GameFrame();
            gameFrame.setVisible(true);
        });
    }
}
