package org.Roguelike.collections.map;

import org.Roguelike.collections.items.Item;
import org.Roguelike.collections.map.elements.HeroElement;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record MapLogicResult(@NotNull MapElement location, @Nullable Item item) {
}
