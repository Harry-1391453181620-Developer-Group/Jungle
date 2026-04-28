package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import constants.GameConstants;
import util.ResourceLoader;

public class MonkeyBanana {
    private int x, y, width, height;
    private double dx, dy;
    private double vx, vy;
    private double speed = 8;
    private boolean exploded;
    private BufferedImage bananaImage;

    public MonkeyBanana(int startX, int startY, int targetX, int targetY) {
        bananaImage = ResourceLoader.loadImage(GameConstants.BANANA_IMAGE_PATH);
        width = bananaImage.getWidth();
        height = bananaImage.getHeight();
        x = startX;
        y = startY;
        exploded = false;
        // 计算速度分量
        dx = targetX - startX;
        dy = targetY - startY;
        double dist = Math.sqrt(dx * dx + dy * dy);
        vx = speed * dx / dist;
        vy = speed * dy / dist;
    }
    public void update() {
        x += vx;
        y += vy;
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    public boolean isExploded() { return exploded; }
    public void setExploded(boolean e) { exploded = e; }
    public int getX() { return x; }
    public int getY() { return y; }
    public BufferedImage getBananaImage() { return bananaImage; }
}