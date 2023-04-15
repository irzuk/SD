package org.Roguelike.collections;

import org.Roguelike.collections.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class GameFrame {
    protected final CharacteristicsInfo info;
    protected final Map map;
    protected final Point heroPosition;
    protected final Inventory inventory;

    public GameFrame(CharacteristicsInfo info, Map map, Point heroPosition, Inventory inventory) {
        this.info = info;
        this.map = map;
        this.heroPosition = heroPosition;
        this.inventory = inventory;
    }

    public @NotNull CharacteristicsInfo getInfo() {
        return info;
    }

    public @NotNull Map getMap() {
        return map;
    }

    public @NotNull Point getHeroPosition() {
        return heroPosition;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
