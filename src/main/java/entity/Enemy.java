package entity;

import Panel.PanelGame;

import java.util.Random;

public abstract class Enemy extends Entity {

    protected PanelGame gp;
    Random rand = new Random();
    int[][] map = new int[14][31];
    public abstract void getImage();

}
