package Maps.Flame;

import java.awt.*;
import java.awt.image.BufferedImage;

import Maps.object;
import Panel.PanelGame;
import entity.Bomb;

public abstract class Flame extends object {

    protected PanelGame panel;
    protected BufferedImage flame1, flame2, flame3;
    protected int spriteNum;

    public abstract void getImage(String type);

    public abstract void drawFlame(Graphics2D g2, Bomb bomb);
}
