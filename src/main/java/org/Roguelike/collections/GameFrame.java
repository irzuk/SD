package org.Roguelike.collections;

import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.items.Item;
import org.Roguelike.collections.items.Thing;
import org.Roguelike.collections.map.Map;
import org.Roguelike.collections.map.elements.MapElement;
import org.Roguelike.collections.enemies.Enemy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GameFrame {
    private final CharacteristicsInfo info;
    private final Map map;
    private final MapElement heroLocation;
    private final List<@NotNull Thing> items;
    private final List<@NotNull Enemy> enemies;
    private final @Nullable Item receivedItem;
    private final boolean stop;
    private final boolean mapChanged;

    private GameFrame(CharacteristicsInfo info,
                      Map map,
                      MapElement heroLocation,
                      List<@NotNull Thing> items,
                      List<@NotNull Enemy> enemies,
                      @Nullable Item receivedItem,
                      boolean stop,
                      boolean mapChanged) {
        this.info = info;
        this.map = map;
        this.heroLocation = heroLocation;
        this.items = items;
        this.enemies = enemies;
        this.receivedItem = receivedItem;
        this.stop = stop;
        this.mapChanged = mapChanged;
    }

    public GameFrame(GameFrame gameFrame) {
        this(gameFrame.info, gameFrame.map, gameFrame.heroLocation, gameFrame.items,
                gameFrame.enemies, gameFrame.receivedItem, gameFrame.stop, gameFrame.mapChanged);
    }

    public @Nullable CharacteristicsInfo getInfo() {
        return info;
    }

    public @Nullable Map getMap() {
        return map;
    }

    public @Nullable MapElement getHeroLocation() {
        return heroLocation;
    }

    public @Nullable List<Thing> getItems() {
        return items;
    }

    public @Nullable Item getReceivedItem() {
        return receivedItem;
    }

    public boolean isStop() {
        return stop;
    }

    public boolean isMapChanged() {
        return mapChanged;
    }

    public @Nullable List<@NotNull Enemy> getEnemies() {
        return enemies;
    }

    public static class GameFrameBuilder {
        private CharacteristicsInfo info;
        private Map map;
        private MapElement heroLocation;
        private List<@NotNull Thing> items;
        private List<@NotNull Enemy> enemies;
        private Item receivedItem;
        private boolean stop = false;
        private boolean mapChanged = false;

        public GameFrameBuilder setCharacteristicsInfo(@NotNull CharacteristicsInfo info) {
            this.info = info;
            return this;
        }

        public GameFrameBuilder setMap(@NotNull Map map) {
            this.map = map;
            return this;
        }

        public GameFrameBuilder setHeroLocation(@NotNull MapElement heroLocation) {
            this.heroLocation = heroLocation;
            return this;
        }

        public GameFrameBuilder setThings(@NotNull List<Thing> items) {
            this.items = items;
            return this;
        }

        public GameFrameBuilder setReceivedItem(@Nullable Item receivedItem) {
            this.receivedItem = receivedItem;
            return this;
        }

        public GameFrameBuilder setStop(boolean stop) {
            this.stop = stop;
            return this;
        }

        public GameFrameBuilder setMapChanged(boolean mapChanged) {
            this.mapChanged = mapChanged;
            return this;
        }

        public GameFrameBuilder setEnemiesLocations(@NotNull List<@NotNull Enemy> enemies) {
            this.enemies = enemies;
            return this;
        }

        public GameFrame build() {
            return new GameFrame(info, map, heroLocation, items, enemies, receivedItem, stop, mapChanged);
        }
    }
}
