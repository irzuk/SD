package org.Roguelike.collections.inventory;

import org.Roguelike.collections.items.Item;
import org.Roguelike.collections.items.Thing;

import java.util.*;

import static org.Roguelike.collections.items.ItemType.THING;

public class SimpleInventory implements Inventory {
    private final Set<Thing> availableThings = new LinkedHashSet<>();

    private void checkIsThing(Item item) {
        if (item.getType() != THING) {
            throw new RuntimeException("Incorrect Item in inventory: need THING, got " + item.getType().name());
        }
    }

    @Override
    public boolean add(Item item) {
        checkIsThing(item);
        return availableThings.add((Thing) item);
    }

    @Override
    public boolean contains(Item item) {
        checkIsThing(item);
        return availableThings.contains((Thing) item);
    }

    @Override
    public boolean remove(Item item) {
        checkIsThing(item);
        return availableThings.remove((Thing) item);
    }

    @Override
    public List<Item> getAvailableItems() {
        return new ArrayList<>(availableThings);
    }
}
