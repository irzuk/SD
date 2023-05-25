package org.Roguelike.collections.map.elements;

import org.Roguelike.collections.geometry.Point;
import org.jetbrains.annotations.NotNull;

import static org.Roguelike.collections.geometry.Point.*;
import static org.Roguelike.collections.map.MapElementsParameters.DOOR_HORIZONTAL_HEIGHT;
import static org.Roguelike.collections.map.MapElementsParameters.DOOR_HORIZONTAL_WIDTH;
import static org.Roguelike.collections.map.MapElementsParameters.DOOR_VERTICAL_HEIGHT;
import static org.Roguelike.collections.map.MapElementsParameters.DOOR_VERTICAL_WIDTH;

public class DoorElement extends MapElement {
    public static MapElement horizontalDoorFromPoint(@NotNull Point leftBot) {
        return doorFromPoint(leftBot, DOOR_HORIZONTAL_WIDTH, DOOR_HORIZONTAL_HEIGHT);
    }

    public static MapElement verticalDoorFromPoint(@NotNull Point leftBot) {
        return doorFromPoint(leftBot, DOOR_VERTICAL_WIDTH, DOOR_VERTICAL_HEIGHT);
    }

    public boolean isHorizontal() {
        return rightTop.x() - leftBot.x() > rightTop.y() - leftBot.y();
    }


    private static MapElement doorFromPoint(Point leftBot, int width, int height) {
        return new MapElement(leftBot, byY(leftBot, height), byXY(leftBot, width, height), byX(leftBot, width));
    }
}
