package org.Roguelike.model;

import org.Roguelike.UI.Drawer;
import org.Roguelike.UI.Listener;
import org.Roguelike.collections.GameFrame;
import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.items.Item;
import org.Roguelike.collections.items.Thing;
import org.Roguelike.model.hero.HeroLogic;
import org.Roguelike.model.hero.SimpleHeroLogic;
import org.Roguelike.model.map.MapLogic;
import org.Roguelike.model.map.RoomLogic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.Roguelike.collections.map.MapElementsParameters.CELL_BORDER;

public class GameModel implements Runnable {
    private final @NotNull Drawer drawer = new Drawer();
    private final @NotNull MapLogic mapLogic = new RoomLogic();
    private final @NotNull HeroLogic heroLogic = new SimpleHeroLogic();
    private final @NotNull AtomicBoolean needStop = new AtomicBoolean(false);
    private final @NotNull Lock lock = new ReentrantLock();
    private final @NotNull LinkedList<@NotNull KeyEvent> queueKeyEvents = new LinkedList<>();
    private static final @NotNull Map<@NotNull KeyEvent, @NotNull Integer> eventToInd;

    static {
        eventToInd = new HashMap<>();
        eventToInd.put(KeyEvent.USE_THING_1, 0);
        eventToInd.put(KeyEvent.USE_THING_2, 1);
        eventToInd.put(KeyEvent.USE_THING_3, 2);
        eventToInd.put(KeyEvent.USE_THING_4, 3);
        eventToInd.put(KeyEvent.USE_THING_5, 4);
        eventToInd.put(KeyEvent.USE_THING_6, 5);
        eventToInd.put(KeyEvent.USE_THING_7, 6);
        eventToInd.put(KeyEvent.USE_THING_8, 7);
        eventToInd.put(KeyEvent.USE_THING_9, 8);
        eventToInd.put(KeyEvent.USE_THING_10, 9);
    }

    public GameModel() {
        KeyListener listener = new Listener(this);
        drawer.addKeyListener(listener);
    }

    @Override
    public void run() {
        long lastDecreasing = System.currentTimeMillis();
        while (!needStop.get()) {
            Item recievedItem = null;
            lock.lock();
            var keyEvent = queueKeyEvents.poll();
            lock.unlock();
            if (keyEvent != null) {
                recievedItem = processKeyEvent(keyEvent);
            }
            boolean stop = needStop.get();
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastDecreasing >= 1000) {
                stop = !heroLogic.decreaseCharacteristics();
                lastDecreasing = currentTime;
            }
            var gameFrame = new GameFrame.GameFrameBuilder()
                    .setMap(mapLogic.getMap())
                    .setThings(heroLogic.getAvailableItems().stream().map(item -> (Thing) item).toList())
                    .setHeroLocation(heroLogic.getLocation())
                    .setCharacteristicsInfo(heroLogic.getCharacteristics())
                    .setReceivedItem(recievedItem)
                    .setStop(stop)
                    .setMapChanged(mapLogic.pollMapChanged()).build();
            try {
                drawer.drawFrame(gameFrame);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        var gameFrame = new GameFrame.GameFrameBuilder().setStop(true).build();
        try {
            drawer.drawFrame(gameFrame);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setKeyEvent(@NotNull KeyEvent keyEvent) {
        lock.lock();
        queueKeyEvents.add(keyEvent);
        lock.unlock();
    }

    public void stop() {
        needStop.set(true);
    }

    private @Nullable Item processKeyEvent(KeyEvent keyEvent) {
        return switch (keyEvent) {
            case GO_UP -> processKeyWithVector(Vector.vectorDown(CELL_BORDER));
            case GO_DOWN -> processKeyWithVector(Vector.vectorUp(CELL_BORDER));
            case GO_LEFT -> processKeyWithVector(Vector.vectorLeft(CELL_BORDER));
            case GO_RIGHT -> processKeyWithVector(Vector.vectorRight(CELL_BORDER));
            default -> setItemAndReturn(keyEvent);
        };
    }

    private @Nullable Item processKeyWithVector(@NotNull Vector vector) {
        var location = heroLogic.getLocation();
        var res = mapLogic.processHeroLocation(location, vector);
        heroLogic.setLocation(res.location());
        heroLogic.processItem(res.item());
        return res.item();
    }

    private @Nullable Item setItemAndReturn(@NotNull KeyEvent event) {
        heroLogic.setItem(eventToInd.get(event));
        return null;
    }
}
