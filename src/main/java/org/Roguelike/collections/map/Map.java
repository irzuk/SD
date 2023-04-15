package org.Roguelike.collections.map;

import org.Roguelike.collections.geometry.Line;
import org.Roguelike.collections.map.elements.ChestElement;
import org.Roguelike.collections.map.elements.DoorElement;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public record Map(@NotNull List<Line> roomLines,
                  @NotNull LinkedList<ChestElement> chests,
                  @NotNull List<DoorElement> doors) {
}
