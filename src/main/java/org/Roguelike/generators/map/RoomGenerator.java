package org.Roguelike.generators.map;

import org.Roguelike.collections.geometry.Line;
import org.Roguelike.collections.geometry.Point;
import org.Roguelike.collections.map.Map;
import org.Roguelike.collections.map.MapElementsParameters;
import org.Roguelike.collections.map.elements.ChestElement;
import org.Roguelike.collections.map.elements.DoorElement;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class RoomGenerator implements MapGenerator {
    @Override
    public @NotNull Map generateMap(SideWithDoor sideWithDoor) {
        // Assume (0, 0) -- is left bottom corner.

        // Room
        Point[] roomPoints = new Point[]{
                new Point(0, 0),
                new Point(MapElementsParameters.MAP_WIDTH, 0),
                new Point(MapElementsParameters.MAP_WIDTH, MapElementsParameters.MAP_HEIGHT),
                new Point(0, MapElementsParameters.MAP_HEIGHT)
        };

        List<Line> roomLines = new ArrayList<>();
        roomLines.add(new Line(roomPoints[0], roomPoints[1]));
        roomLines.add(new Line(roomPoints[1], roomPoints[2]));
        roomLines.add(new Line(roomPoints[2], roomPoints[3]));
        roomLines.add(new Line(roomPoints[3], roomPoints[0]));

        // Chests

        int prob_perc = 1;
        LinkedList<MapElement> chests = new LinkedList<>();
        for (int row = MapElementsParameters.CHEST_HEIGHT;
             row + MapElementsParameters.CHEST_HEIGHT < MapElementsParameters.MAP_HEIGHT;
             row += MapElementsParameters.CHEST_HEIGHT) {
            for (int col = MapElementsParameters.CHEST_WIDTH;
                 col + MapElementsParameters.CHEST_WIDTH < MapElementsParameters.MAP_WIDTH;
                 col += MapElementsParameters.CHEST_WIDTH) {
                var tmp = ThreadLocalRandom.current().nextInt(0, 100);
                if (tmp < prob_perc) {
                    chests.add(ChestElement.fromPoint(new Point(row, col)));
                }
            }
        }

        // Door
        MapElement door;
        switch (sideWithDoor) {

            case LEFT -> {
                var tmp = ThreadLocalRandom.current().nextInt(MapElementsParameters.DOOR_VERTICAL_HEIGHT,
                        MapElementsParameters.MAP_HEIGHT - MapElementsParameters.DOOR_VERTICAL_HEIGHT + 1);
                door = DoorElement.verticalDoorFromPoint(new Point(0, tmp));
            }
            case RIGHT -> {
                var tmp = ThreadLocalRandom.current().nextInt(MapElementsParameters.DOOR_VERTICAL_HEIGHT,
                        MapElementsParameters.MAP_HEIGHT - MapElementsParameters.DOOR_VERTICAL_HEIGHT + 1);
                door = DoorElement.verticalDoorFromPoint(new Point(MapElementsParameters.MAP_WIDTH, tmp));
            }
            case TOP -> {
                var tmp = ThreadLocalRandom.current().nextInt(MapElementsParameters.DOOR_HORIZONTAL_WIDTH,
                        MapElementsParameters.MAP_WIDTH - MapElementsParameters.DOOR_HORIZONTAL_WIDTH + 1);
                door = DoorElement.verticalDoorFromPoint(new Point(tmp, MapElementsParameters.MAP_HEIGHT));
            }
            case BOT -> {
                var tmp = ThreadLocalRandom.current().nextInt(MapElementsParameters.DOOR_HORIZONTAL_WIDTH,
                        MapElementsParameters.MAP_WIDTH - MapElementsParameters.DOOR_HORIZONTAL_WIDTH + 1);
                door = DoorElement.verticalDoorFromPoint(new Point(tmp, 0));
            }
            default -> throw new IllegalStateException("Unexpected value: " + sideWithDoor);
        }

        return new Map(roomLines, chests, new ArrayList<>(List.of(door)));
    }
}
