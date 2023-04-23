package org.Roguelike.UI;

import org.Roguelike.model.GameModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Listener implements KeyListener {
    GameModel gameModel;

    public Listener(GameModel model) {
        gameModel = model;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 87) {
            System.err.println("Key pressed: up");
            gameModel.setKeyEvent(org.Roguelike.model.KeyEvent.GO_UP);
        }
        if (e.getKeyCode() == 83) {
            gameModel.setKeyEvent(org.Roguelike.model.KeyEvent.GO_DOWN);
        }

        if (e.getKeyCode() == 65) {
            gameModel.setKeyEvent(org.Roguelike.model.KeyEvent.GO_LEFT);
        }

        if (e.getKeyCode() == 68) {
            gameModel.setKeyEvent(org.Roguelike.model.KeyEvent.GO_RIGHT);
        }

        if (e.getKeyCode() == 49) {
            gameModel.setKeyEvent(org.Roguelike.model.KeyEvent.USE_THING_1);
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
