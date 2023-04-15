package org.Roguelike.collections;

import java.util.List;

public class Map {
    public final List<Pair<Point, Point>> roomLines;
    public final List<Point> chests;
    public final List<Point> doors;

    public Map(List<Pair<Point, Point>> roomLines, List<Point> chests, List<Point> doors) {
        this.roomLines = roomLines;
        this.chests = chests;
        this.doors = doors;
    }
}
