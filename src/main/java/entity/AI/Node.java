package entity.AI;

public class Node {
    Node parent;
    int col;
    int row;
    int gCost;
    int hCost;
    int fCost;
    boolean start = false;
    boolean goal = false;
    boolean solid = false;
    boolean open = false;
    boolean checked = false;

    public Node(int row, int col) {
        this.col = col;
        this.row = row;
    }

    public void setAsStart() {
        start = true;
    }

    public void setAsGoal() {
        goal = true;
    }

    public void setAsSolid() {
        solid = true;
    }

    public void setAsOpen() {
        open = true;
    }

    public void setAsChecked() {
        checked = true;
    }

    public boolean equals(Node other) {
        return other.row == row && other.col == col;
    }
}
