package org.Roguelike.collections.geometry;

import org.jetbrains.annotations.NotNull;

public record Point(int x, int y) {
   // private java.awt.Point = new java.awt.Point();
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
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Point)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        var c = (Point) o;

        // Compare the data members and return accordingly
        return c.x == x && c.y == y;
    }

    @Override
    public @NotNull String toString() {
        return String.format("(%d, %d)", x, y);
    }
}