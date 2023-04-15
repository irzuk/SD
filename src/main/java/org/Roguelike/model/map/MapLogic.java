package org.Roguelike.model.map;

import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.map.Map;
import org.Roguelike.collections.map.MapLogicResult;
import org.Roguelike.collections.map.elements.HeroElement;
import org.jetbrains.annotations.NotNull;

public interface MapLogic {
    @NotNull MapLogicResult processHeroLocation(@NotNull HeroElement location, @NotNull Vector direction);
    @NotNull Map getMap();
}
