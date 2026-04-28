package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import constants.GameConstants;
import util.ResourceLoader;

public class Banana {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private boolean exploded;
    private BufferedImage bananaImage;
    
    public Banana() {
        bananaImage = ResourceLoader.loadImage(GameConstants.BANANA_IMAGE_PATH);
        width = bananaImage.getWidth();
        height = bananaImage.getHeight();
        x = GameConstants.WIDTH;
        y = GameConstants.BANANA_GROUND_Y;
        exploded = false;
    }
    public void update() {
        if (!exploded) {
            x -= speed;
            if (x < -width) {
                resetPosition();
            }
        }
    }
    public void resetPosition() {
        x = GameConstants.WIDTH;
        y = GameConstants.BANANA_GROUND_Y;
        exploded = false;
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
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
    public boolean isExploded() {
        return exploded;
    }
    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }
    public BufferedImage getBananaImage() {
        return bananaImage;
    }
}
