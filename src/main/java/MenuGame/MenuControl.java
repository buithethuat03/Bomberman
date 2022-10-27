package MenuGame;

import Panel.PanelGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class MenuControl {
    private PanelGame gp;
    private Graphics2D g2;
    private Font minecraft, upheavtt;
    private BufferedImage guideImage;
    private BufferedImage playerBlue, playerRed, playerYellow;
    private BufferedImage volume, volumeMute, heart;
    private Stroke stroke1 = new BasicStroke(6f);
    private int guideX = 150;
    private int guideY = 0;
    public boolean quitGuideState = false;
    public boolean quitOptionState = false;

    public int getGuideX() {
        return guideX;
    }

    public int getGuideY() {
        return guideY;
    }

    public void setGuideY(int guideY) {
        this.guideY = guideY;
    }
    public MenuControl(PanelGame panelGame) {
        this.gp = panelGame;
        try {
            InputStream is = getClass().getResourceAsStream("/font/Minecraft.ttf");
            minecraft = Font.createFont(Font.TRUETYPE_FONT, is);

            is = getClass().getResourceAsStream("/font/upheavtt.ttf");
            upheavtt = Font.createFont(Font.TRUETYPE_FONT, is);

            guideImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("something/guide2.png")));

            playerBlue = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/blue/boy_down_1.png")));
            playerRed = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/red/boy_down_1.png")));
            playerYellow = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/yellow/boy_down_1.png")));

            volume = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("something/volume.png")));
            volumeMute = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("something/volume-mute.png")));
            heart = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("something/heart.png")));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public int getXforCenterText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screen_width / 2 - length / 2;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(upheavtt);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        //TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleState(g2);
            // GUIDE
            if (gp.guideState) {
                drawGuideState(g2);
            }
            // OPTIONS
            if (gp.optionState) {
                drawOptionState(g2);
            }
        }
        // GAME OVER
        if (gp.gameState == gp.gameOverState) {
            drawGameOver(g2);
        }
        // PAUSE
        if (gp.gameState == gp.pauseGameState) {
            drawPauseGame(g2);
        }
        //WIN
        if (gp.gameState == gp.winGame) {
            drawWinGame(g2);
        }
    }

    public void drawTitleState(Graphics2D g2) {
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screen_width, gp.screen_height);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        g2.setColor(Color.white);

        String gameName = "Bomberman";
        g2.drawString(gameName, getXforCenterText(gameName), 3 * PanelGame.tileSize);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g2.drawString("NEW GAME", getXforCenterText("NEW GAME"), 6 * PanelGame.tileSize);
        g2.drawString("OPTIONS", getXforCenterText("OPTIONS"), 8 * PanelGame.tileSize);
        g2.drawString("GUIDE", getXforCenterText("GUIDE"), 10 * PanelGame.tileSize);
        g2.drawString("QUIT ", getXforCenterText("QUIT"), 12 * PanelGame.tileSize);

        // POINTER
        g2.setColor(Color.orange);
        if (gp.commandNum == 0) {
            g2.drawString("NEW GAME", getXforCenterText("NEW GAME"), 6 * PanelGame.tileSize);
        }
        if (gp.commandNum == 1) {
            g2.drawString("OPTIONS", getXforCenterText("OPTIONS"), 8 * PanelGame.tileSize);
        }
        if (gp.commandNum == 2) {
            g2.drawString("GUIDE", getXforCenterText("GUIDE"), 10 * PanelGame.tileSize);
        }
        if (gp.commandNum == 3) {
            g2.drawString("QUIT ", getXforCenterText("QUIT"), 12 * PanelGame.tileSize);
        }
    }

    public void drawGuideState(Graphics2D g2) {
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screen_width, gp.screen_height);
        // guide
        g2.drawImage(guideImage, guideX, guideY, 800, 581, null);

        g2.setColor(Color.white);
        g2.drawString("<", 20, 40);

        if (quitGuideState) {
            g2.setColor(Color.orange);
            g2.drawString("<", 20, 40);
        }
    }

    public void drawOptionState(Graphics2D g2) {
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screen_width, gp.screen_height);

        g2.setColor(Color.white);
        g2.drawString("<", 20, 40);

        if (quitOptionState) {
            g2.setColor(Color.orange);
            g2.drawString("<", 20, 40);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 55F));
        g2.setColor(Color.white);
        // PLAYER
        g2.drawString("PLAYER:", 2 * PanelGame.tileSize, 3 * PanelGame.tileSize);
        g2.drawImage(playerBlue, 10 * PanelGame.tileSize, 2 * PanelGame.tileSize, 60, 60, null);
        g2.drawImage(playerRed, 15 * PanelGame.tileSize, 2 * PanelGame.tileSize, 60, 60, null);
        g2.drawImage(playerYellow, 20 * PanelGame.tileSize, 2 * PanelGame.tileSize, 60, 60, null);

        g2.setStroke(stroke1);
        g2.setColor(Color.orange);
        switch (gp.player.getColor()) {
            case "blue" -> g2.draw(new Rectangle(10 * PanelGame.tileSize - 7, 2 * PanelGame.tileSize - 7, 70, 70));
            case "red" -> g2.draw(new Rectangle(15 * PanelGame.tileSize - 7, 2 * PanelGame.tileSize - 7, 70, 70));
            case "yellow" -> g2.draw(new Rectangle(20 * PanelGame.tileSize - 7, 2 * PanelGame.tileSize - 7, 70, 70));
        }
        // MODE
        g2.setColor(Color.white);
        g2.drawString("MODE:", 2 * PanelGame.tileSize, 6 * PanelGame.tileSize);
        g2.drawString("DAY", 10 * PanelGame.tileSize, 6 * PanelGame.tileSize);
        g2.drawString("NIGHT", 17 * PanelGame.tileSize, 6 * PanelGame.tileSize);

        g2.setColor(Color.orange);
        if (gp.mode.equals("day")) {
            g2.drawString("DAY", 10 * PanelGame.tileSize, 6 * PanelGame.tileSize);
        } else if (gp.mode.equals("night")) {
            g2.drawString("NIGHT", 17 * PanelGame.tileSize, 6 * PanelGame.tileSize);
        }
        // MUSIC AND SOUND EFFECT
        g2.setColor(Color.white);
        g2.drawString("MUSIC:", 2 * PanelGame.tileSize, 9 * PanelGame.tileSize);
        g2.drawString("EFFECT:", 2 * PanelGame.tileSize, 12 * PanelGame.tileSize);

        g2.setColor(Color.orange);
        g2.draw(new Rectangle(10 * PanelGame.tileSize, 8 * PanelGame.tileSize - 10, 60, 60));
        g2.draw(new Rectangle(10 * PanelGame.tileSize, 11 * PanelGame.tileSize - 10, 60, 60));

        if (gp.sound.isMusic()) {
            g2.drawImage(volume, 10 * PanelGame.tileSize + 5, 8 * PanelGame.tileSize - 5, 45, 45, null);
        } else {
            g2.drawImage(volumeMute, 10 * PanelGame.tileSize + 5, 8 * PanelGame.tileSize - 5, 45, 45, null);
        }
        if (gp.sound.isEffect()) {
            g2.drawImage(volume, 10 * PanelGame.tileSize + 5, 11 * PanelGame.tileSize - 5, 45, 45, null);
        } else {
            g2.drawImage(volumeMute, 10 * PanelGame.tileSize + 5, 11 * PanelGame.tileSize - 5, 45, 45, null);
        }
    }

    public void drawInfoGame(Graphics2D g2) {
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, 31 * PanelGame.tileSize, PanelGame.tileSize);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        g2.setColor(Color.white);

        g2.drawString("TIME: " + gp.getTime(), 10, 30);

        g2.drawString("POINT: " + gp.point, 6 * PanelGame.tileSize, 30);

        g2.drawImage(heart, 14 * PanelGame.tileSize,0, 35, 35, null);
        g2.drawString("x" + gp.player.getLives(), 15 * PanelGame.tileSize, 30);

        g2.drawString("LEVEL: " + (gp.getLevel() + 1), 20 * PanelGame.tileSize, 30);

        g2.drawString("SPEED: " + gp.player.getSpeed(), 27 * PanelGame.tileSize + 10, 30);
    }

    public void drawPauseGame(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screen_width, gp.screen_height);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
        g2.setColor(Color.white);
        g2.drawString("PAUSE", getXforCenterText("PAUSE"), 6 * PanelGame.tileSize);
        //
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
        g2.drawString("RESUME", getXforCenterText("RESUME"), 9 * PanelGame.tileSize);
        // QUIT
        g2.drawString("QUIT", getXforCenterText("QUIT"), 11 * PanelGame.tileSize);

        g2.setColor(Color.orange);
        if (gp.commandNum == 0) {
            g2.drawString("RESUME", getXforCenterText("RESUME"), 9 * PanelGame.tileSize);
        }
        if (gp.commandNum == 1) {
            g2.drawString("QUIT", getXforCenterText("QUIT"), 11 * PanelGame.tileSize);
        }
    }

    public void drawGameOver(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screen_width, gp.screen_height);
        // TEXT: GAME OVER
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
        g2.setColor(Color.white);
        g2.drawString("GAME OVER", getXforCenterText("GAME OVER"), 6 * PanelGame.tileSize);
        // RETRY
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
        g2.drawString("RETRY", getXforCenterText("RETRY"), 9 * PanelGame.tileSize);
        // QUIT
        g2.drawString("QUIT", getXforCenterText("QUIT"), 11 * PanelGame.tileSize);
        // POINTER
        g2.setColor(Color.orange);
        if (gp.commandNum == 0) {
            g2.drawString("RETRY", getXforCenterText("RETRY"), 9 * PanelGame.tileSize);
        }
        if (gp.commandNum == 1) {
            g2.drawString("QUIT", getXforCenterText("QUIT"), 11 * PanelGame.tileSize);
        }
    }

    public void drawWinGame(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screen_width, gp.screen_height);
        // TEXT: GAME OVER
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 108F));
        g2.setColor(Color.white);
        g2.drawString("WIN!", getXforCenterText("WIN!"), 8 * PanelGame.tileSize);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
        g2.drawString("QUIT", getXforCenterText("QUIT"), 11 * PanelGame.tileSize);
        // POINTER
        g2.setColor(Color.orange);
        if (gp.commandNum == 0) {
            g2.drawString("QUIT", getXforCenterText("QUIT"), 11 * PanelGame.tileSize);
        }
    }
}
