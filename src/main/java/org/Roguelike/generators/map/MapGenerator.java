package org.Roguelike.generators.map;

import org.Roguelike.collections.map.Map;
import org.jetbrains.annotations.NotNull;

public interface MapGenerator {
    @NotNull Map generateMap(SideWithDoor sideWithDoor);
}
