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
    protected final @NotNull CharacteristicsInfo info;
    protected final @NotNull Map map;
    protected final @NotNull MapElement heroLocation;
    protected final @NotNull List<Thing> items;
    protected final @Nullable Item receivedItem;
    protected final boolean stop;

    private GameFrame(@NotNull CharacteristicsInfo info,
                      @NotNull Map map,
                      @NotNull MapElement heroLocation,
                      @NotNull List<Thing> items,
                      @Nullable Item receivedItem, boolean stop) {
        this.info = info;
        this.map = map;
        this.heroLocation = heroLocation;
        this.items = items;
        this.receivedItem = receivedItem;
        this.stop = stop;
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

    public static class GameFrameBuilder {
        protected CharacteristicsInfo info;
        protected Map map;
        protected MapElement heroLocation;
        protected List<@NotNull Thing> items;
        protected Item receivedItem;
        protected boolean stop;
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

        public GameFrame build() {
            return new GameFrame(info, map, heroLocation, items, receivedItem, stop);
        }
    }
}
