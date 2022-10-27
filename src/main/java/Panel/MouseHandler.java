package Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    private PanelGame gp;
    private boolean enterFrame = false;
    private int posFrameX = 0;
    private int posFrameY = 0;

    public MouseHandler(PanelGame gp) {
        this.gp = gp;
    }

    public void mouseLocation() {
        double mouseX = MouseInfo.getPointerInfo().getLocation().getX() - posFrameX;
        double mouseY = MouseInfo.getPointerInfo().getLocation().getY() - posFrameY;

        if (gp.gameState == gp.titleState) {
            if (mouseX >= 440 && mouseX <= 675 && mouseY >= 190 && mouseY <= 215) {
                gp.commandNum = 0;
            } else if (mouseX >= 455 && mouseX <= 658 && mouseY >= 265 && mouseY <= 285) {
                gp.commandNum = 1;
            } else if (mouseX >= 485 && mouseX <= 630 && mouseY >= 337 && mouseY <= 360) {
                gp.commandNum = 2;
            } else if (mouseX >= 500 && mouseX <= 615 && mouseY >= 408 && mouseY <= 432) {
                gp.commandNum = 3;
            } else {
                gp.commandNum = -1;
            }

            if (gp.guideState) {
                if (mouseX >= 20 && mouseX <= 40 && mouseY >= 15 && mouseY <= 40) {
                    gp.menuControl.quitGuideState = true;
                } else {
                    gp.menuControl.quitGuideState = false;
                }
            }

            if (gp.optionState) {
                if (mouseX >= 20 && mouseX <= 40 && mouseY >= 15 && mouseY <= 40) {
                    gp.menuControl.quitOptionState = true;
                } else {
                    gp.menuControl.quitOptionState = false;
                }
            }
        }

        if (gp.gameState == gp.gameOverState) {
            if (mouseX >= 500 && mouseX <= 614 && mouseY >= 305 && mouseY <= 323) {
                gp.commandNum = 0;
            } else if (mouseX >= 516 && mouseX <= 600 && mouseY >= 377 && mouseY <= 396) {
                gp.commandNum = 1;
            } else {
                gp.commandNum = -1;
            }
        }

        if (gp.gameState == gp.pauseGameState) {
            if (mouseX >= 500 && mouseX <= 614 && mouseY >= 305 && mouseY <= 323) {
                gp.commandNum = 0;
            } else if (mouseX >= 516 && mouseX <= 600 && mouseY >= 377 && mouseY <= 396) {
                gp.commandNum = 1;
            } else {
                gp.commandNum = -1;
            }
        }
        if (gp.gameState == gp.winGame) {
            if (mouseX >= 516 && mouseX <= 600 && mouseY >= 377 && mouseY <= 396) {
                gp.commandNum = 0;
            } else {
                gp.commandNum = -1;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (gp.gameState == gp.titleState && SwingUtilities.isLeftMouseButton(e)) {
            if (!gp.guideState && !gp.optionState) {
                if (e.getX() >= 440 && e.getX() <= 675 && e.getY() >= 190 && e.getY() <= 215) {
                    gp.gameState = gp.playState;
                } else if (e.getX() >= 455 && e.getX() <= 658 && e.getY() >= 265 && e.getY() <= 285) {
                    gp.optionState = true;
                } else if (e.getX() >= 485 && e.getX() <= 630 && e.getY() >= 337 && e.getY() <= 360) {
                    gp.guideState = true;
                } else if (e.getX() >= 500 && e.getX() <= 615 && e.getY() >= 408 && e.getY() <= 432) {
                    System.exit(0);
                }
            }
            if (gp.guideState) {
                if (e.getX() >= 20 && e.getX() <= 40 && e.getY() >= 15 && e.getY() <= 40) {
                    gp.guideState = false;
                }
            }
            if (gp.optionState) {
                if (e.getX() >= 20 && e.getX() <= 40 && e.getY() >= 15 && e.getY() <= 40) {
                    gp.player.getPlayerImage();
                    gp.tle.getTileImage();
                    gp.tle.loadMode();
                    gp.optionState = false;
                }
                // player
                if (e.getX() >= 10 * PanelGame.tileSize && e.getX() <= 10 * PanelGame.tileSize + 60 && e.getY() >= 2 * PanelGame.tileSize && e.getY() <= 2 * PanelGame.tileSize + 60) {
                    gp.player.setColor("blue");
                } else if (e.getX() >= 15 * PanelGame.tileSize && e.getX() <= 15 * PanelGame.tileSize + 60 && e.getY() >= 2 * PanelGame.tileSize && e.getY() <= 2 * PanelGame.tileSize + 60) {
                    gp.player.setColor("red");
                } else if (e.getX() >= 20 * PanelGame.tileSize && e.getX() <= 20 * PanelGame.tileSize + 60 && e.getY() >= 2 * PanelGame.tileSize && e.getY() <= 2 * PanelGame.tileSize + 60) {
                    gp.player.setColor("yellow");
                }
                //mode
                if (e.getX() >= 360 && e.getX() <= 468 && e.getY() >= 185 && e.getY() <= 215) {
                    gp.mode = "day";
                } else if (e.getX() >= 610 && e.getX() <= 780 && e.getY() >= 185 && e.getY() <= 215) {
                    gp.mode = "night";
                }
                //music
                if (e.getX() >= 10 * PanelGame.tileSize + 5 && e.getX() <= 10 * PanelGame.tileSize + 50 && e.getY() >= 8 * PanelGame.tileSize - 5 && e.getY() <= 8 * PanelGame.tileSize + 40) {
                    gp.sound.setOpenMusic(!gp.sound.isMusic());
                } else if (e.getX() >= 10 * PanelGame.tileSize + 5 && e.getX() <= 10 * PanelGame.tileSize + 50 && e.getY() >= 11 * PanelGame.tileSize - 5 && e.getY() <= 11 * PanelGame.tileSize + 40) {
                    gp.sound.setOpenEffect(!gp.sound.isEffect());
                }
            }
        }
        if (gp.gameState == gp.gameOverState && SwingUtilities.isLeftMouseButton(e)) {
            if (e.getX() >= 500 && e.getX() <= 614 && e.getY() >= 305 && e.getY() <= 323) {
                gp.restartGame(gp.pathMap[0]);
                gp.gameState = gp.playState;
            } else if (e.getX() >= 516 && e.getX() <= 600 && e.getY() >= 377 && e.getY() <= 396) {
                gp.gameState = gp.titleState;
                gp.commandNum = 0;
                gp.restartGame(gp.pathMap[0]);
            }
        }
        if (gp.gameState == gp.pauseGameState && SwingUtilities.isLeftMouseButton(e)) {
            if (e.getX() >= 500 && e.getX() <= 614 && e.getY() >= 305 && e.getY() <= 323) {
                gp.gameState = gp.playState;
            } else if (e.getX() >= 516 && e.getX() <= 600 && e.getY() >= 377 && e.getY() <= 396) {
                gp.gameState = gp.titleState;
                gp.commandNum = 0;
                gp.restartGame(gp.pathMap[0]);
            }
        }
        if (gp.gameState == gp.winGame && SwingUtilities.isLeftMouseButton(e)) {
            if (e.getX() >= 516 && e.getX() <= 600 && e.getY() >= 377 && e.getY() <= 396) {
                gp.gameState = gp.titleState;
                gp.commandNum = 0;
                gp.restartGame(gp.pathMap[0]);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        enterFrame = true;
        posFrameX = e.getXOnScreen() - e.getX();
        posFrameY = e.getYOnScreen() - e.getY();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        enterFrame = false;
    }

}
