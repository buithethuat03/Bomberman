package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
import java.util.Scanner;

import Maps.Flame.*;
import Panel.PanelGame;

public class Bomb {
    private int x, y;
    PanelGame gp;
    private int spriteCounter = 0;
    public int spriteNum = 1;
    private BufferedImage bomb1, bomb2, bomb3, bomb_exploded1, bomb_exploded2, bomb_exploded3;


    private int[][] map;
    public List<Flame> flameList = new ArrayList<>();
    private long time_init, time_now;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bomb(int x, int y, PanelGame gp, int type) {
        this.x = x;
        this.y = y;
        this.gp = gp;
        getBoomImages();

        getFlame(type, gp.pathMap[gp.getLevel()]);
        time_init = System.nanoTime();
        status = "prepare_to_explode";
    }

    public int getMatrix_X() {
        return (x / PanelGame.tileSize) * PanelGame.tileSize;
    }

    public int getMatrix_Y() {
        return (y / PanelGame.tileSize) * PanelGame.tileSize;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void getFlame(int type, String path) {
        map = new int[14][31];
        try {
            File fileReader = new File("src/main/resources/data/map1.txt");
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
        if (gp.player.typeBomb == 4) {
            // left

            FlameF flameF = new FlameF(gp, "normal");
            flameF.setLocation(getMatrix_X() - 36, getMatrix_Y());
            if (map[flameF.getY() / PanelGame.tileSize][flameF.getX() / PanelGame.tileSize] != 1) {
                flameList.add(flameF);
            }
            // right

            FlameR flameR = new FlameR(gp, "normal");
            flameR.setLocation(getMatrix_X() + 36, getMatrix_Y());
            if (map[flameR.getY() / PanelGame.tileSize][flameR.getX() / PanelGame.tileSize] != 1) {
                flameList.add(flameR);
            }
            // top

            FlameT flameT = new FlameT(gp, "normal");
            flameT.setLocation(getMatrix_X(), getMatrix_Y() - 36);
            if (map[flameT.getY() / PanelGame.tileSize][flameT.getX() / PanelGame.tileSize] != 1) {
                flameList.add(flameT);
            }
            // bottom

            FlameB flameB = new FlameB(gp, "normal");
            flameB.setLocation(getMatrix_X(), getMatrix_Y() + 36);
            if (map[flameB.getY() / PanelGame.tileSize][flameB.getX() / PanelGame.tileSize] != 1) {
                flameList.add(flameB);
            }

        } else if (gp.player.typeBomb == 8) {
            // left
            FlameF flameF1 = new FlameF(gp, "advanced_1");
            flameF1.setLocation(getMatrix_X() - 36, getMatrix_Y());

            if (map[flameF1.getY() / PanelGame.tileSize][flameF1.getX() / PanelGame.tileSize] != 1) {
                flameList.add(flameF1);
                FlameF flameF2 = new FlameF(gp, "advanced_2");
                flameF2.setLocation(getMatrix_X() - 72, getMatrix_Y());
                if (map[flameF2.getY() / PanelGame.tileSize][flameF2.getX() / PanelGame.tileSize] != 1) {
                    flameList.add(flameF2);
                }
            }

            // right

            FlameR flameR1 = new FlameR(gp, "advanced_1");
            flameR1.setLocation(getMatrix_X() + 36, getMatrix_Y());

            if (map[flameR1.getY() / PanelGame.tileSize][flameR1.getX() / PanelGame.tileSize] != 1) {
                flameList.add(flameR1);
                FlameR flameR2 = new FlameR(gp, "advanced_2");
                flameR2.setLocation(getMatrix_X() + 72, getMatrix_Y());
                if (map[flameR2.getY() / PanelGame.tileSize][flameR2.getX() / PanelGame.tileSize] != 1) {
                    flameList.add(flameR2);
                }
            }
            // top

            FlameT flameT1 = new FlameT(gp, "advanced_1");
            flameT1.setLocation(getMatrix_X(), getMatrix_Y() - 36);
            if (map[flameT1.getY() / PanelGame.tileSize][flameT1.getX() / PanelGame.tileSize] != 1) {
                flameList.add(flameT1);
                FlameT flameT2 = new FlameT(gp, "advanced_2");
                flameT2.setLocation(getMatrix_X(), getMatrix_Y() - 72);
                if (map[flameT2.getY() / PanelGame.tileSize][flameT2.getX() / PanelGame.tileSize] != 1) {
                    flameList.add(flameT2);
                }
            }

            // bottom

            FlameB flameB1 = new FlameB(gp, "advanced_1");
            flameB1.setLocation(getMatrix_X(), getMatrix_Y() + 36);
            if (map[flameB1.getY() / PanelGame.tileSize][flameB1.getX() / PanelGame.tileSize] != 1) {
                flameList.add(flameB1);
                FlameB flameB2 = new FlameB(gp, "advanced_2");
                flameB2.setLocation(getMatrix_X(), getMatrix_Y() + 72);
                if (map[flameB2.getY() / PanelGame.tileSize][flameB2.getX() / PanelGame.tileSize] != 1) {
                    flameList.add(flameB2);
                }
            }
        }

    }

    void getBoomImages() {
        try {
            bomb1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/bomb.png")));
            bomb2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/bomb_1.png")));
            bomb3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/bomb_2.png")));

            bomb_exploded1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/bomb_exploded2.png")));
            bomb_exploded2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/bomb_exploded1.png")));
            bomb_exploded3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/bomb_exploded.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        time_now = System.nanoTime();
        spriteCounter++;
        if ((time_now - time_init) / 1000000000 >= 2) {
            if (status.equals("prepare_to_explode")) {
                status = "exploding";
                try {
                    gp.sound.bomb();
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if ((time_now - time_init) / 1000000000.0 >= 2.5) {
            if (status.equals("exploding")) {
                status = "exploded";
            }
        }
        // có thừa chỗ này không :?
        if (status.equals("prepare_to_explode")) {
            if (spriteCounter > 20) {
                if (spriteNum < 3) {
                    spriteNum++;
                } else if (spriteNum == 3) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        if (status.equals("exploding")) {
            if (spriteCounter > 12) {
                if (spriteNum < 3) {
                    spriteNum++;
                } else if (spriteNum == 3) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (status) {
            case "prepare_to_explode" -> {
                if (spriteNum == 1) image = bomb1;
                if (spriteNum == 2) image = bomb2;
                if (spriteNum == 3) image = bomb3;
            }
            case "exploding" -> {
                for (Flame flame : flameList) {
                    flame.drawFlame(g2, this);
                }
                if (spriteNum == 1) {
                    image = bomb_exploded1;
                }
                if (spriteNum == 2) {
                    image = bomb_exploded2;
                }
                if (spriteNum == 3) {
                    image = bomb_exploded3;
                }
            }
        }
        g2.drawImage(image, x, y, PanelGame.tileSize, PanelGame.tileSize, null);
    }
}
