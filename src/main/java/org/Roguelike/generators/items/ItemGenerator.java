package org.Roguelike.generators.items;

import org.Roguelike.collections.items.Item;
import org.jetbrains.annotations.NotNull;

public interface ItemGenerator {
    @NotNull Item generateItem();
}
