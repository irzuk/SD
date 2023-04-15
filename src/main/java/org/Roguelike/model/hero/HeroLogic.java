package org.Roguelike.model.hero;

import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.geometry.Point;
import org.Roguelike.collections.items.Item;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface HeroLogic {
    void processItem(@NotNull Item item);
    boolean setItem(@NotNull Item item);
    boolean decreaseCharacteristics();
    @NotNull List<@NotNull Item> getAvailableItems();
    @NotNull MapElement getLocation();
    @NotNull CharacteristicsInfo getCharacteristics();
    void setLocation(@NotNull MapElement location);
}
