package org.Roguelike.collections.map.elements;

import org.Roguelike.collections.geometry.Point;
import org.jetbrains.annotations.NotNull;

import static org.Roguelike.collections.map.MapElementsParameters.DOOR_HORIZONTAL_HEIGHT;
import static org.Roguelike.collections.map.MapElementsParameters.DOOR_HORIZONTAL_WIDTH;
import static org.Roguelike.collections.map.MapElementsParameters.DOOR_VERTICAL_HEIGHT;
import static org.Roguelike.collections.map.MapElementsParameters.DOOR_VERTICAL_WIDTH;

public class DoorElement extends MapElement {
    public static DoorElement horizontalDoorFromPoint(@NotNull Point leftBot) {
        return (DoorElement) squareFromPoint(leftBot, DOOR_HORIZONTAL_WIDTH, DOOR_HORIZONTAL_HEIGHT);
    }

    public static DoorElement verticalDoorFromPoint(@NotNull Point leftBot) {
        return (DoorElement) squareFromPoint(leftBot, DOOR_VERTICAL_WIDTH, DOOR_VERTICAL_HEIGHT);
    }

    public boolean isHorizontal() {
        return rightTop.x() - leftBot.x() > rightTop.y() - leftBot.y();
    }
}
