package org.Roguelike;

import org.Roguelike.model.GameModel;

public class Application {

    public static void main(String[] args) {
        GameModel game = new GameModel();
        Thread t = new Thread(game);
        t.start();
    }

}
