package Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Maps.*;
import MenuGame.MenuControl;
import entity.*;

public class PanelGame extends JPanel implements Runnable {
    public static final int originalTileSize = 12;
    public static final int scale = 3;
    public static final int fps = 60;
    public static final long draw_time = 1000000000 / fps;
    public static final int tileSize = originalTileSize * scale;
    public static final int maxScreenCol = 31;
    public static final int maxScreenRow = 14;
    public final int screen_width = maxScreenCol * tileSize;
    public final int screen_height = maxScreenRow * tileSize;

    //public boolean activeBomb;
    //public String message;
    private int level = 0;
    public String[] pathMap = {"src/main/resources/data/map1.txt"
            , "src/main/resources/data/map2.txt"};
    // menuGame
    public int commandNum = -1;
    public int gameState;
    public final int titleState = 0;
    public final int playState = 2;
    public final int gameOverState = 3;
    public final int pauseGameState = 4;
    public final int winGame = 5;
    public boolean guideState = false;
    public boolean optionState = false;
    //
    public MenuControl menuControl = new MenuControl(this);
    private Key key = new Key(this);
    private MouseHandler mouseHandler = new MouseHandler(this);
    public String mode = "day";
    public int point = 0;
    private int time = 150;
    private int counter = 0;

    public Sound sound = new Sound();

    private Thread game_thread;
    public Player player;
    private Portal portal;
    private List<Enemy> enemies = new ArrayList<>();


    public TileManager tle = new TileManager(this);
    public CollisionChecker checker = new CollisionChecker(this);

    public int getLevel() {
        return level;
    }

    public int getTime() {
        return time;
    }

    public PanelGame() {
        this.setPreferredSize(new Dimension(screen_width, screen_height));
        this.setBackground(Color.DARK_GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.addMouseListener(mouseHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        game_thread = new Thread(this);
        game_thread.start();
    }

    private void initEntityAndObject() {
        player = new Player(this, key);
        String[] dir = {"up", "down", "left", "right"};
        Random rand = new Random();
        int[][] mapTileNum = new int[maxScreenRow][maxScreenCol];
        try {
            File fileReader = new File("src/main/resources/data/map1.txt");
            Scanner scanner = new Scanner(fileReader);
            for (int i = 0; i < maxScreenRow; i++) {
                for (int j = 0; j < maxScreenCol; j++) {
                    mapTileNum[i][j] = scanner.nextInt();
                    if (mapTileNum[i][j] == 3) {
                        Balloom balloom = new Balloom(this);
                        balloom.setLocationAndDirection(j * tileSize, i * tileSize, dir[rand.nextInt(dir.length)]);
                        enemies.add(balloom);
                    }

                    if (mapTileNum[i][j] == 4) {
                        // TODO: load new enemy can run over wall (kondoria)
                        Kondoria kondoria = new Kondoria(this);
                        kondoria.setLocationAndDirection(j * tileSize, i * tileSize, dir[rand.nextInt(dir.length)]);
                        enemies.add(kondoria);
                    }
                    if (mapTileNum[i][j] == 5) {
                        Oneal oneal = new Oneal(this);
                        oneal.setLocationAndDirection(j * tileSize, i * tileSize, dir[rand.nextInt(dir.length)]);
                        enemies.add(oneal);
                    }
                    if (mapTileNum[i][j] == 6) {
                        Ovape ovape = new Ovape(this);
                        ovape.setLocationAndDirection(j * tileSize, i * tileSize, dir[rand.nextInt(dir.length)]);
                        enemies.add(ovape);
                    }
                    // TODO: sửa 1
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restartGame(String path) {
        // clean old game
        enemies.clear();
        portal = null;
        // make new game
        player.setDefaultValues();
        tle.restartAndSetValue(path);
        level = 0;
        point = 0;
        time = 150;
        String[] dir = {"up", "down", "left", "right"};
        Random rand = new Random();
        int[][] mapTileNum = new int[maxScreenRow][maxScreenCol];
        try {
            File fileReader = new File(path);
            Scanner scanner = new Scanner(fileReader);
            for (int i = 0; i < maxScreenRow; i++) {
                for (int j = 0; j < maxScreenCol; j++) {
                    mapTileNum[i][j] = scanner.nextInt();
                    if (mapTileNum[i][j] == 3) {
                        Balloom balloom = new Balloom(this);
                        balloom.setLocationAndDirection(j * tileSize, i * tileSize, dir[rand.nextInt(dir.length)]);
                        enemies.add(balloom);
                    }

                    if (mapTileNum[i][j] == 4) {
                        // TODO: load new enemy can run over wall (kondoria)
                        Kondoria kondoria = new Kondoria(this);
                        kondoria.setLocationAndDirection(j * tileSize, i * tileSize, dir[rand.nextInt(dir.length)]);
                        enemies.add(kondoria);
                    }
                    if (mapTileNum[i][j] == 5) {
                        Oneal oneal = new Oneal(this);
                        oneal.setLocationAndDirection(j * tileSize, i * tileSize, dir[rand.nextInt(dir.length)]);
                        enemies.add(oneal);
                    }
                    if (mapTileNum[i][j] == 6) {
                        Ovape ovape = new Ovape(this);
                        ovape.setLocationAndDirection(j * tileSize, i * tileSize, dir[rand.nextInt(dir.length)]);
                        enemies.add(ovape);
                    }
                    // TODO: sửa 1
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nextLevel(String path) {
        // clean old game
        enemies.clear();
        portal = null;
        // make new game
        player.setNextLevel();
        tle.restartAndSetValue(path);
        time = 150;
        String[] dir = {"up", "down", "left", "right"};
        Random rand = new Random();
        int[][] mapTileNum = new int[maxScreenRow][maxScreenCol];
        try {
            File fileReader = new File(path);
            Scanner scanner = new Scanner(fileReader);
            for (int i = 0; i < maxScreenRow; i++) {
                for (int j = 0; j < maxScreenCol; j++) {
                    mapTileNum[i][j] = scanner.nextInt();
                    if (mapTileNum[i][j] == 3) {
                        Balloom balloom = new Balloom(this);
                        balloom.setLocationAndDirection(j * tileSize, i * tileSize, dir[rand.nextInt(dir.length)]);
                        enemies.add(balloom);
                    }

                    if (mapTileNum[i][j] == 4) {
                        // TODO: load new enemy can run over wall (kondoria)
                        Kondoria kondoria = new Kondoria(this);
                        kondoria.setLocationAndDirection(j * tileSize, i * tileSize, dir[rand.nextInt(dir.length)]);
                        enemies.add(kondoria);
                    }
                    if (mapTileNum[i][j] == 5) {
                        Oneal oneal = new Oneal(this);
                        oneal.setLocationAndDirection(j * tileSize, i * tileSize, dir[rand.nextInt(dir.length)]);
                        enemies.add(oneal);
                    }
                    if (mapTileNum[i][j] == 6) {
                        Ovape ovape = new Ovape(this);
                        ovape.setLocationAndDirection(j * tileSize, i * tileSize, dir[rand.nextInt(dir.length)]);
                        enemies.add(ovape);
                    }
                    // TODO: sửa 1
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        initEntityAndObject();
        try {
            sound.music();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (gameState == playState) {
            //Create a portal if enemies = 0
            if (portal == null && enemies.size() == 0) {
                portal = new Portal(this);
            }

            //Player put a bomb!
            player.player_Put_Bomb();

            // update bomb destroyed brick
            player.bomb_Dis_Brick();

            // update brick breaking
            List<Brick> brickList = tle.getBricks();
            for (Brick brick : brickList) {
                brick.updateBreaking();
            }

            // update brick
            tle.update();

            //After player kill all enemies, portal appear, if player interact portal, exit the game
            if (portal != null) {
                if (player.updateWin(portal) && level < 2) {
                    level++;
                    if (level == 2) {
                        gameState = winGame;
                        level = 0;
                    } else {
                        nextLevel(pathMap[level]);
                    }
                }
            }

            //Check entity with bomb exploding
            int idx = 0;
            while (idx < enemies.size()) {
                if (enemies.get(idx) != null) {
                    enemies.get(idx).update(player.getBombs(), brickList);
                    player.check_crush_monster(enemies.get(idx));
                    if (enemies.get(idx).spriteCounter > 45) {
                        enemies.remove(idx);
                    } else {
                        idx++;
                    }
                }
            }
            // time
            counter++;
            if (counter >= 60) {
                time--;
                counter = 0;
            }
            if (time <= 0) {
                player.setStatus("dead");
                time = 0;
            }
        }
        mouseHandler.mouseLocation();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //Tile State
        if (gameState == titleState) {
            menuControl.draw(g2);

        } else {
            // vẽ nền
            tle.drawGrass(g2);
            // vẽ portal
            if (portal != null) {
                portal.draw(g2);
                player.updateWin(portal);
            }

            // vẽ bom
            if (player.getBombs() != null) {
                for (Bomb bomb : player.getBombs()) {
                    if (bomb != null) {
                        bomb.draw(g2);
                    }
                }
            }
            // vẽ tileManager( điều phối các hình tĩnh có trong game)

            tle.draw(g2);

            // vẽ enemy
            for (Enemy enemy : enemies) {
                if (enemy != null) {
                    enemy.draw(g2);
                }
            }

            // vẽ player

            if (player != null) {
                player.draw(g2);
            }

            menuControl.drawInfoGame(g2);

            // game over pause winGame
            if (gameState == gameOverState || gameState == pauseGameState || gameState == winGame) {
                menuControl.draw(g2);
            }
        }
        g2.dispose();
    }
}
