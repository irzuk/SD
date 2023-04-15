package org.Roguelike.collections.inventory;

import org.Roguelike.collections.items.Item;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Inventory {
    boolean add(@NotNull Item item);
    boolean contains(@NotNull Item item);
    boolean remove(@NotNull Item item);
    @NotNull List<@NotNull Item> getAvailableItems();
}
