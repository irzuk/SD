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

import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.Roguelike.collections.map.MapElementsParameters.CELL_BORDER;

public class GameModel implements Runnable {
    private final @NotNull Drawer drawer = new Drawer();
    private final @NotNull Listener listener = new Listener(this);
    private final @NotNull MapLogic mapLogic = new RoomLogic();
    private final @NotNull HeroLogic heroLogic = new SimpleHeroLogic();
    private final @NotNull AtomicBoolean needStop = new AtomicBoolean(false);
    private final @NotNull Lock lock = new ReentrantLock();
    private final @NotNull LinkedList<@NotNull KeyEvent> queueKeyEvents = new LinkedList<>();
    private final Date date = new Date();

    public GameModel() {
        drawer.addKeyListener(listener);
    }

    @Override
    public void run() {
        System.err.println("Started");
        long lastDecreasing = date.getTime();
        long lastDrawing = date.getTime();
        while (!needStop.get()) {
            System.err.println("New iteration");
            Item recievedItem = null;
            lock.lock();
            var keyEvent = queueKeyEvents.poll();
            lock.unlock();
            if (keyEvent != null) {
                recievedItem = processKeyEvent(keyEvent);
            }
            boolean stop = false;
            long currentTime = date.getTime();
            if (currentTime - lastDecreasing >= 1000) {
                stop = !heroLogic.decreaseCharacteristics();
                lastDecreasing = currentTime;
            }
            System.err.println("Loop");
//            while (date.getTime() - lastDrawing < 30) { // TODO: WTF, Why we got warning?
//            }
            System.err.println("Form game frame");
            var gameFrame = new GameFrame.GameFrameBuilder()
                .setMap(mapLogic.getMap())
                .setThings(heroLogic.getAvailableItems().stream().map(item -> (Thing) item).toList())
                .setHeroLocation(heroLogic.getLocation())
                .setCharacteristicsInfo(heroLogic.getCharacteristics())
                .setReceivedItem(recievedItem)
                .setStop(stop).build();
            drawer.drawFrame(gameFrame);
            lastDrawing = date.getTime();
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
            case GO_UP -> processKeyWithVector(Vector.vectorUp(CELL_BORDER));
            case GO_DOWN -> processKeyWithVector(Vector.vectorDown(CELL_BORDER));
            case GO_LEFT -> processKeyWithVector(Vector.vectorLeft(CELL_BORDER));
            case GO_RIGHT -> processKeyWithVector(Vector.vectorRight(CELL_BORDER));
            default -> setItemAndReturn(keyEvent.getThing());
        };
    }

    private @Nullable Item processKeyWithVector(@NotNull Vector vector) {
        var location = heroLogic.getLocation();
        var res = mapLogic.processHeroLocation(location, vector);
        heroLogic.setLocation(res.location());
        heroLogic.processItem(res.item());
        return res.item();
    }

    private @Nullable Item setItemAndReturn(@NotNull Thing thing) {
        heroLogic.setItem(thing);
        return null;
    }
}
