package org.Roguelike.collections.map.elements;

import org.Roguelike.collections.geometry.Line;
import org.Roguelike.collections.geometry.Point;
import org.jetbrains.annotations.NotNull;

import static org.Roguelike.collections.geometry.Point.*;

public class MapElement {
    protected Point leftBot;
    protected Point leftTop;
    protected Point rightTop;
    protected Point rightBot;

    protected MapElement() {}

    public MapElement(@NotNull Point leftBot, @NotNull Point leftTop, @NotNull Point rightTop, @NotNull Point rightBot) {
        this.leftBot = leftBot;
        this.leftTop = leftTop;
        this.rightTop = rightTop;
        this.rightBot = rightBot;
    }

    public static MapElement squareFromPoint(Point leftBot, int width, int height) {
        return new MapElement(leftBot, byY(leftBot, height), byXY(leftBot, width, height), byX(leftBot, width));
    }

    public @NotNull Point leftBot() {
        return leftBot;
    }

    public @NotNull Point leftTop() {
        return leftTop;
    }

    public @NotNull Point rightTop() {
        return rightTop;
    }

    public @NotNull Point rightBot() {
        return rightBot;
    }

    @Override
    public @NotNull String toString() {
        return String.format("[%s, %s]", new Line(leftBot, leftTop), new Line(rightBot, rightTop));
    }
}
