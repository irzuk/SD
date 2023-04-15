package org.Roguelike.model.hero;

import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.Point;
import org.Roguelike.collections.items.Item;

import java.util.List;

public interface HeroLogic {
    void addItem(Item item);
    boolean setItem(Item item);
    boolean decreaseCharacteristics();
    List<Item> getAvailableItems();
    Point getPosition();
    CharacteristicsInfo getCharacteristics();
    void setPosition(Point point);
}
