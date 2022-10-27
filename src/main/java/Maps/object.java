package Maps;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class object {

    protected int x, y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected BufferedImage normal;
    public abstract void draw(Graphics2D g2);
}
