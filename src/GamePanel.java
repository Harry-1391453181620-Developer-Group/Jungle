import constants.GameConstants;
import entity.Banana;
import entity.Monkey;
import entity.Player;
import entity.MonkeyBanana;
import util.ResourceLoader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.sound.sampled.Clip;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    private Player player;
    private List<Banana> bananas;
    private List<Monkey> monkeys;
    private Timer gameTimer;
    private BufferedImage backgroundImage;
    private BufferedImage heartImage;
    private BufferedImage explosionImage;
    private Clip musicClip;
    private int score;
    private int highestScore;
    private int gameState; // 0:菜单 1:游戏中 2:游戏结束
    private JButton playAgainButton;
    private JButton quitButton;
    private List<MonkeyBanana> monkeyBananas = new ArrayList<>();
    private long lastThrowTime = 0;
    private long gameOverTime = 0;
    private boolean buttonsShown = false;

    public GamePanel() {
        initGame();
        setFocusable(true);
        requestFocusInWindow();
    }

    private void initGame() {
        // basic init
        setFocusable(true);
        addKeyListener(this);
        gameTimer = new Timer(50, this); // 20FPS刷新

        // entity init
        player = new Player();
        bananas = new ArrayList<>();
        bananas.add(new Banana());
        monkeys = new ArrayList<>();
        monkeys.add(new Monkey());

        // resource loading
        backgroundImage = ResourceLoader.loadImage(GameConstants.BACKGROUND_IMAGE_PATH);
        heartImage = ResourceLoader.loadImage(GameConstants.HEART_IMAGE_PATH);
        explosionImage = ResourceLoader.loadImage(GameConstants.EXPLOSION_IMAGE_PATH);
        musicClip = ResourceLoader.loadAudio(GameConstants.MUSIC_PATH);

        // game status
        score = 0;
        highestScore = loadHighestScore();
        gameState = GameConstants.STATE_MENU;

        // button init
        playAgainButton = new JButton("Play Again");
        quitButton = new JButton("Quit");
        playAgainButton.addActionListener(e -> restartGame());
        quitButton.addActionListener(e -> System.exit(0));
    }

    public void startGame() {
        if (gameState == GameConstants.STATE_MENU) {
            gameState = GameConstants.STATE_GAME;
            if (musicClip != null) {
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            gameTimer.start();
        }
    }

    // restart game
    private void restartGame() {
        gameState = GameConstants.STATE_GAME;
        player = new Player();
        bananas.forEach(Banana::resetPosition);
        monkeys.forEach(Monkey::resetPosition);
        score = 0;
        remove(playAgainButton);
        remove(quitButton);
        musicClip.setFramePosition(0);
        gameTimer.restart();
        buttonsShown = false;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);

        if (gameState == GameConstants.STATE_GAME) {
            drawPlayer(g);
            drawBananas(g);
            drawMonkeys(g);
            drawHearts(g);
            drawScore(g);
        } else if (gameState == GameConstants.STATE_GAME_OVER) {
            drawGameOver(g);
            if (!buttonsShown && System.currentTimeMillis() - gameOverTime > 1000) {
                playAgainButton.setBounds(GameConstants.WIDTH/2 - 80, GameConstants.HEIGHT/2 + 20, 100, 30);
                quitButton.setBounds(GameConstants.WIDTH/2 + 10, GameConstants.HEIGHT/2 + 20, 80, 30);
                add(playAgainButton);
                add(quitButton);
                buttonsShown = true;
            }
        }
    }

    private void drawBackground(Graphics g) {
        for (int x = 0; x < GameConstants.WIDTH; x += GameConstants.BACKGROUND_REPEAT_INTERVAL) {
            g.drawImage(backgroundImage, x, 0, this);
        }
    }

    private void drawPlayer(Graphics g) {
        g.drawImage(
            player.getPlayerImage(), 
            player.getX(), 
            player.getY(), 
            this
        );
    }

    private void drawBananas(Graphics g) {
        // 原有香蕉
        for (Banana banana : bananas) {
            if (banana.isExploded()) {
                g.drawImage(explosionImage, banana.getX(), banana.getY(), banana.getWidth(), banana.getHeight(), this);
            } else {
                g.drawImage(
                    banana.getBananaImage(), 
                    banana.getX(), 
                    banana.getY(), 
                    this
                );
            }
        }

        for (MonkeyBanana mb : monkeyBananas) {
            if (!mb.isExploded()) {
                g.drawImage(mb.getBananaImage(), mb.getX(), mb.getY(), this);
            } else {
                g.drawImage(explosionImage, mb.getX(), mb.getY(), 50, 50, this);
            }
        }
    }

    private void drawMonkeys(Graphics g) {
        for (Monkey monkey : monkeys) {
            g.drawImage(
                monkey.getMonkeyImage(), 
                monkey.getX(), 
                monkey.getY(), 
                this
            );
        }
    }

    private void drawHearts(Graphics g) {
        for (int i = 0; i < player.getHearts(); i++) {
            g.drawImage(
                heartImage, 
                GameConstants.WIDTH - 50 - i * 30, 
                10, 
                30, 
                30, 
                this
            );
        }
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Highest: " + highestScore, 10, 40);
    }

    private void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.drawString("GAME OVER", GameConstants.WIDTH/2-50, GameConstants.HEIGHT/2-30);
        g.drawString("Score: " + score, GameConstants.WIDTH/2-40, GameConstants.HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameState != GameConstants.STATE_GAME) return;

        updateGame();
        checkCollisions();
        repaint();
        score++;
    }

    private void updateGame() {
        player.update();
        bananas.forEach(Banana::update);
        monkeys.forEach(Monkey::update);
        for (MonkeyBanana mb : monkeyBananas) mb.update();
        monkeyBananas.removeIf(mb -> mb.getX() < -100 || mb.isExploded());

        long now = System.currentTimeMillis();
        if (now - lastThrowTime > 5000 && !monkeys.isEmpty()) {
            Monkey monkey = monkeys.get(0);
            monkeyBananas.add(new MonkeyBanana(
                monkey.getX(), monkey.getY(),
                player.getX(), player.getY()
            ));
            lastThrowTime = now;
        }
    }

    private void checkCollisions() {
        Rectangle playerRect = player.getBounds();
        for (Banana banana : bananas) {
            if (banana.isExploded()) continue;
            Rectangle bananaRect = banana.getBounds();
            if (playerRect.intersects(bananaRect)) {
                player.setHearts(player.getHearts() - 2);
                banana.setExploded(true);
                if (player.getHearts() <= 0) {
                    gameOver();
                }
            }
        }
        for (MonkeyBanana mb : monkeyBananas) {
            if (mb.isExploded()) continue;
            if (playerRect.intersects(mb.getBounds())) {
                player.setHearts(player.getHearts() - 1);
                mb.setExploded(true);
                if (player.getHearts() <= 0) {
                    gameOver();
                }
            }
        }
    }

    private void gameOver() {
        gameState = GameConstants.STATE_GAME_OVER;
        if (musicClip != null) musicClip.stop();
        saveHighestScore();
        gameTimer.stop();
        gameOverTime = System.currentTimeMillis();
        buttonsShown = false;
        repaint();
    }

    private int loadHighestScore() {
        try {
            java.nio.file.Path path = java.nio.file.Paths.get("highest_score.txt");
            if (java.nio.file.Files.exists(path)) {
                String s = java.nio.file.Files.readString(path);
                return Integer.parseInt(s.trim());
            }
        } catch (Exception e) {}
        return 0;
    }
    private void saveHighestScore() {
        if (score > highestScore) {
            highestScore = score;
            try {
                java.nio.file.Files.writeString(java.nio.file.Paths.get("highest_score.txt"), String.valueOf(highestScore));
            } catch (Exception e) {}
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameState != GameConstants.STATE_GAME) return;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (e.isShiftDown()) {
                player.highJump();
            } else {
                player.jump();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.moveRight();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
