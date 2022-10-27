package entity;

import Maps.Brick;
import Panel.PanelGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Kondoria extends Enemy {

    public Kondoria(PanelGame gp) {
        this.gp = gp;
        solidArea = new Rectangle();
        // hix_box //
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 35;
        solidArea.height = 35;
        // ======//
        setDefaultValues();
        getImage();
        loadmap(gp.pathMap[gp.getLevel()]);
    }

    public void setLocationAndDirection(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    private void loadmap(String path) {
        try {
            File fileReader = new File(path);
            Scanner scanner = new Scanner(fileReader);
            for (int i = 0; i < PanelGame.maxScreenRow; i++) {
                for (int j = 0; j < PanelGame.maxScreenCol; j++) {
                    map[i][j] = scanner.nextInt();
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setDefaultValues() {
        x = 36;
        y = 72;
        speed = 1;
        direction = "right";
        status = "live";
    }

    public void getImage() {
        try {
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("kondoria/kondoria_left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("kondoria/kondoria_left2.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("kondoria/kondoria_left3.png")));

            right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("kondoria/kondoria_right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("kondoria/kondoria_right2.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("kondoria/kondoria_right3.png")));

            dead = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("kondoria/kondoria_dead.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String update(List<Bomb> bombs, List<Brick> bricks) {
        //Toa do x, y trong ma tran
        int idy = x / PanelGame.tileSize;
        int idx = y / PanelGame.tileSize;

        if(status.equals("live")) {
            gp.checker.checkBomb(this, bombs);
            // CHECK TILE COLLISION
            collisionOn = false;
            gp.checker.checkTile(this);
            // IF COLLISION IS FALSE AND LIVE kondoria CAN MOVE
            if (!collisionOn && status.equals("live")) {
                switch (direction) {
                    case "up" -> {
                        spriteCounter++;
                        y -= speed;
                    }
                    case "down" -> {
                        spriteCounter++;
                        y += speed;
                    }
                    case "left" -> {
                        spriteCounter++;
                        x -= speed;
                    }
                    case "right" -> {
                        spriteCounter++;
                        x += speed;
                    }
                }
            } else {
                switch (direction) {
                    case "up" -> {
                        spriteCounter++;
                        y += solidArea.y + speed;
                        List<String> directionList = new ArrayList<>();
                        directionList.add("down");
                        if (map[y/PanelGame.tileSize][idy - 1] == 0) {
                            directionList.add("left");
                        }
                        if (map[y/PanelGame.tileSize][idy + 1] == 0) {
                            directionList.add("right");
                        }
                        direction = directionList.get(rand.nextInt(directionList.size()));
                    }
                    case "down" -> {
                        spriteCounter++;
                        List<String> directionList = new ArrayList<>();
                        directionList.add("up");
                        if (map[idx][idy - 1] == 0) {
                            directionList.add("left");
                        }
                        if (map[idx][idy + 1] == 0) {
                            directionList.add("right");
                        }
                        direction = directionList.get(rand.nextInt(directionList.size()));
                    }
                    case "left" -> {
                        spriteCounter++;
                        List<String> directionList = new ArrayList<>();
                        x += solidArea.x + speed;
                        directionList.add("right");
                        if (map[idx - 1][x/PanelGame.tileSize] == 0) {
                            directionList.add("up");
                        }
                        if (map[idx + 1][x/PanelGame.tileSize] == 0) {
                            directionList.add("down");
                        }
                        direction = directionList.get(rand.nextInt(directionList.size()));
                    }
                    case "right" -> {
                        spriteCounter++;
                        List<String> directionList = new ArrayList<>();
                        directionList.add("left");
                        if (map[idx - 1][idy] == 0) {
                            directionList.add("up");
                        }
                        if (map[idx + 1][idy] == 0) {
                            directionList.add("down");
                        }
                        direction = directionList.get(rand.nextInt(directionList.size()));
                    }
                }
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
        else if (status.equals("dead")) {
            spriteCounter++;
            if(spriteCounter == 45) {
                gp.point += 200;
            }
        }
        return "None";
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        if (status.equals("live")) {
            switch (direction) {
                case "left", "up" -> {
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                    if (spriteNum == 3) image = left3;
                }
                case "right", "down" -> {
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                    if (spriteNum == 3) image = right3;
                }
            }
        } else {
            image = dead;
        }
        g2.drawImage(image, x, y, PanelGame.tileSize, PanelGame.tileSize, null);
    }
}
