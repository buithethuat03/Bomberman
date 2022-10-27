package Maps;

import Panel.PanelGame;
import item.Item;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Portal extends Item {
    PanelGame panel;

    public Portal(PanelGame gp, String path) {
        this.panel = gp;
        getImage();
        setRandomLocation(path);
    }
    @Override
    public void updateItem() {

    }

    public void getImage() {
        try {
            normal = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("portal/portal.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void drawItem(Graphics2D g2) {
        g2.drawImage(normal, x, y, PanelGame.tileSize, PanelGame.tileSize, null);
    }
}
