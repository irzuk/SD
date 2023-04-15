package org.Roguelike.collections.inventory;

import org.Roguelike.collections.items.Item;
import org.Roguelike.collections.items.Thing;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static org.Roguelike.collections.items.ItemType.THING;

public class SimpleInventory implements Inventory {
    private final @NotNull Set<@NotNull Thing> availableThings = new LinkedHashSet<>();

    @Override
    public boolean add(@NotNull Item item) {
        assert item.getType() == THING;
        return availableThings.add((Thing) item);
    }

    @Override
    public boolean contains(@NotNull Item item) {
        assert item.getType() == THING;
        return availableThings.contains((Thing) item);
    }

    @Override
    public boolean remove(@NotNull Item item) {
        assert item.getType() == THING;
        return availableThings.remove((Thing) item);
    }

    @Override
    public @NotNull List<@NotNull Item> getAvailableItems() {
        return new ArrayList<>(availableThings);
    }
}
