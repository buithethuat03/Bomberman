package entity;

import Maps.Brick;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class Entity {
    protected int x, y;
    protected int speed;
    // thêm 2 thuộc tính này để check va chạm
    //==================================//
    public Rectangle solidArea;
    public boolean collisionOn = false;
    //==================================//
    protected String status;
    protected BufferedImage up1, up2, up3, left1, left2, left3, down1, down2, down3, right1, right2, right3;
    protected BufferedImage dead;
    protected String direction;

    public int spriteCounter = 0;

    public int spriteNum = 1;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
    public abstract String update(List<Bomb> bombs, List<Brick> bricks);
    public abstract void draw(Graphics2D g2);
}
