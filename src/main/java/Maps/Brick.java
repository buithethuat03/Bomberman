package Maps;

import Maps.Flame.Flame;
import Panel.PanelGame;
import entity.Bomb;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Brick extends object {

    private PanelGame gp;
    private BufferedImage normal, explode1, explode2, explode3;
    private int spriteCounter = 0;
    private int spriteNum = 1;
    private String status;
    private boolean check = true;

    public Brick(PanelGame gp) {
        this.gp = gp;
        setupNewFragileWall();
        getImage();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void getImage() {
        try {
            if (gp.mode.equals("day")) {
                normal = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("brick/brick.png")));
            } else if (gp.mode.equals("night")) {
                normal = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("brick/brick_night.png")));
            }
            explode1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("brick/brick_exploded.png")));
            explode2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("brick/brick_exploded1.png")));
            explode3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("brick/brick_exploded2.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateWaitBreaking(Bomb bomb) {
        if (bomb.getStatus().equals("exploding") && status.equals("normal")) {
            for (Flame flame : bomb.flameList) {
                if (Math.abs(flame.y - y) == 0 && Math.abs(flame.x - x) == 0) {
                    status = "breaking";
                    break;
                }
            }
        }
    }

    public void updateBreaking() {
        if (status.equals("breaking")) {
            if(check) {
                try {
                    gp.sound.brickbroken();
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
                check = false;
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum++;
                if (spriteNum > 3) {
                    status = "broken";
                }
                spriteCounter = 0;
            }
        }
    }

    void setupNewFragileWall() {
        x = 0;
        y = 0;
        status = "normal";
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (status) {
            case "normal" -> image = normal;
            case "breaking" -> {
                if (spriteNum == 1) image = explode1;
                if (spriteNum == 2) image = explode2;
                if (spriteNum == 3) image = explode3;
            }
        }
        if (image != null) {
            g2.drawImage(image, x, y, PanelGame.tileSize, PanelGame.tileSize, null);
        }
    }
}
