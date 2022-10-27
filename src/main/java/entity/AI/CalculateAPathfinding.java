package entity.AI;

import java.util.ArrayList;

public class CalculateAPathfinding {
    //
    final int maxCol = 31;
    final int maxRow = 14;
    final int nodeSize = 36;

    Node[][] node = new Node[maxRow][maxCol];
    Node startNode, goalNode, currentNode;

    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();

    boolean goalReached = false;
    public CalculateAPathfinding(int [][]matrix, int px, int py, int ox, int oy, String typeEntity) {
        px = (px + nodeSize / 2) / nodeSize;
        py = (py + nodeSize / 2) / nodeSize;
        ox = (ox + nodeSize / 2) / nodeSize;
        oy = (oy + nodeSize / 2) / nodeSize;
        for (int idx = 0; idx < maxRow; idx++) {
            for (int idy = 0; idy < maxCol; idy++) {
                node[idx][idy] = new Node(idx, idy);
            }
        }
        setStartNode(oy, ox);
        setGoalNode(py, px);

        setSolidNode(matrix, typeEntity);
        setCostOnNodes();
    }

    public void setStartNode(int idx, int idy) {
        node[idx][idy].setAsStart();
        startNode = node[idx][idy];
        currentNode = startNode;
    }

    public void setGoalNode(int idx, int idy) {
        node[idx][idy].setAsGoal();
        goalNode = node[idx][idy];
    }

    private void setSolidNode(int[][] matrix, String typeEntity) {
        for (int idx = 0; idx < maxRow; idx++) {
            for (int idy = 0; idy < maxCol; idy++) {
                if (matrix[idx][idy] == 1) {
                    node[idx][idy].setAsSolid();
                    //System.out.println("Solid: " + idx + ", " + idy);
                }
                if (matrix[idx][idy] == 2 && typeEntity.equals("oneal")) {
                    node[idx][idy].setAsSolid();
                    //System.out.println("Solid: " + idx + ", " + idy);
                }
            }
        }
    }

    private void setCostOnNodes() {
        for (int idx = 0; idx < maxRow; idx++) {
            for (int idy = 0; idy < maxCol; idy++) {
                getCost(node[idx][idy]);
            }
        }
    }
    private void getCost(Node node) {
        //G-Cost
        int dy = Math.abs(node.col - startNode.col);
        int dx = Math.abs(node.row - startNode.row);
        node.gCost = dx + dy;

        //H-Cost
        dy = Math.abs(node.col - goalNode.col);
        dx = Math.abs(node.row - goalNode.row);
        node.hCost = dx + dy;

        //F-Cost
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {
        int t = 0;
        while (!goalReached) {
            t++;
            if (t > 199) {
                //System.out.println("Time limit exceeded!");
                return false;
            }
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

             //
            if (row - 1 >= 0) {
                openNode(node[row - 1][col]);
            }
            if (col - 1 >= 0) {
                openNode(node[row][col - 1]);
            }
            if (row + 1 < maxRow) {
                openNode(node[row + 1][col]);
            }
            if (col + 1 < maxCol) {
                openNode(node[row][col + 1]);
            }

            //
            int bestNodeIndex = 0;
            int bestNodefCost = 999;
            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodefCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }
            if (openList.size() > 0) {
                currentNode = openList.get(bestNodeIndex);
            }
            if (currentNode == goalNode) {
                goalReached = true;
            }
        }
        return true;
    }

    private void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.setAsOpen();
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public String getDirection() {
        boolean flag = search();
        if (flag) {
            Node node = goalNode;
            while (node.parent != null && !node.parent.equals(startNode)) {
                node = node.parent;
            }
            if (node.row == startNode.row) {
                if (startNode.col < node.col) {
                    return "right";
                } else {
                    return "left";
                }
            }
            if (node.col == startNode.col) {
                if (startNode.row < node.row) {
                    return "down";
                } else {
                    return "up";
                }
            }
        }
        return "false";
    }
}
