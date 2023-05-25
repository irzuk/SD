package org.Roguelike.collections.inventory;

import org.Roguelike.collections.items.Item;
import org.Roguelike.collections.items.Thing;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.Roguelike.collections.items.ItemType.THING;

public class SimpleInventory implements Inventory {
    private final @NotNull Set<@NotNull Thing> availableThings = new LinkedHashSet<>();

    @Override
    public boolean add(@NotNull Item item) {
        assert item.getType() == THING;
        return availableThings.add((Thing) item);
    }

    @Override
    public Thing pollByInd(int ind) {
        int counter = 0;
        Thing res = null;
        for (var thing : availableThings) {
            if (counter == ind) {
                res = thing;
                break;
            }
            counter++;
        }
        if (res != null) {
            availableThings.remove(res);
        }
        return res;
    }

    @Override
    public @NotNull List<? extends Item> getAvailableItems() {
        return new ArrayList<>(availableThings);
    }
}
