package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import constants.GameConstants;
import util.ResourceLoader;

public class Monkey {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private BufferedImage monkeyImage;

    private int aiDirection; // 1:向下, -1:向上
    private int aiVerticalSpeed;
    private int aiHorizontalSpeed = 2;
    private int minY, maxY, minX, maxX;

    public Monkey() {
        monkeyImage = ResourceLoader.loadImage(GameConstants.MONKEY_IMAGE_PATH);
        width = monkeyImage.getWidth();
        height = monkeyImage.getHeight();
        x = GameConstants.WIDTH - width - 50;
        y = GameConstants.HEIGHT * 2 / 3;
        speed = 3;
        aiDirection = 1;
        aiVerticalSpeed = 2;
        minY = GameConstants.HEIGHT * 2 / 3 - 200;
        maxY = GameConstants.HEIGHT * 2 / 3 + 200;
        minX = 100;
        maxX = GameConstants.WIDTH - width - 50;
    }

    public void update() {
        // up and down
        y += aiDirection * aiVerticalSpeed;
        if (y < minY) {
            y = minY;
            aiDirection = 1;
        } else if (y > maxY) {
            y = maxY;
            aiDirection = -1;
        }
        // left and right
        x += aiHorizontalSpeed;
        if (x > maxX || x < minX) {
            aiHorizontalSpeed = -aiHorizontalSpeed;
        }
    }

    public void resetPosition() {
        x = GameConstants.WIDTH;
        y = GameConstants.HEIGHT - height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Getter and Setter methods
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

    public BufferedImage getMonkeyImage() {
        return monkeyImage;
    }

}
