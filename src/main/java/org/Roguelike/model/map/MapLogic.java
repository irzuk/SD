package org.Roguelike.model.map;

import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.map.Map;
import org.Roguelike.collections.map.MapLogicResult;
import org.Roguelike.collections.map.elements.MapElement;
import org.Roguelike.collections.enemies.Enemy;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface MapLogic {
    @NotNull MapLogicResult processHeroLocation(@NotNull MapElement location, @NotNull Vector direction);
    @NotNull List<@NotNull Vector> processEnemiesDirections(@NotNull List<@NotNull Enemy> enemies,
                                                            @NotNull List<@NotNull Vector> directions);
    @NotNull Map getMap();
    boolean pollMapChanged();
}
