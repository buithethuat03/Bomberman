package entity;

import Maps.Brick;
import Panel.PanelGame;
import entity.AI.CalculateAPathfinding;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Ovape extends Enemy {
    String canChase, prevDirection;
    boolean triggered = false;
    public String status;

    public Ovape(PanelGame gp) {
        this.gp = gp;
        solidArea = new Rectangle();
        // hixbox //
        solidArea.x = 1;
        solidArea.y = 1;
        solidArea.width = 34;
        solidArea.height = 34;
        //========//
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
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("ovape/ovape_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("ovape/ovape_left_2.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("ovape/ovape_triggered_left.png")));

            right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("ovape/ovape_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("ovape/ovape_right_2.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("ovape/ovape_triggered_right.png")));

            dead = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("ovape/ovape_dead.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: Lam mot phuong thuc phu hop cho oneal
    @Override
    public String update(List<Bomb> bombs, List<Brick> bricks) {
        // TODO: sửa 3

        if (status.equals("live")) {
            status = gp.checker.checkBomb(this, bombs);
            final int safeArea = 6;
            if (Math.sqrt(Math.abs(x - gp.player.x) * Math.abs(x - gp.player.x) + Math.abs(y - gp.player.y) * Math.abs(y - gp.player.y)) > safeArea * PanelGame.tileSize) {
                updateOvape(bombs, bricks);
                triggered = false;
            } else {
                //TODO: Thuat toan tim duong di ngan nhat trong ma tran
                if (gp.player.status.equals("live")) {
                    update_Actv(gp.tle.mapTileNum, gp.player.x, gp.player.y);
                    triggered = true;
                    updateOvape(bombs, bricks);
                }
            }
            if (spriteCounter > 4) {
                if (spriteNum < 2) {
                    spriteNum++;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        if (status.equals("dead")) {
            spriteCounter++;
        }
        return "none";
    }

    private void update_Actv(int[][] matrix, int playerX, int playerY) {
        CalculateAPathfinding aPathfinding = new CalculateAPathfinding(matrix, playerX, playerY, x, y, "ovape");
        canChase = aPathfinding.getDirection();
        if (!canChase.equals("false")) {
            prevDirection = direction;
            direction = canChase;
            if (direction.equals("up") || direction.equals("down")) {
                if (x % PanelGame.tileSize != 0) {
                    if (prevDirection.equals("left")) {
                        direction = "left";
                    } else {
                        direction = "right";
                    }
                }
            } else if (direction.equals("left") || direction.equals("right")) {
                if (y % PanelGame.tileSize != 0) {
                    if (prevDirection.equals("up")) {
                        direction = "up";
                    } else {
                        direction = "down";
                    }
                }
            }
        }
    }

    public void updateOvape(List<Bomb> bombs, List<Brick> bricks) {
        //Toa do x, y trong ma tran
        int idy = x / PanelGame.tileSize;
        int idx = y / PanelGame.tileSize;

        if (status.equals("live")) {
            gp.checker.checkBomb(this, bombs);

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.checker.checkTile(this);
            // IF COLLISION IS FALSE AND LIVE BALLOOM CAN MOVE

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
                        y+= solidArea.y + speed;
                        List<String> directionList = new ArrayList<>();
                        directionList.add("down");
                        if (map[y / PanelGame.tileSize][idy - 1] == 0) {
                            directionList.add("left");
                        }
                        if (map[y / PanelGame.tileSize][idy + 1] == 0) {
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
                        x += solidArea.x + speed;
                        List<String> directionList = new ArrayList<>();
                        directionList.add("right");
                        if (map[idx - 1][x / PanelGame.tileSize] == 0) {
                            directionList.add("up");
                        }
                        if (map[idx + 1][x / PanelGame.tileSize] == 0) {
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

        }
        if (spriteCounter > 20 && status.equals("live")) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (status.equals("dead")) {
            spriteCounter++;
            /*if(spriteCounter == 45) {
                gp.point += 400;
            }*/
            gp.point += 400;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        if (status.equals("live")) {
            switch (direction) {
                case "left", "up" -> {
                    if (spriteNum == 1) {
                        if (triggered) image = left3;
                        else image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                case "right", "down" -> {
                    if (spriteNum == 1) {
                        if (triggered) image = right3;
                        else image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
            }
        } else {
            image = dead;
        }
        g2.drawImage(image, x, y, PanelGame.tileSize, PanelGame.tileSize, null);
    }
}
