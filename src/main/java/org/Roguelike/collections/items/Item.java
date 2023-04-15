package org.Roguelike.collections.items;

import org.jetbrains.annotations.NotNull;

public interface Item {
    @NotNull ItemType getType();
    int getCheerfullness();
    int getSatiety();
    @NotNull String getDescription();
    double getProbability();
}
