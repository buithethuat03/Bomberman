package item;

import Panel.PanelGame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public abstract class Item {

    protected PanelGame gp;
    protected int x;
    protected int y;

    protected BufferedImage normal;
    protected int[][] map;

    public String status;
    public abstract void updateItem();

    public abstract void getImage();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void setRandomLocation(String path) {
        List<Pointer> pointerList = new ArrayList<>();
        map = new int[PanelGame.maxScreenRow][PanelGame.maxScreenCol];
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
        for (int i = 0; i < PanelGame.maxScreenRow; i++) {
            for (int j = 0; j < PanelGame.maxScreenCol; j++) {
                if (map[i][j] == 2) {
                    Pointer temp = new Pointer(i, j);
                    pointerList.add(temp);
                    break;
                }
            }
        }
        Random rand = new Random();
        int n = pointerList.size();
        int k = rand.nextInt(n);
        setLocation((pointerList.get(k).y) * PanelGame.tileSize, (pointerList.get(k).x) * PanelGame.tileSize);
    }

    public abstract void setLocation(int x, int y);

    public abstract void drawItem(Graphics2D g2);
}
