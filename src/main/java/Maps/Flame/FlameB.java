package Maps.Flame;

import Panel.PanelGame;
import entity.Bomb;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class FlameB extends Flame {

    public FlameB(PanelGame gp, String type) {
        this.panel = gp;
        getImage(type);
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public void getImage(String type) {
        try {
            if (type.equals("advanced_2") || type.equals("normal")) {
                flame1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/s1.png")));
                flame2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/s2.png")));
                flame3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/s3.png")));
            } else {
                flame1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/w,s1.png")));
                flame2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/w,s2.png")));
                flame3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/w,s3.png")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawFlame(Graphics2D g2, Bomb bomb) {
         BufferedImage image = null;
        if (bomb.spriteNum == 1) image = flame1;
        if (bomb.spriteNum == 2) image = flame2;
        if (bomb.spriteNum == 3) image = flame3;
        if (image != null) {
            g2.drawImage(image, x, y, PanelGame.tileSize, PanelGame.tileSize, null);
        }
    }
    @Override
    public void draw(Graphics2D g2) {
    }
}
