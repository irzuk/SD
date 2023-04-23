package org.Roguelike;

import org.Roguelike.model.GameModel;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.awt.Graphics;
import java.time.LocalDateTime;
import java.awt.AWTEvent;
import java.util.EventListener;
import java.util.Observable;
import java.util.Observer;


public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        GameModel game = new GameModel();
        Thread t = new Thread(game);
        t.start();
    }

}
