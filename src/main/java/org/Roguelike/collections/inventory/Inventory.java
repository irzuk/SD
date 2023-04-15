package org.Roguelike.collections.inventory;

import org.Roguelike.collections.items.Item;

public interface Inventory {
    boolean add(Item item);
    boolean contains(Item item);
    boolean remove(Item item);
}
