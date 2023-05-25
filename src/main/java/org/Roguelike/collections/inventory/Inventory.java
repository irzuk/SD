package org.Roguelike.collections.inventory;

import org.Roguelike.collections.items.Item;
import org.Roguelike.collections.items.Thing;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Inventory {
    boolean add(@NotNull Item item);
    Thing pollByInd(int ind);
    @NotNull List<? extends Item> getAvailableItems();
}
