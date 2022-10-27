package entity;


import Maps.Brick;
import Maps.Portal;
import Panel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Player extends Entity {
    PanelGame gp;
    Key key;
    private BufferedImage dead1, dead2, dead3, dead4, dead5, dead6;
    private int lives = 2;
    private boolean check = true;

    private String colorPlayer = "blue";

    private List<Bomb> bombs;

    private final int COUNT_BOMB = 1;
    private int countBomb = COUNT_BOMB;

    protected int _timeBetweenPutBombs = 0;

    public int typeBomb;

    public Player(PanelGame gp, Key key) {
        this.gp = gp;
        this.key = key;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 8;
        solidArea.width = 20;
        solidArea.height = 24;
        setDefaultValues();
        getPlayerImage();
    }

    public List<Bomb> getBombs() {
        return bombs;
    }
    public void setBombCounts(int bomb) {
        this.countBomb = bomb;
    }
    public int getLives() {
        return lives;
    }

    public String getColor() {
        return colorPlayer;
    }

    public void setColor(String color) {
        this.colorPlayer = color;
    }

    public void getPlayerImage() {
        try {
            if (colorPlayer.equals("blue")) {
                up1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_up_1.png")));
                up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_up_2.png")));
                up3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_up_1.png")));

                left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_left_1.png")));
                left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_left_2.png")));
                left3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_left_1.png")));

                down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_down_1.png")));
                down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_down_2.png")));
                down3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_down_1.png")));

                right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_right_1.png")));
                right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_right_2.png")));
                right3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_right_1.png")));

                dead1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_dead_1.png")));
                dead2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_dead_2.png")));
                dead3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_dead_3.png")));
                dead4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_dead_4.png")));
                dead5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_dead_5.png")));
            } else if (colorPlayer.equals("red")) {
                up1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_up_1.png")));
                up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_up_2.png")));
                up3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_up_1.png")));

                left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_left_1.png")));
                left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_left_2.png")));
                left3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_left_1.png")));

                down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_down_1.png")));
                down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_down_2.png")));
                down3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_down_1.png")));

                right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_right_1.png")));
                right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_right_2.png")));
                right3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_right_1.png")));

                dead1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_dead_1.png")));
                dead2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_dead_2.png")));
                dead3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_dead_3.png")));
                dead4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_dead_4.png")));
                dead5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_dead_5.png")));
            } else if (colorPlayer.equals("yellow")) {
                up1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_up_1.png")));
                up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_up_2.png")));
                up3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_up_1.png")));

                left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_left_1.png")));
                left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_left_2.png")));
                left3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_left_1.png")));

                down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_down_1.png")));
                down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_down_2.png")));
                down3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_down_1.png")));

                right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_right_1.png")));
                right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_right_2.png")));
                right3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_right_1.png")));

                dead1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_dead_1.png")));
                dead2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_dead_2.png")));
                dead3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_dead_3.png")));
                dead4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_dead_4.png")));
                dead5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_dead_5.png")));
            }
            dead6 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_dead_6.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        x = 36;
        y = 72;
        lives = 2;
        speed = 2;
        direction = "down";
        status = "live";
        spriteNum = 1;
        typeBomb = 4;
        check = true;
        bombs = new ArrayList<>();
        countBomb = 1;
    }

    public void setNextLevel() {
        x = 36;
        y = 72;
        speed = 2;
        direction = "down";
        status = "live";
        spriteNum = 1;
        typeBomb = 4;
        check = true;
        bombs = new ArrayList<>();
        countBomb = 1;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void check_crush_monster(Entity i) {
//        if (Math.abs(monster.x - solidArea.x) < PanelGame.tileSize && Math.abs(monster.y - solidArea.y) < PanelGame.tileSize) {
//            status = "dead";
//        }
        int entityLeftX = x + solidArea.x;
        int entityRightX = x + solidArea.x + solidArea.width;
        int entityTopY = y + solidArea.y;
        int entityBottomY = y + solidArea.y + solidArea.height;
        switch (direction) {
            case "up": {
                if (((entityLeftX - i.x <= PanelGame.tileSize)
                        && (i.x - entityLeftX <= solidArea.width))
                        && entityTopY - i.y <= PanelGame.tileSize
                        && entityTopY - i.y >= 0) {
                    status = "dead";
                    break;
                }
            }
            case "down": {
                if (((entityLeftX - i.x <= PanelGame.tileSize)
                        && (i.x - entityLeftX <= solidArea.width))
                        && i.y - entityBottomY <= 0
                        && i.y - entityBottomY >= -PanelGame.tileSize) {
                    status = "dead";
                    break;
                }
            }
            case "left": {
                if (((entityTopY - i.y <= PanelGame.tileSize)
                        && (i.y - entityTopY <= solidArea.height))
                        && entityLeftX - i.x <= PanelGame.tileSize
                        && entityLeftX - i.x >= 0) {
                    status = "dead";
                    break;
                }
            }
            case "right": {
                if (((entityTopY - i.y <= PanelGame.tileSize)
                        && (i.y - entityTopY <= solidArea.height))
                        && i.x - entityRightX <= 0
                        && i.x - entityRightX >= -PanelGame.tileSize) {
                    status = "dead";
                    break;
                }
            }
        }


        // FIX VA CHẠM CHỖ NÀY SAU.
    }

    public void player_Put_Bomb() {
        String message = update(bombs, gp.tle.getBricks());
        if (message.equals("Put a bomb!") && bombs.size() < countBomb && _timeBetweenPutBombs < 0) {
            Bomb bomb1 = new Bomb((x + PanelGame.tileSize / 2) / PanelGame.tileSize * PanelGame.tileSize, (y + PanelGame.tileSize / 2) / PanelGame.tileSize * PanelGame.tileSize, gp, typeBomb);
            bombs.add(bomb1);
            try {
                gp.sound.setbomb();
            } catch (
                    Exception e) {
                e.printStackTrace();
            }
            _timeBetweenPutBombs = 30;

        }

    }

    public boolean updateWin(Portal p) {
        return Math.abs(x - p.getX()) < 18 && Math.abs(y - p.getY()) < 18;
    }

    public void bomb_Dis_Brick() {
        Iterator<Bomb> bs = bombs.iterator();
        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            for (Brick brick : gp.tle.getBricks()) {
                brick.updateWaitBreaking(b);
            }
            b.update();
            if (b.getStatus().equals("exploded")) {
                bs.remove();
            }
        }

    }

    @Override
    public String update(List<Bomb> bombs, List<Brick> bricks) {
        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;
        if (!status.equals("dead")) {
            gp.checker.checkBomb(this, bombs);

            int[] keyinput = {0, 0, 0, 0};

            if (key.up || key.down || key.left || key.right || key.space) {
                if (key.up) {
                    direction = "up";
                    keyinput[0] = 1;
                }
                if (key.down) {
                    direction = "down";
                    keyinput[1] = 2;
                }
                if (key.left) {
                    direction = "left";
                    keyinput[2] = 3;
                }
                if (key.right) {
                    direction = "right";
                    keyinput[3] = 4;
                }
                if (key.space) {
                    return "Put a bomb!";
                }
                // CHECK TILE COLLISION
                collisionOn = false;
                gp.checker.checkTile(this);
                // IF COLLISION IS FALSE PLAY CAN MOVE
                // CHECK TILE COLLISION
                if (keyinput[0] == 1) {
                    collisionOn = false;
                    direction = "up";
                    gp.checker.checkTile(this);
                    if (!collisionOn) {
                        y -= speed;
                    }
                }
                if (keyinput[1] == 2) {
                    collisionOn = false;
                    direction = "down";
                    gp.checker.checkTile(this);
                    if (!collisionOn) {
                        y += speed;
                    }
                }
                if (keyinput[2] == 3) {
                    collisionOn = false;
                    direction = "left";
                    gp.checker.checkTile(this);
                    if (!collisionOn) {
                        x -= speed;
                    }
                }
                if (keyinput[3] == 4) {
                    collisionOn = false;
                    direction = "right";
                    gp.checker.checkTile(this);
                    if (!collisionOn) {
                        x += speed;
                    }
                }
                spriteCounter++;
                if (spriteCounter > 6) {
                    if (spriteNum < 3) {
                        spriteNum++;
                    } else if (spriteNum == 3) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }

        }
        if (status.equals("dead")) {
            if (spriteNum == 1 && check) {
                try {
                    gp.sound.die();
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
                check = false;
            }
            if (spriteCounter > 20 && spriteNum < 6) {
                spriteNum++;
                spriteCounter = 0;
            }
            if (spriteNum == 6 && lives == 1) {
                lives--;
                gp.gameState = gp.gameOverState;
                try {
                    gp.sound.endgame();
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }
            spriteCounter++;
            // có 2 mạng mỗi ván chơi
            if(spriteNum == 6 && lives > 1) {
                lives--;
                status = "live";
                spriteNum = 1;
                x = 36;
                y = 72;
            }
        }
        return "None";
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        if (!status.equals("dead")) {
            switch (direction) {
                case "up" -> {
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                    if (spriteNum == 3) image = up3;
                    break;
                }
                case "down" -> {
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                    if (spriteNum == 3) image = down3;
                    break;
                }
                case "left" -> {
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                    if (spriteNum == 3) image = left3;
                    break;
                }
                case "right" -> {
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                    if (spriteNum == 3) image = right3;
                    break;
                }
            }
        }
        if (status.equals("dead")) {
            if (spriteNum == 1) image = dead1;
            if (spriteNum == 2) image = dead2;
            if (spriteNum == 3) image = dead3;
            if (spriteNum == 4) image = dead4;
            if (spriteNum == 5) image = dead5;
            if (spriteNum == 6) image = dead6;
            if (spriteNum == 8) {
                status = "deaded";
            }
        }
        g2.drawImage(image, x, y, PanelGame.tileSize, PanelGame.tileSize, null);
    }
}