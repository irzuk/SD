package org.Roguelike.collections;

import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.items.Item;
import org.Roguelike.collections.items.Thing;
import org.Roguelike.collections.map.Map;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GameFrame {
    private final @NotNull CharacteristicsInfo info;
    private final @NotNull Map map;
    private final @NotNull MapElement heroLocation;
    private final @NotNull List<Thing> items;
    private final @Nullable Item receivedItem;
    private final boolean stop;
    private final boolean mapChanged;

    private GameFrame(@NotNull CharacteristicsInfo info,
                      @NotNull Map map,
                      @NotNull MapElement heroLocation,
                      @NotNull List<Thing> items,
                      @Nullable Item receivedItem,
                      boolean stop,
                      boolean mapChanged) {
        this.info = info;
        this.map = map;
        this.heroLocation = heroLocation;
        this.items = items;
        this.receivedItem = receivedItem;
        this.stop = stop;
        this.mapChanged = mapChanged;
    }

    public GameFrame(GameFrame gameFrame) {
        this(gameFrame.info, gameFrame.map, gameFrame.heroLocation, gameFrame.items, gameFrame.receivedItem, gameFrame.stop, gameFrame.mapChanged);
    }

    public @NotNull CharacteristicsInfo getInfo() {
        return info;
    }

    public @NotNull Map getMap() {
        return map;
    }

    public @NotNull MapElement getHeroLocation() {
        return heroLocation;
    }

    public @NotNull List<Thing> getItems() {
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

    public static class GameFrameBuilder {
        protected CharacteristicsInfo info;
        protected Map map;
        protected MapElement heroLocation;
        protected List<@NotNull Thing> items;
        protected Item receivedItem;
        protected boolean stop = false;
        protected boolean mapChanged = false;
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

        public GameFrame build() {
            return new GameFrame(info, map, heroLocation, items, receivedItem, stop, mapChanged);
        }
    }
}
