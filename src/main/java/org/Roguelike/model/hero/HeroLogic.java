package org.Roguelike.model.hero;

import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.items.Item;
import org.Roguelike.collections.map.elements.HeroElement;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface HeroLogic {
    void processItem(@Nullable Item item);
    boolean setItem(@NotNull Item item);
    boolean decreaseCharacteristics();
    @NotNull List<? extends Item> getAvailableItems();
    @NotNull MapElement getLocation();
    @NotNull CharacteristicsInfo getCharacteristics();
    void setLocation(@NotNull MapElement location);
}
