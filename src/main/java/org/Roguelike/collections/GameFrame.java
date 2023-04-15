package org.Roguelike.collections;

import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.inventory.Inventory;
import org.Roguelike.collections.items.Item;
import org.Roguelike.collections.map.Map;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;

public class GameFrame {
    protected final @NotNull CharacteristicsInfo info;
    protected final @NotNull Map map;
    protected final @NotNull MapElement heroLocation;
    protected final @NotNull Inventory inventory;
    protected final @NotNull Item receivedItem;

    private GameFrame(@NotNull CharacteristicsInfo info,
                      @NotNull Map map,
                      @NotNull MapElement heroLocation,
                      @NotNull Inventory inventory,
                      @NotNull Item receivedItem) {
        this.info = info;
        this.map = map;
        this.heroLocation = heroLocation;
        this.inventory = inventory;
        this.receivedItem = receivedItem;
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

    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public @NotNull Item getReceivedItem() {
        return receivedItem;
    }

    public static class GameFrameBuilder {
        protected CharacteristicsInfo info;
        protected Map map;
        protected MapElement heroLocation;
        protected Inventory inventory;
        protected Item receivedItem;
        private GameFrameBuilder() {}
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
        public GameFrameBuilder setMap(@NotNull Inventory inventory) {
            this.inventory = inventory;
            return this;
        }
        public GameFrameBuilder setReceivedItem(@NotNull Item receivedItem) {
            this.receivedItem = receivedItem;
            return this;
        }

        public GameFrame build() {
            return new GameFrame(info, map, heroLocation, inventory, receivedItem);
        }
    }
}
