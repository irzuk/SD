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

        if (e.getKeyCode() == 50) {
            gameModel.setKeyEvent(org.Roguelike.model.KeyEvent.USE_THING_2);
        }

        if (e.getKeyCode() == 51) {
            gameModel.setKeyEvent(org.Roguelike.model.KeyEvent.USE_THING_3);
        }

        if (e.getKeyCode() == 52) {
            gameModel.setKeyEvent(org.Roguelike.model.KeyEvent.USE_THING_4);
        }

        if (e.getKeyCode() == 53) {
            gameModel.setKeyEvent(org.Roguelike.model.KeyEvent.USE_THING_5);
        }

        if (e.getKeyCode() == 54) {
            gameModel.setKeyEvent(org.Roguelike.model.KeyEvent.USE_THING_6);
        }

        if (e.getKeyCode() == 55) {
            gameModel.setKeyEvent(org.Roguelike.model.KeyEvent.USE_THING_7);
        }

        if (e.getKeyCode() == 56) {
            gameModel.setKeyEvent(org.Roguelike.model.KeyEvent.USE_THING_8);
        }
        if (e.getKeyCode() == 57) {
            gameModel.setKeyEvent(org.Roguelike.model.KeyEvent.USE_THING_9);
        }

        if (e.getKeyCode() == 27) {
            gameModel.stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
