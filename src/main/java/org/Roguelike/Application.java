package org.Roguelike;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.awt.Graphics;
import java.time.LocalDateTime;
import java.awt.AWTEvent;
import java.util.EventListener;
import java.util.Observable;
import java.util.Observer;


class ONewsAgency extends Observable {
    private String news;

    public void setNews(String news) {
        this.news = news;
        setChanged();
        notifyObservers(news);
    }
}

class Character {
    int x = 300;
    int y = 300;
}

public class Application extends Frame implements KeyListener, Observer {

    final private int hight = 600;
    final private int weight = 1200;
    Character character = new Character();

    private MyCanvas c = new MyCanvas(hight, weight);

    public Application() throws IOException, InterruptedException {
        setLayout(new FlowLayout());
        c.setCharPosition(character);
        add(c);

        addKeyListener(this);

        setSize(weight, hight);
        setVisible(true);


    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Application app = new Application();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    System.err.println("Started");
                    while (true){
                        Thread.sleep(1000);
                        ONewsAgency observable = new ONewsAgency();
                        observable.addObserver(app);
                        observable.setNews("news");
                        System.err.println("Event created");

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        Thread t = new Thread(r);
        t.start();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyChar() == 'w') {
            character.y += 5;
            c.update(c.getGraphics());
        }
        if (e.getKeyChar() == 's') {
            character.y -= 5;
            c.update(c.getGraphics());
        }
        if (e.getKeyChar() == 'a') {
            character.x -= 5;
            c.update(c.getGraphics());
        }
        if (e.getKeyChar() == 'd') {
            character.x += 5;
            c.update(c.getGraphics());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void update(Observable o, Object arg) {
        c.update(c.getGraphics());
    }
}

class MyCanvas extends Canvas {
    // class constructor
    private Character c;

    public MyCanvas(int height, int weight) {
        setBackground(Color.WHITE);
        setSize(weight, height);
    }

    public void setCharPosition(Character c) {
        this.c = c;
    }

    // paint() method to draw inside the canvas
    public void paint(Graphics g) {
        // timer
        g.drawString("TIME LEFT", 20, 20);
        g.drawString(LocalDateTime.now().toString(), 20, 35);
        // right panel
        // character characteristics (array of them)
        g.drawString("Health", 1000, 20);
        g.drawString("Happiness", 1000, 40);
        // backpack(array of them)
        g.drawString("Backpack", 1000, 200);
        // current cloth
        g.drawString("Current cloth: cloth_name", 1000, 500);
        // map
        // field
        g.setColor(Color.lightGray);
        g.fillRect(100, 100, 800, 500);
        // character
        g.setColor(Color.black);
        g.drawString("*", c.x, c.y);
        // things on map (array of them) (rectangulars?)
        g.drawString("e", 350, 350);
    }
}
