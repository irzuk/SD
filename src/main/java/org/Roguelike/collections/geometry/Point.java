package org.Roguelike.collections.geometry;

import org.jetbrains.annotations.NotNull;

public record Point(int x, int y) {
    public static @NotNull Point byX(@NotNull Point p, int val) {
        return new Point(p.x + val, p.y);
    }

    public static @NotNull Point byY(@NotNull Point p, int val) {
        return new Point(p.x, p.y + val);
    }

    public static @NotNull Point byXY(@NotNull Point p, int valX, int valY) {
        return new Point(p.x + valX, p.y + valY);
    }

    @Override
    public @NotNull String toString() {
        return String.format("(%d, %d)", x, y);
    }
}