package Maps;

import Panel.PanelGame;
import item.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

// class này bao gồm các tile: grass tile: wall, brick: listbrick, item
public class TileManager {
    private PanelGame gp;
    private Tile[] tiles;
    private List<Brick> bricks = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    public int[][] mapTileNum;
    public List<Brick> getBricks() {
        return bricks;
    }
    public Tile[] getTiles() {
        return tiles;
    }
    public TileManager(PanelGame gp) {
        this.gp = gp;
        tiles = new Tile[10];
        mapTileNum = new int[PanelGame.maxScreenRow][PanelGame.maxScreenCol];
        getTileImage();
        loadMap(gp.pathMap[gp.getLevel()]);
    }

    public void getTileImage() {
        try {
            if (gp.mode.equals("day")) {
                tiles[0] = new Tile();
                tiles[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/grass.png")));

                tiles[1] = new Tile();
                tiles[1].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("wall/wall.png")));
                tiles[1].collision = true;
            } else if (gp.mode.equals("night")) {
                tiles[0] = new Tile();
                tiles[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/grass_n.png")));

                tiles[1] = new Tile();
                tiles[1].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("wall/wall_night.png")));
                tiles[1].collision = true;
            }
            tiles[3] = new Tile();
            tiles[4] = new Tile();
            tiles[5] = new Tile();
            tiles[6] = new Tile();
            for(int i = 3; i <= 6; i++) {
                tiles[i].collision = false;
            }
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void loadMap(String path) {
        // thêm cái item
        Item temp = new SpeedItem(gp);
        temp.setRandomLocation(path);
        Item temp1 = new FlameItem(gp);
        temp1.setRandomLocation(path);
//        Item temp2 = new BombItem(gp);
//        temp2.setRandomLocation();
        Item temp2 = new BombItem(gp);
        temp2.setRandomLocation(path);
        items.add(temp);
        items.add(temp1);
        items.add(temp2);

        // thêm các brick
        try {
            File fileReader = new File(path);
            Scanner scanner = new Scanner(fileReader);
            for (int i = 0; i < PanelGame.maxScreenRow; i++) {
                for (int j = 0; j < PanelGame.maxScreenCol; j++) {
                    mapTileNum[i][j] = scanner.nextInt();
                    if (mapTileNum[i][j] == 2) {
                        Brick brick = new Brick(gp);
                        brick.setLocation(j * PanelGame.tileSize, i * PanelGame.tileSize);
                        bricks.add(brick);
                    }
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restartAndSetValue(String path) {
        // clean
        bricks.clear();
        items.clear();
        // make new
        loadMap(path);
    }

    public void loadMode() {
        bricks.clear();
        for (int i = 0; i < PanelGame.maxScreenRow; i++) {
            for (int j = 0; j < PanelGame.maxScreenCol; j++) {
                if (mapTileNum[i][j] == 2) {
                    Brick brick = new Brick(gp);
                    brick.setLocation(j * PanelGame.tileSize, i * PanelGame.tileSize);
                    bricks.add(brick);
                }
            }
        }
    }

    // vẽ brick
    public void drawBrick(Graphics2D g2) {
        for (Brick obj : bricks) {
            obj.draw(g2);
        }
    }

    public void updateBrick() {
        int idx = 0;
        while (idx < bricks.size()) {
            if (bricks.get(idx).getStatus().equals("broken")) {
                mapTileNum[bricks.get(idx).y / PanelGame.tileSize][bricks.get(idx).x / PanelGame.tileSize] = 0;
                bricks.remove(idx);
            } else {
                idx++;
            }
        }
    }

    public void updateItem() {
        int x = 0;
        while (x < items.size()) {
            items.get(x).updateItem();
            if(items.get(x).status.equals("remove")) {
                items.remove(x);
            } else {
                x++;
            }
        }
    }

    public void update() {
        updateBrick();
        updateItem();
    }
    // vẽ grass
    public void drawGrass(Graphics2D g2) {
        // vẽ grass
        for (int i = 0; i < PanelGame.maxScreenRow; i++) {
            for (int j = 0; j < PanelGame.maxScreenCol; j++) {
                g2.drawImage(tiles[0].image, j * PanelGame.tileSize, i * PanelGame.tileSize, PanelGame.tileSize, PanelGame.tileSize, null);
            }
        }
    }

    public void draw(Graphics2D g2) {
        // vẽ wall
        int x = 0;
        int y = 0;
        while (x < PanelGame.maxScreenRow && y < PanelGame.maxScreenCol) {
            int tileNum = mapTileNum[x][y];
            if (tileNum == 1) {
                g2.drawImage(tiles[tileNum].image, y * PanelGame.tileSize, x * PanelGame.tileSize, PanelGame.tileSize, PanelGame.tileSize, null);
            }
            y++;
            if (y == PanelGame.maxScreenCol) {
                y = 0;
                x++;
            }
        }
        // vẽ item
        for (Item i : items) {
            i.drawItem(g2);
        }
        // vẽ brick
        drawBrick(g2);

    }
}
