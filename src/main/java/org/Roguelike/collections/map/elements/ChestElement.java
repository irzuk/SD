package org.Roguelike.collections.map.elements;

import org.Roguelike.collections.geometry.Point;
import org.jetbrains.annotations.NotNull;

import static org.Roguelike.collections.map.MapElementsParameters.CHEST_HEIGHT;
import static org.Roguelike.collections.map.MapElementsParameters.CHEST_WIDTH;

public class ChestElement extends MapElement {
    public static ChestElement chestFromPoint(@NotNull Point leftBot) {
        return (ChestElement) squareFromPoint(leftBot, CHEST_WIDTH, CHEST_HEIGHT);
    }
}
