package org.Roguelike.collections.map;

import org.Roguelike.collections.map.elements.HeroElement;
import org.jetbrains.annotations.NotNull;

public record MapLogicResult(@NotNull HeroElement position, int openChests) {
}
