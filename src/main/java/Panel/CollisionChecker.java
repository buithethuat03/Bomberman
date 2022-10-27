package Panel;

import Maps.Brick;
import Maps.Flame.Flame;
import Maps.Tile;
import entity.*;

import java.util.List;

public class CollisionChecker {
    private PanelGame gp;

    public CollisionChecker(PanelGame gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        int entityLeftX = entity.getX() + entity.solidArea.x;
        int entityRightX = entity.getX() + entity.solidArea.x + entity.solidArea.width;
        int entityTopY = entity.getY() + entity.solidArea.y;
        int entityBottomY = entity.getY() + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = (entityLeftX) / PanelGame.tileSize;
        // quan trọng fix bug

        // * nếu entityLeftX chia hết cho PanelGame.tileSize thì trường hợp đó nó sẽ đi được tại vi trí biên ngoài của wall
        // chỗ này hơi khó hiểu nhưng mà quan trọng có gì hỏi lại thành :v

        if (entityLeftCol * PanelGame.tileSize == entityLeftX) {
            entityLeftX += 1;
            entityLeftCol = (entityLeftX) / PanelGame.tileSize;
        }

        int entityRightCol = (entityRightX) / PanelGame.tileSize;
        int entityTopRow = (entityTopY) / PanelGame.tileSize;
        if (entityTopRow * PanelGame.tileSize == entityTopY) {
            entityTopY++;
            entityTopRow = entityTopY / PanelGame.tileSize;
        }
        int entityBottomRow = (entityBottomY) / PanelGame.tileSize;

        int tileNum1, tileNum2;
        Tile[] tiles = gp.tle.getTiles();
        switch (entity.getDirection()) {
            case "up" -> {
                entityTopY = entityTopY - entity.getSpeed();
                entityTopRow = (entityTopY) / PanelGame.tileSize;
                tileNum1 = gp.tle.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tle.mapTileNum[entityTopRow][entityRightCol];
                // check giữa entity với các tile( gồm grass với wall)
                if (tileNum1 != 2 && tileNum2 != 2) {

                    if ((tiles[tileNum1].collision || tiles[tileNum2].collision)) {
                        entity.collisionOn = true;
                    }
                }
                // check va chạm với brick
                if (entity instanceof Balloom || (entity instanceof Player) || entity instanceof Oneal) {
                    List<Brick> brickList = gp.tle.getBricks();
                    for (Brick i : brickList) {
                        if (((entityLeftX - i.getX() <= PanelGame.tileSize)
                                && (i.getX() - entityLeftX <= entity.solidArea.width))
                                && entityTopY - i.getY() <= PanelGame.tileSize
                                && entityTopY - i.getY() >= 0) {
                            entity.collisionOn = true;
                            break;
                        }
                    }
                }
            }
            case "down" -> {
                entityBottomY = entityBottomY + entity.getSpeed();
                entityBottomRow = (entityBottomY) / PanelGame.tileSize;
                tileNum1 = gp.tle.mapTileNum[entityBottomRow][entityLeftCol];
                tileNum2 = gp.tle.mapTileNum[entityBottomRow][entityRightCol];
                if (tileNum1 != 2 && tileNum2 != 2) {
                    if ((tiles[tileNum1].collision || tiles[tileNum2].collision)) {
                        entity.collisionOn = true;
                    }
                }
                if (entity instanceof Balloom || (entity instanceof Player) || entity instanceof Oneal) {
                    List<Brick> brickList = gp.tle.getBricks();
                    for (Brick i : brickList) {
                        if (((entityLeftX - i.getX() <= PanelGame.tileSize)
                                && (i.getX() - entityLeftX <= entity.solidArea.width))
                                && i.getY() - entityBottomY <= 0
                                && i.getY() - entityBottomY >= -PanelGame.tileSize) {
                            entity.collisionOn = true;
                            break;
                        }
                    }
                }
            }
            case "left" -> {
                entityLeftX = entityLeftX - entity.getSpeed();
                entityLeftCol = (entityLeftX) / PanelGame.tileSize;
                tileNum1 = gp.tle.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tle.mapTileNum[entityBottomRow][entityLeftCol];
                if (tileNum1 != 2 && tileNum2 != 2) {
                    if (tiles[tileNum1].collision || tiles[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                }
                if (entity instanceof Balloom || (entity instanceof Player) || entity instanceof Oneal) {
                    List<Brick> brickList = gp.tle.getBricks();
                    for (Brick i : brickList) {
                        if (((entityTopY - i.getY() <= PanelGame.tileSize)
                                && (i.getY() - entityTopY <= entity.solidArea.height))
                                && entityLeftX - i.getX() <= PanelGame.tileSize
                                && entityLeftX - i.getX() >= 0) {
                            entity.collisionOn = true;
                            break;
                        }
                    }
                }
            }
            case "right" -> {
                entityRightX = entityRightX + entity.getSpeed();
                entityRightCol = (entityRightX) / PanelGame.tileSize;
                tileNum1 = gp.tle.mapTileNum[entityTopRow][entityRightCol];
                tileNum2 = gp.tle.mapTileNum[entityBottomRow][entityRightCol];
                if (tileNum1 != 2 && tileNum2 != 2) {
                    if (tiles[tileNum1].collision || tiles[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                }

                if (entity instanceof Balloom || (entity instanceof Player) || entity instanceof Oneal) {
                    List<Brick> brickList = gp.tle.getBricks();
                    for (Brick i : brickList) {
                        if (((entityTopY - i.getY() <= PanelGame.tileSize)
                                && (i.getY() - entityTopY <= entity.solidArea.height))
                                && i.getX() - entityRightX <= 0
                                && i.getX() - entityRightX >= -PanelGame.tileSize) {
                            entity.collisionOn = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    // kiểm tra va chạm giữa bom và entity
    public String checkBomb(Entity entity, List<Bomb> bombs) {
        int entityLeftX = entity.getX() + entity.solidArea.x;
        int entityRightX = entity.getX() + entity.solidArea.x + entity.solidArea.width;
        int entityTopY = entity.getY() + entity.solidArea.y;
        int entityBottomY = entity.getY() + entity.solidArea.y + entity.solidArea.height;

        if(bombs!= null) {
            for (Bomb bomb : bombs) {
                if (bomb != null) {
                    if (bomb.getStatus().equals("exploding")) {
                        for (Flame i : bomb.flameList) {
                            switch (entity.getDirection()) {
                                case "up": {
                                    if (((entityLeftX - i.getX() <= PanelGame.tileSize)
                                            && (i.getX() - entityLeftX <= entity.solidArea.width))
                                            && entityTopY - i.getY() <= PanelGame.tileSize
                                            && entityTopY - i.getY() >= 0) {
                                        entity.setStatus("dead");
                                        entity.spriteNum = 1;
                                        entity.spriteCounter = 0;
                                        return "dead";
                                    }
                                }
                                case "down": {
                                    if (((entityLeftX - i.getX() <= PanelGame.tileSize)
                                            && (i.getX() - entityLeftX <= entity.solidArea.width))
                                            && i.getY() - entityBottomY <= 0
                                            && i.getY() - entityBottomY >= -PanelGame.tileSize) {
                                        entity.setStatus("dead");
                                        entity.spriteNum = 1;
                                        entity.spriteCounter = 0;
                                        return "dead";
                                    }
                                }
                                case "left": {
                                    if (((entityTopY - i.getY() <= PanelGame.tileSize)
                                            && (i.getY() - entityTopY <= entity.solidArea.height))
                                            && entityLeftX - i.getX() <= PanelGame.tileSize
                                            && entityLeftX - i.getX() >= 0) {
                                        entity.setStatus("dead");
                                        entity.spriteNum = 1;
                                        entity.spriteCounter = 0;
                                        return "dead";
                                    }
                                }
                                case "right": {
                                    if (((entityTopY - i.getY() <= PanelGame.tileSize)
                                            && (i.getY() - entityTopY <= entity.solidArea.height))
                                            && i.getX() - entityRightX <= 0
                                            && i.getX() - entityRightX >= -PanelGame.tileSize) {
                                        entity.setStatus("dead");
                                        entity.spriteNum = 1;
                                        entity.spriteCounter = 0;
                                        return "dead";
                                    }
                                }
                            }
                        }
                        if (Math.abs(entity.getX() - bomb.getX()) < PanelGame.tileSize && Math.abs(entity.getY() - bomb.getY()) < PanelGame.tileSize) {
                            entity.setStatus("dead");
                            entity.spriteNum = 1;
                            entity.spriteCounter = 0;
                            return "dead";
                        }
                    }
                }
            }
        }
        return "live";
    }
}
