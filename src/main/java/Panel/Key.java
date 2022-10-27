package Panel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Key implements KeyListener {
    private PanelGame gp;

    public boolean up, down, left, right, space;

    public Key(PanelGame panelGame) {
        this.gp = panelGame;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.gameState == gp.titleState) {
            keyTitleState(e, code);
            if (gp.guideState) {
                keyGuideState(e, code);
            }
            if (gp.optionState) {
                keyOptionState(e, code);
            }
        }
        if (gp.gameState == gp.playState) {
            keyPlayState(e, code);
        }
        if (gp.gameState == gp.gameOverState) {
            keyGameOverState(e, code);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            up = false;
        }
        if (code == KeyEvent.VK_A) {
            left = false;
        }
        if (code == KeyEvent.VK_S) {
            down = false;
        }
        if (code == KeyEvent.VK_D) {
            right = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            space = false;
        }
    }

    public void keyTitleState(KeyEvent e, int code) {
        if (code == KeyEvent.VK_W) {
            gp.commandNum--;
            if (gp.commandNum <= 0) {
                gp.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.commandNum++;
            if (gp.commandNum >= 3) {
                gp.commandNum = 3;
            }
        }
        if (gp.commandNum == 0 && code == KeyEvent.VK_ENTER && !gp.guideState) {
            gp.gameState = gp.playState;
        }
        if (gp.commandNum == 2 && code == KeyEvent.VK_ENTER) {
            gp.guideState = true;
        }
        if (gp.commandNum == 3 && code == KeyEvent.VK_ENTER) {
            System.exit(0);
        }
    }

    public void keyGuideState(KeyEvent e, int code) {
        if (code == KeyEvent.VK_S) {
            gp.menuControl.setGuideY(gp.menuControl.getGuideY() - 5);
            if (gp.menuControl.getGuideY() <= -74) {
                gp.menuControl.setGuideY(-74);
            }
        }
        if (code == KeyEvent.VK_W) {
            gp.menuControl.setGuideY(gp.menuControl.getGuideY() + 5);
            if (gp.menuControl.getGuideY() >= 0) {
                gp.menuControl.setGuideY(0);
            }
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.guideState = false;
        }
    }

    public void keyOptionState(KeyEvent e, int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.player.getPlayerImage();
            gp.tle.getTileImage();
            gp.tle.loadMode();
            gp.optionState = false;
        }
    }

    public void keyPlayState(KeyEvent e, int code) {
        if (code == KeyEvent.VK_W) {
            up = true;
        }
        if (code == KeyEvent.VK_A) {
            left = true;
        }
        if (code == KeyEvent.VK_S) {
            down = true;
        }
        if (code == KeyEvent.VK_D) {
            right = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            space = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.pauseGameState;
        }
    }

    public void keyPauseGameState(KeyEvent e, int code) {
        //TODO: Pause game
    }

    public void keyGameOverState(KeyEvent e, int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.titleState;
            gp.commandNum = 0;
            gp.restartGame(gp.pathMap[0]);
        }
    }
}
