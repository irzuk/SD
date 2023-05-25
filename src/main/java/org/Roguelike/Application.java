package org.Roguelike;

import org.Roguelike.UI.Drawer;
import org.Roguelike.UI.Listener;
import org.Roguelike.model.GameModel;

import java.awt.event.KeyListener;

public class Application {

    public static void main(String[] args) {
        Drawer drawer = new Drawer();
        GameModel game = new GameModel(drawer);
        KeyListener listener = new Listener(game);
        drawer.addKeyListener(listener);
        Thread t = new Thread(game);
        t.start();
    }
}
