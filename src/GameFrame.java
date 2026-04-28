import constants.GameConstants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    private JButton startButton;

    public GameFrame() {
        // 窗口基础配置
        setTitle("Jungle Adventure"); // 游戏标题
        setSize(GameConstants.WIDTH, GameConstants.HEIGHT); // 使用常量定义的窗口尺寸
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭窗口时退出程序
        setLocationRelativeTo(null); // 窗口居中显示
        setResizable(false); // 禁止调整窗口大小

        // 初始化游戏面板
        gamePanel = new GamePanel();
        gamePanel.setLayout(null); // 自定义布局，便于按钮定位

        // 初始化开始按钮（主菜单用）
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setBounds(
            GameConstants.WIDTH / 2 - 100,
            GameConstants.HEIGHT / 2 - 25,
            200, 50
        ); // 居中放置按钮

        // 绑定开始按钮事件：点击后隐藏按钮并启动游戏
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.remove(startButton); // 移除开始按钮
                gamePanel.startGame(); // 调用游戏面板的启动方法
                gamePanel.repaint(); // 重绘面板
            }
        });

        // 将组件添加到面板
        gamePanel.add(startButton);
        add(gamePanel); // 将游戏面板添加到窗口

        // 初始状态显示主菜单（仅显示开始按钮）
        setVisible(true);
    }
}

