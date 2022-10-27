package item;
import Panel.PanelGame;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.*;

public class BombItem extends Item {
    public BombItem(PanelGame gp) {
        status = "null";
        this.gp = gp;
        getImage();
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void getImage() {
        try {
            normal = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Item/BombItem.png")));
        } catch (IOException e) {
            System.err.println("Loi load anh");
        }
    }

    public void updateItem() {
        boolean check = Math.abs(gp.player.getX() - x) < PanelGame.tileSize - 10 && Math.abs(gp.player.getY() - y) < PanelGame.tileSize - 10;
        if (check) {
            try {
                gp.sound.item();
            } catch (
                    Exception e) {
                e.printStackTrace();
            }
            gp.player.setBombCounts(2);
            status = "remove";
        }
    }
    @Override
    public void drawItem(Graphics2D g2) {
        g2.drawImage(normal, x, y, PanelGame.tileSize, PanelGame.tileSize, null);
    }
}
