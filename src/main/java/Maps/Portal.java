package Maps;

import Panel.PanelGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Portal extends object {
    PanelGame panel;

    public Portal(PanelGame gp) {
        this.panel = gp;
        setDefaultValues();
        getImage();
    }

    private void setDefaultValues() {
        this.x = 36;
        this.y = 108;
    }

    private void getImage() {
        try {
            normal = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("portal/portal.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = normal;
        g2.drawImage(image, x, y, PanelGame.tileSize, PanelGame.tileSize, null);
    }
}
