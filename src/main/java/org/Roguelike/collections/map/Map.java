package org.Roguelike.collections.map;

import org.Roguelike.collections.geometry.Line;
import org.Roguelike.collections.map.elements.ChestElement;
import org.Roguelike.collections.map.elements.DoorElement;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public record Map(@NotNull List<Line> roomLines,
                  @NotNull LinkedList<MapElement> chests,
                  @NotNull List<MapElement> doors) {
}
