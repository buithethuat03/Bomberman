package entity;


import Maps.Brick;
import Maps.Portal;
import Maps.Wall;
import Panel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Player extends Entity {
    PanelGame gp;
    Key key;

    public Player(PanelGame gp, Key key) {
        this.gp = gp;
        this.key = key;
        setDefaultValues();
        getPlayerImage();
        direction = "down";
        status = "live";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_up_2.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_up_1.png")));

            left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_left_2.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_left_1.png")));

            down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_down_2.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_down_1.png")));

            right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_right_2.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_right_1.png")));

            dead1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_dead_1.png")));
            dead2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_dead_2.png")));
            dead3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_dead_3.png")));
            dead4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_dead_4.png")));
            dead5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_dead_5.png")));
            dead6 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_dead_6.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        x = 36;
        y = 36;
        speed = 2;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void check_crush_monster(Entity monster) {
//        if (monster instanceof Balloom) {
//            monster = (Balloom) monster;
//        }
//        if (monster instanceof Kondoria) {
//            monster = (Kondoria) monster;
//        }
        if (Math.abs(monster.x - x) < gp.tileSize - 2 * speed && Math.abs(monster.y - y) < gp.tileSize - 2 * speed) {
            status = "dead";
        }
    }
    public boolean updateWin(Portal p) {
        return Math.abs(x - p.x) < 36 && Math.abs(y - p.y) < 36;
    }
    @Override
    public String update(List<Wall> walls, Bomb bomb, List<Brick> bricks) {
        if (!status.equals("dead")) {
            if (bomb != null) {
                if (bomb.status.equals("exploding")) {
                    //TODO: Can phai sua lai va cham voi bomb
                    if (Math.abs(bomb.x - x) < gp.tileSize - 3 * speed && Math.abs(bomb.y - y) < 2 * gp.tileSize -  4 * speed
                            || Math.abs(bomb.y - y) < gp.tileSize - 3 * speed && Math.abs(bomb.x - x) < 2 * gp.tileSize - 3 * speed) {
                        status = "dead";
                        spriteNum = 1;
                        spriteCounter = 0;
                    }
                }
            }


            if (key.up) {
                direction = "up";
                spriteCounter++;
                boolean can_up = true;
                for (Wall wall : walls) {
                    if (wall.x - x <= gp.tileSize - 4 * speed && wall.x - x >= -gp.tileSize + 4 * speed
                            && y - wall.y < gp.tileSize - 3 * speed && y - wall.y >= 0) {
                        can_up = false;
                        break;
                    }
                }

                for (Brick brick : bricks) {
                    if (brick.x - x <= gp.tileSize - 4 * speed && brick.x - x >= -gp.tileSize + 4 * speed
                            && y - brick.y < gp.tileSize - 3 * speed && y - brick.y >= 0) {
                        can_up = false;
                        break;
                    }
                }

                if (can_up && y >= speed) {
                    y -= speed;
                }
            }
            if (key.down) {
                direction = "down";
                spriteCounter++;
                boolean can_down = true;
                for (Wall wall : walls) {
                    if (wall.x - x <= gp.tileSize - 4 * speed && wall.x - x >= -gp.tileSize + 4 * speed
                            && wall.y - y < gp.tileSize && wall.y - y >= 0) {
                        can_down = false;
                        break;
                    }
                }

                for (Brick brick : bricks) {
                    if (brick.x - x <= gp.tileSize - 4 * speed && brick.x - x >= -gp.tileSize + 4 * speed
                            && brick.y - y < gp.tileSize && brick.y - y >= 0) {
                        can_down = false;
                        break;
                    }
                }

                if (can_down && y < (gp.tileSize * (gp.maxScreenRow) - 1)) {
                    y += speed;
                }
            }
            if (key.left) {
                direction = "left";
                spriteCounter++;
                boolean can_left = true;
                for (Wall wall : walls) {
                    if (wall.y - y <= gp.tileSize - 2 * speed && wall.y - y >= -gp.tileSize + 5 * speed && x - wall.x < gp.tileSize - 2 * speed && x - wall.x >= 0) {
                        can_left = false;
                        break;
                    }
                }

                for (Brick brick : bricks) {
                    if (brick.y - y <= gp.tileSize - 2 * speed && brick.y - y >= -gp.tileSize + 5 * speed && x - brick.x < gp.tileSize - 2 * speed && x - brick.x >= 0) {
                        can_left = false;
                        break;
                    }
                }
                if (can_left && x >= speed) {
                    x -= speed;
                }
            }
            if (key.right) {
                direction = "right";
                spriteCounter++;
                boolean can_right = true;
                for (Wall wall : walls) {
                    if (wall.y - y <= gp.tileSize - 2 * speed && wall.y - y >= -gp.tileSize + 5 * speed && wall.x - x < gp.tileSize - 2 * speed && wall.x - x >= 0) {
                        can_right = false;
                        break;
                    }
                }

                for (Brick brick : bricks) {
                    if (brick.y - y <= gp.tileSize - 2 * speed && brick.y - y >= -gp.tileSize + 5 * speed && brick.x - x < gp.tileSize - 2 * speed && brick.x - x >= 0) {
                        can_right = false;
                        break;
                    }
                }

                if (x < gp.tileSize * (gp.maxScreenCol) - 1 && can_right) {
                    x += speed;
                }
            }
            if (key.space) {
                return "Put a bomb!";
            }

            if (spriteCounter > 6) {
                if (spriteNum < 3) {
                    spriteNum++;
                } else if (spriteNum == 3) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        if (status.equals("dead")) {
            if (spriteCounter > 24) {
                spriteNum++;
                spriteCounter = 0;
            }
            spriteCounter++;
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
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}