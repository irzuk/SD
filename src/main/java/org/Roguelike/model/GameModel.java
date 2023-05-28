package org.Roguelike.model;

import org.Roguelike.UI.Drawer;
import org.Roguelike.collections.GameFrame;
import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.items.Item;
import org.Roguelike.collections.items.Thing;
import org.Roguelike.model.enemies.EnemiesLogic;
import org.Roguelike.model.enemies.SimpleEnemiesLogic;
import org.Roguelike.model.hero.HeroLogic;
import org.Roguelike.model.hero.SimpleHeroLogic;
import org.Roguelike.model.map.MapLogic;
import org.Roguelike.model.map.RoomLogic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;


public class GameModel implements Runnable {
    private interface Task {
        @Nullable Item process();
    }

    public final class MoveTask implements Task {
        @NotNull
        private final Vector vector;

        public MoveTask(@NotNull Vector vector) {
            this.vector = vector;
        }

        @Override
        public @Nullable Item process() {
            var location = heroLogic.getLocation();
            var res = mapLogic.processHeroLocation(location, vector);
            heroLogic.setLocation(res.location());
            heroLogic.processItem(res.item());
            return res.item();
        }
    }

    public final class SetItemTask implements Task {
        private final int ind;

        public SetItemTask(int ind) {
            this.ind = ind;
        }

        @Override
        public @Nullable Item process() {
            heroLogic.setItem(ind);
            return null;
        }
    }

    private final @NotNull Drawer drawer;
    private final @NotNull MapLogic mapLogic = new RoomLogic();
    private final @NotNull HeroLogic heroLogic = new SimpleHeroLogic();
    private final @NotNull EnemiesLogic enemiesLogic = new SimpleEnemiesLogic(mapLogic.getMap());
    private final @NotNull AtomicBoolean needStop = new AtomicBoolean(false);
    private final @NotNull Queue<@NotNull Task> queueKeyEvents = new ConcurrentLinkedQueue<>();

    public GameModel(@NotNull Drawer drawer) {
        this.drawer = drawer;
    }

    @Override
    public void run() {
        while (!needStop.get()) {
            // process Hero
            Item recievedItem = null;
            var task = queueKeyEvents.poll();
            if (task != null) {
                recievedItem = task.process();
            }
            boolean stop = needStop.get() || heroLogic.decreaseCharacteristics();
            boolean isMapChanged = mapLogic.pollMapChanged();

            // process Enemies
            if (isMapChanged) {
                enemiesLogic.processMap(mapLogic.getMap());
            }
            var enemiesDirections = enemiesLogic.calculateDirections(heroLogic.getLocation());
            enemiesDirections = mapLogic.processEnemiesDirections(enemiesLogic.getEnemies(), enemiesDirections);
            int experience = enemiesLogic.processMovement(enemiesDirections,
                    heroLogic.getLocation(), heroLogic.getCharacteristics());
            heroLogic.addExperience(experience);

            // generate Frame
            var frame = new GameFrame.GameFrameBuilder()
                    .setMap(mapLogic.getMap())
                    .setThings(heroLogic.getAvailableItems().stream().map(item -> (Thing) item).toList())
                    .setHeroLocation(heroLogic.getLocation())
                    .setCharacteristicsInfo(heroLogic.getCharacteristics())
                    .setReceivedItem(recievedItem)
                    .setEnemiesLocations(enemiesLogic.getEnemies())
                    .setStop(stop)
                    .setMapChanged(isMapChanged).build();
            draw(frame);
        }
        var frame = new GameFrame.GameFrameBuilder().setStop(true).build();
        draw(frame);
    }

    public void addTask(@NotNull Task task) {
        queueKeyEvents.add(task);
    }

    public void stop() {
        needStop.set(true);
    }

    private void draw(GameFrame frame) {
        try {
            drawer.drawFrame(frame);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
