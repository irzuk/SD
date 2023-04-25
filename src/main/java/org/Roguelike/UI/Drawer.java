package org.Roguelike.UI;

import org.Roguelike.collections.GameFrame;
import org.Roguelike.collections.map.MapElementsParameters;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class Drawer extends Frame {

    @NotNull Canvas map = new Map();
    @NotNull Canvas timer = new Canvas();
    @NotNull Canvas characteristics = new Characters();
    @NotNull Canvas state = new Canvas();

    GameFrame oldGameFrame;
    @Nullable GameFrame gameFrame;

    private static int BORDER = 5;
    private static int STRING_H = 20;

    private static int MAP_X = 50;
    private static int MAP_Y = 50;
    private static int MAP_H = MapElementsParameters.MAP_HEIGHT;
    private static int MAP_W = MapElementsParameters.MAP_WIDTH;

    private static int PANEL_X = MAP_W + MAP_X + BORDER;
    private static int PANEL_Y = BORDER;
    private static int PANEL_H = MAP_H;
    private static int PANEL_W = 200;


    public Drawer() {
        setLayout(new FlowLayout());

        map.setBounds(MAP_X, MAP_Y, MAP_W, MAP_H);
        add(map);

        characteristics.setBounds(PANEL_X, PANEL_Y, PANEL_W, PANEL_H);
        add(characteristics);

        setSize(BORDER + MAP_X + MAP_W + BORDER + PANEL_W + BORDER, BORDER + MAP_Y + MAP_H + BORDER);
        setVisible(true);

    }

    public void drawFrame(GameFrame gameFrame) {

        if (gameFrame.isStop()) {
            Dialog d = new Dialog(this, "Game over");
            System.exit(1); // TODO: Why we don't reach this point?
        }
        this.gameFrame = gameFrame;

        gameFrame.getMap().roomLines();
        gameFrame.getMap().doors();

        map.repaint();
        characteristics.repaint();
    }

    private class Map extends Canvas {
        public void update(Graphics g) {
            if (gameFrame == null) {
                return;
            }
            MapElement h = gameFrame.getHeroLocation();
            g.clearRect(h.leftTop().x() - MapElementsParameters.HERO_HEIGHT * 2, h.leftTop().y() - MapElementsParameters.HERO_WIDTH * 2, MapElementsParameters.HERO_HEIGHT * 4, MapElementsParameters.HERO_WIDTH * 4);
            paint(g);
        }

        @Override
        public void paint(Graphics g) {
            if (gameFrame == null) {
                return;
            }
            g.setColor(Color.RED);
            g.drawPolygon(gameFrame.getHeroLocation());

            g.setColor(Color.BLACK);
            for (var x : gameFrame.getMap().chests()) {
                g.drawPolygon(x);
            }
        }
    }

    private class Characters extends Canvas {
        private int currX = BORDER;
        private int currY = BORDER;

        private void addString(Graphics g, String s) {
            g.drawString(s, currX, currY);
            currY += STRING_H;
        }

        @Override
        public void paint(Graphics g) {
            if (gameFrame == null) {
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
                addString(g, item.name() + " " + item.getDescription());
            }
            if (gameFrame.getReceivedItem() != null)
                addString(g, "Received " + gameFrame.getReceivedItem().getDescription());
        }
    }
}


//
// g.drawString("TIME LEFT", 20, 20);
//        g.drawString(LocalDateTime.now().toString(), 20, 35);
//        // right panel
//        // character characteristics (array of them)
//        g.drawString("Health", 1000, 20);
//        g.drawString("Happiness", 1000, 40);
//        // backpack(array of them)
//        g.drawString("Backpack", 1000, 200);
//        // current cloth
//        g.drawString("Current cloth: cloth_name", 1000, 500);
//        // map
//        // field
//        g.setColor(Color.lightGray);
//        g.fillRect(100, 100, 800, 500);
//        // character
//        g.setColor(Color.black);
//        g.drawString("*", c.x, c.y);
//        // things on map (array of them) (rectangulars?)
//        g.drawString("e", 350, 350);