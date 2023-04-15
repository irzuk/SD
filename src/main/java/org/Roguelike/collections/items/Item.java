package org.Roguelike.collections.items;

public interface Item {
    ItemType getType();
    int getCheerfullness();
    int getSatiety();
    String getDescription();
    double getProbability();
}
