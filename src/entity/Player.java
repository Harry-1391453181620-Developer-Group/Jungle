package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import constants.GameConstants;
import util.ResourceLoader;

public class Player {
    private int x;
    private int y;
    private int width; 
    private int height;
    private int speedX;
    private int speedY;
    private int hearts;
    private boolean isJumping;
    private long jumpStartTime;
    private BufferedImage playerImage;

    public Player() {
        playerImage = ResourceLoader.loadImage(GameConstants.PLAYER_IMAGE_PATH);
        width = playerImage.getWidth();
        height = playerImage.getHeight();
        x = 50;
        y = GameConstants.HEIGHT / 3; // 三分之一高度
        hearts = GameConstants.TOTAL_HEARTS;
        isJumping = false;
    }
    public void moveLeft() {
        x -= GameConstants.MOVE_SPEED;
        if (x < 0) {
            x = 0; // Prevent moving out of bounds
        }
    }
    public void moveRight() {
        x += GameConstants.MOVE_SPEED;
        if (x > GameConstants.WIDTH) {
            x = GameConstants.WIDTH - width; // Prevent moving out of bounds
        }
    }
    public void jump() {
        if (!isJumping) {
            isJumping = true;
            speedY = -GameConstants.JUMP_SPEED;
            jumpStartTime = System.currentTimeMillis();
        }
    }
    public void highJump() {
        if (!isJumping) {
            isJumping = true;
            speedY = -GameConstants.HIGH_JUMP_SPEED;
            jumpStartTime = System.currentTimeMillis();
        }
    }
    public void update() {
        y += speedY;
        speedY += 1; // Gravity effect
        int minY = GameConstants.HEIGHT / 3;
        if (y >= GameConstants.PLAYER_GROUND_Y) {
            y = GameConstants.PLAYER_GROUND_Y;
            isJumping = false;
            speedY = 0;
        }
        if (y < minY) { // 不低于初始高度
            y = minY;
            speedY = 0;
        }
        if (isJumping && System.currentTimeMillis() - jumpStartTime > GameConstants.HIGH_JUMP_INTERVAL) {
            speedY = -GameConstants.JUMP_SPEED;
        }
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getHearts() {
        return hearts;
    }
    public void setHearts(int hearts) {
        this.hearts = hearts;
    }
    public boolean isJumping() {
        return isJumping;
    }
    public BufferedImage getPlayerImage() {
        return playerImage;
    }
}
