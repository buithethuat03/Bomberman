package entity;

import Maps.Brick;
import Maps.Grass;
import Maps.Wall;
import Panel.PanelGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Kondoria extends Entity {
    PanelGame gp;
    BufferedImage dead;

    Random rand = new Random();
    int[][] map = new int[13][31];

    public String status;
    public Kondoria(PanelGame gp) {
        this.gp = gp;
        setDefaultValues();
        getBalloomImage();
        loadmap();
    }

    public void setLocationAndDirection(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    private void loadmap() {
        try {
            File fileReader = new File("src/main/resources/data/map1.txt");
            Scanner scanner = new Scanner(fileReader);
            for (int i = 0;i < gp.maxScreenRow; i++) {
                for(int j = 0; j < gp.maxScreenCol; j++) {
                    map[i][j] = scanner.nextInt();
                }
            }
            scanner.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    void setDefaultValues() {
        x = 36;
        y = 36;
        speed = 1;
        direction = "right";
        status = "live";
    }

    void getBalloomImage() {
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
    public String update(List<Wall> walls, Bomb bomb, List<Brick> bricks) {
        //Toa do x, y trong ma tran
        int idy = x / gp.tileSize;
        int idx = y / gp.tileSize;

        if (bomb != null) {
            if (bomb.status.equals("exploding")) {
                if (Math.abs(bomb.x - x) < gp.tileSize && Math.abs(bomb.y - y) < 2 * gp.tileSize - 2 * speed
                        || Math.abs(bomb.y - y) < gp.tileSize && Math.abs(bomb.x - x) < 2 * gp.tileSize - 2 * speed) {
                    status = "dead";
                }
            }
        }
        switch (direction) {
            case "up" -> {
                spriteCounter++;
                boolean can_up = true;
                for (Wall wall : walls) {
                    if (wall.x - x <= gp.tileSize - speed && wall.x - x >= -gp.tileSize + 4 && y - wall.y < gp.tileSize + speed && y - wall.y >= 0) {
                        can_up = false;
                        break;
                    }
                }

                if (can_up && y >= speed && status.equals("live")) {
                    y -= speed;
                } else {
                    List<String> directionList = new ArrayList<>();
                    directionList.add("down");
                    if (map[idx][idy - 1] == 0) {
                        directionList.add("left");
                    }
                    if (map[idx][idy + 1] == 0) {
                        directionList.add("right");
                    }
                    direction = directionList.get(rand.nextInt(directionList.size()));
                }
            }
            case "down" -> {
                spriteCounter++;
                boolean can_down = true;
                for (Wall wall : walls) {
                    if (wall.x - x <= gp.tileSize - speed && wall.x - x >= -gp.tileSize + 4 && wall.y - y < gp.tileSize + speed && wall.y - y >= 0) {
                        can_down = false;
                        break;
                    }
                }

                if (can_down && y < (gp.tileSize * (gp.maxScreenRow - 1)) && status.equals("live")) {
                    y += speed;
                } else {
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
            }
            case "left" -> {
                spriteCounter++;
                boolean can_left = true;
                for (Wall wall : walls) {
                    if (wall.y - y <= gp.tileSize - speed && wall.y - y >= -gp.tileSize + speed && x - wall.x < gp.tileSize + speed && x - wall.x >= 0) {
                        can_left = false;
                        break;
                    }
                }

                if (can_left && x >= speed && status.equals("live")) {
                    x -= speed;
                } else {
                    List<String> directionList = new ArrayList<>();
                    directionList.add("right");
                    if (map[idx - 1][idy] == 0) {
                        directionList.add("up");
                    }
                    if (map[idx + 1][idy] == 0) {
                        directionList.add("down");
                    }
                    direction = directionList.get(rand.nextInt(directionList.size()));
                }
            }
            case "right" -> {
                spriteCounter++;
                boolean can_right = true;
                for (Wall wall : walls) {
                    if (wall.y - y <= gp.tileSize - speed && wall.y - y >= -gp.tileSize + speed && wall.x - x < gp.tileSize + speed && wall.x - x >= 0) {
                        can_right = false;
                        break;
                    }
                }

                if (x < (gp.tileSize * (gp.maxScreenCol - 1)) && can_right && status.equals("live")) {
                    x += speed;
                } else {
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
        if (spriteCounter > 6 && status.equals("live")) {
            if (spriteNum < 3) {
                spriteNum++;
            } else if (spriteNum == 3){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (status.equals("dead")) {
            spriteCounter++;
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
        }
        else {
            image = dead;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
