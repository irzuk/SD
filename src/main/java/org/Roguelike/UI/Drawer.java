package org.Roguelike.UI;

import org.Roguelike.collections.GameFrame;
import org.Roguelike.collections.geometry.Point;
import org.Roguelike.collections.map.MapElementsParameters;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
    private static final int PANEL_H = MAP_H;
    private static final int PANEL_W = 200;

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

        map.repaint();
        characteristics.repaint();
    }

    private class Map extends Canvas {
        private MapElement prevHeroLocation = MapElement.fromPoint(new Point(0, 0));
        public void update(Graphics g) {
            if (gameFrame == null || gameFrame.isStop()) {
                return;
            }
            MapElement h = gameFrame.getHeroLocation();
            if(gameFrame.isMapChanged()) {
                System.out.println(gameFrame.isMapChanged());
                g.clearRect(0,0, MAP_W, MAP_H);
            }
            // TODO: Ira, please, check this code. I tried remove hero trace
            if (!h.toString().equals(prevHeroLocation.toString())) {
                g.clearRect(prevHeroLocation.leftTop().x() - BORDER,
                        prevHeroLocation.leftTop().y() - BORDER,
                        MapElementsParameters.HERO_WIDTH + 2 * BORDER,
                        MapElementsParameters.HERO_HEIGHT + 2 * BORDER);
                g.clearRect(h.leftTop().x() - BORDER,
                        h.leftTop().y() - BORDER,
                        MapElementsParameters.HERO_WIDTH + 2 * BORDER,
                        MapElementsParameters.HERO_HEIGHT + 2 * BORDER);
                prevHeroLocation = h;
            }
            paint(g);
        }

        @Override
        public void paint(Graphics g) {
            if (gameFrame == null || gameFrame.isStop()) {
                return;
            }
            g.setColor(Color.RED);
            g.drawPolygon(gameFrame.getHeroLocation());

            g.setColor(Color.BLACK);
            for (var x : gameFrame.getMap().chests()) {
                g.drawPolygon(x);
            }
            for (var x : gameFrame.getMap().doors()) {
                g.drawPolygon(x);
            }
//            for (var x : gameFrame.getMap().roomLines()) {
//                g.drawLine(x.first().x(), x.first().y(), x.second().x(), x.second().y());
//            }
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

        @Override
        public void paint(Graphics g) {
            if (gameFrame == null || gameFrame.isStop()) {
                return;
            }
            currY = BORDER * 2;
            g.setColor(Color.black);
            addString(g, "Characteristics");
            addString(g, "Health: " + gameFrame.getInfo().health.current + " / " + gameFrame.getInfo().health.full);
            addString(g, "Cheerfulness: " + gameFrame.getInfo().cheerfullness.current + " / " + gameFrame.getInfo().cheerfullness.full);
            addString(g, "Satisfy: " + gameFrame.getInfo().satiety.current + " / " + gameFrame.getInfo().satiety.full);

            addString(g, "_________");
            addString(g, "Items");

            for (var item : gameFrame.getItems()) {
                addString(g, item.getDescription());
            }
            var item = gameFrame.getReceivedItem();
            if (item != null) {
                addString(g, "Received: " + item.getDescription());
            }
            if(gameFrame.isMapChanged()) {
                addString(g, "Welcome to new room!");
            }
            //TODO: always null/false at this point of execution
            if (gameFrame.getReceivedItem() != null) {
                System.out.println(gameFrame.getReceivedItem().getDescription());
            }
            if (gameFrame.isMapChanged()) {
                System.out.println(gameFrame.isMapChanged());
            }
        }
    }
}
