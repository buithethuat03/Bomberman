package Panel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Maps.*;
import entity.*;

public class PanelGame extends JPanel implements Runnable {
    public final int originalTileSize = 12;
    public final int scale = 3;

    public final int fps = 60;
    public final long draw_time = 1000000000 / fps;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 31;
    public final int maxScreenRow = 13;
    private final int screen_width = maxScreenCol * tileSize;
    private final int screen_height = maxScreenRow * tileSize;

    Key key = new Key();
    Thread game_thread;
    Player player;

    Portal portal;
    List<Balloom> ballooms = new ArrayList<>();
    List<Kondoria> kondorias = new ArrayList<>();
    Bomb bomb;

    TileManager tle = new TileManager(this);

    void initEntityAndObject() {
        player = new Player(this, key);
        String[] dir = {"up", "down", "left", "right"};
        Random rand = new Random();
        int[][] mapTileNum = new int [maxScreenRow][maxScreenCol];
        try {
            File fileReader = new File("src/main/resources/data/map1.txt");
            Scanner scanner = new Scanner(fileReader);
            for (int i = 0;i < maxScreenRow; i++) {
                for(int j = 0; j < maxScreenCol; j++) {
                    mapTileNum[i][j] = scanner.nextInt();
                    if (mapTileNum[i][j] == 3) {
                        Balloom balloom = new Balloom(this);
                        balloom.setLocationAndDirection(j * tileSize, i * tileSize, dir[rand.nextInt(dir.length)]);
                        ballooms.add(balloom);
                    }

                    if (mapTileNum[i][j] == 4) {
                        // TODO: load new enemy can run over wall (kondoria)
                        Kondoria kondoria = new Kondoria(this);
                        kondoria.setLocationAndDirection(j * tileSize, i * tileSize, dir[rand.nextInt(dir.length)]);
                        kondorias.add(kondoria);
                    }
                }
            }
            scanner.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public PanelGame() {
        this.setPreferredSize(new Dimension(screen_width, screen_height));
        this.setBackground(Color.DARK_GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.setFocusable(true);
    }

    public void startGameThread() {
        game_thread = new Thread(this);
        game_thread.start();
    }

    @Override
    public void run() {
        initEntityAndObject();
        while (game_thread != null) {
            long start = System.nanoTime();

            //Update object information
            update();

            //Render
            repaint();

            try {
                long used_time = System.nanoTime() - start;
                if (used_time < draw_time) {
                    Thread.sleep((draw_time - used_time) / 1000000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {

        //Create a portal if enemies = 0
        if (portal == null && ballooms.size() == 0 && kondorias.size() == 0) {
            portal = new Portal(this);
        }

        //Player put a bomb!
        String message = player.update(tle.walls, bomb, tle.bricks);
        if (message.equals("Put a bomb!") && bomb == null) {
            bomb = new Bomb((player.x + tileSize / 2) / tileSize * tileSize, (player.y + tileSize / 2) / tileSize * tileSize, this);
        }

        if (bomb != null) {
            for(Brick brick : tle.bricks) {
                brick.updateWaitBreaking(bomb);
            }
            bomb.update();
            if (bomb.status.equals("exploded")) {
                bomb = null;
            }
        }

        for(Brick brick : tle.bricks) {
            brick.updateBreaking();
        }

        tle.updateBrick();

        //If player dead, exit the game
        if (player.status.equals("deaded")) {
            //game_thread = null;
            System.exit(0);
        }

        //After player kill all enemies, portal appear, if player interact portal, exit the game
        if (portal != null) {
            if (player.updateWin(portal)) {
                System.exit(0);
            }
        }

        //Check entity with bomb exploding
        int idx = 0;
        while (idx < ballooms.size()) {
            if (ballooms.get(idx) != null) {
                ballooms.get(idx).update(tle.walls, bomb, tle.bricks);
                player.check_crush_monster(ballooms.get(idx));
                if (ballooms.get(idx).spriteCounter > 100) {
                    ballooms.remove(idx);
                } else {
                    idx++;
                }
            }
        }

        int idy = 0;
        while (idy < kondorias.size()) {
            if (kondorias.get(idy) != null) {
                kondorias.get(idy).update(tle.walls, bomb, tle.bricks);
                player.check_crush_monster(kondorias.get(idy));
                if (kondorias.get(idy).spriteCounter > 100) {
                    kondorias.remove(idy);
                } else {
                    idy++;
                }
            }
        }

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        tle.drawGrass(g2);

        for (Balloom balloom : ballooms) {
            if (balloom != null) {
                balloom.draw(g2);
            }
        }


        if (portal != null) {
            portal.draw(g2);
            player.updateWin(portal);
        }
        if (bomb != null) {
            bomb.draw(g2);
        }

        tle.drawWall(g2);
        tle.drawBrick(g2);

        for (Kondoria kondoria : kondorias) {
            if (kondoria != null) {
                kondoria.draw(g2);
            }
        }

        if (player != null) {
            player.draw(g2);
        }


        g2.dispose();
    }
}