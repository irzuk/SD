package org.Roguelike.UI;

import org.Roguelike.collections.GameFrame;
import org.Roguelike.collections.geometry.Point;
import org.Roguelike.collections.map.MapElementsParameters;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Drawer extends Frame {

    Canvas map = new Map();
    Canvas characteristics = new Characters();
    @Nullable GameFrame gameFrame;

    private static final int BORDER = 5;
    private static final int STRING_H = 20;

    private static final int MAP_X = BORDER;
    private static final int MAP_Y = BORDER;
    private static final int MAP_H = MapElementsParameters.MAP_HEIGHT;
    private static final int MAP_W = MapElementsParameters.MAP_WIDTH;

    private static final int PANEL_X = MAP_W + MAP_X + BORDER;
    private static final int PANEL_Y = BORDER;
    private static final int PANEL_H = MAP_H + 30;
    private static final int PANEL_W = 200;

    private LocalDateTime newRoomTimer = LocalDateTime.now();
    private LocalDateTime newItemTimer = LocalDateTime.now();
    private LocalDateTime experienceTimer = LocalDateTime.now();

    private String recievedItem;

    public Drawer() {
        setLayout(new FlowLayout());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        map.setBounds(MAP_X, MAP_Y, MAP_W, MAP_H);
        add(map);

        characteristics.setBounds(PANEL_X, PANEL_Y, PANEL_W, PANEL_H);
        add(characteristics);

        setSize(BORDER + MAP_X + MAP_W + BORDER + PANEL_W + BORDER + 100, BORDER + MAP_Y + MAP_H + BORDER + 100);
        setVisible(true);
    }

    public void drawFrame(GameFrame gameFrame) throws InterruptedException {

        this.gameFrame = new GameFrame(gameFrame);

        if (this.gameFrame.isStop()) {
            Dialog d = new Dialog(this, "Game over");
            d.setBounds((BORDER + MAP_X + MAP_W + BORDER + PANEL_W + BORDER) / 3, (BORDER + MAP_Y + MAP_H + BORDER) / 3, 300, 50);
            d.setVisible(true);
            Thread.sleep(5000);
            return;
        }

        characteristics.update(characteristics.getGraphics());
        if (gameFrame.isMapChanged()) {
            map.repaint();
        } else {
            map.repaint(gameFrame.getHeroLocation().leftTop().x() - 20, gameFrame.getHeroLocation().leftTop().y() - 20, 40, 40);
            for (var x : gameFrame.getEnemies()) {
                map.repaint(x.getEnemyLocation().leftTop().x() - 10, x.getEnemyLocation().leftTop().y() - 10, 20, 20);
            }
        }
    }

    private class Map extends Canvas {
        public void update(Graphics g) {
            paint(g);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (gameFrame == null || gameFrame.isStop()) {
                return;
            }
            g.setColor(Color.green);
            g.drawPolygon(gameFrame.getHeroLocation());

            g.setColor(Color.BLACK);
            for (var x : gameFrame.getMap().chests()) {
                g.drawPolygon(x);
            }

            for (var x : gameFrame.getMap().roomLines()) {
                g.drawLine(x.first().x(), x.first().y(), x.second().x(), x.second().y());
            }
            g.setColor(Color.WHITE);
            for (var x : gameFrame.getMap().doors()) {
                g.drawPolygon(x);
            }
            g.setColor(Color.RED);
            for (var x : gameFrame.getEnemies()) {
                g.drawPolygon(x.getEnemyLocation());
            }
        }
    }

    private class Characters extends Canvas {
        @SuppressWarnings("all")
        private final int currX = BORDER;
        private int currY = BORDER;

        private void addString(Graphics g, String s) {
            g.drawString(s, currX, currY);
            currY += STRING_H;
        }

        public void update(Graphics g) {
            paint(g);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            if (gameFrame == null || gameFrame.isStop()) {
                return;
            }
            currY = BORDER * 2;
            g.setColor(Color.black);
            addString(g, "Characteristics");
            addString(g, "Health: " + gameFrame.getInfo().health.current + " / " + gameFrame.getInfo().health.full);
            addString(g, "Cheerfulness: " + gameFrame.getInfo().cheerfullness.current + " / " + gameFrame.getInfo().cheerfullness.full);
            addString(g, "Satisfy: " + gameFrame.getInfo().satiety.current + " / " + gameFrame.getInfo().satiety.full);
            addString(g, "Experience: " + gameFrame.getInfo().experience.current + " / " + gameFrame.getInfo().experience.full);

            addString(g, "_________");
            addString(g, "Items:");

            for (var item : gameFrame.getItems()) {
                addString(g, item.getDescription());
            }
            var item = gameFrame.getReceivedItem();

            if (item != null) {
                newItemTimer = LocalDateTime.now();
                recievedItem = item.getDescription();
            }
            addString(g, "_________");
            if (ChronoUnit.MILLIS.between(newItemTimer, LocalDateTime.now()) < 3000) {
                addString(g, "Received: " + recievedItem);
            }

            if (gameFrame.isMapChanged()) {
                newRoomTimer = LocalDateTime.now();
            }
            if (ChronoUnit.MILLIS.between(newRoomTimer, LocalDateTime.now()) < 3000) {
                addString(g, "Welcome to new room!");
            }

            if(gameFrame.getInfo().experience.current == gameFrame.getInfo().experience.full) {
                experienceTimer = LocalDateTime.now();
            }
            if(ChronoUnit.MILLIS.between(experienceTimer, LocalDateTime.now()) < 3000) {
                addString(g, "Level passed!");
            }

            if(gameFrame.getEnemies().isEmpty()) {
                addString(g, "All enemies are dead, go to the other room!");
            }
        }
    }
}
