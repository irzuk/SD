package org.Roguelike.collections.geometry;

import org.Roguelike.collections.map.elements.HeroElement;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;

public class Vector {
    private final int x;
    private final int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vector vectorDown(int val) {
        assert val >= 0;
        return new Vector(0, -val);
    }

    public static Vector vectorUp(int val) {
        assert val >= 0;
        return new Vector(0, val);
    }

    public static Vector vectorLeft(int val) {
        assert val >= 0;
        return new Vector(-val, 0);
    }

    public static Vector vectorRight(int val) {
        assert val >= 0;
        return new Vector(val, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVertical() {
        return x == 0;
    }

    public boolean isHorizontal() {
        return y == 0;
    }

    public boolean goesLeft() {
        return x < 0;
    }

    public boolean goesDown() {
        return y < 0;
    }

    @Override
    public @NotNull String toString() {
        return String.format("(%d, %d)", x, y);
    }

    public @NotNull MapElement moveHero(@NotNull MapElement location) {
        return HeroElement.heroFromPoint(Point.byXY(location.leftBot(), x, y));
    }

    public boolean intersectsLine(@NotNull MapElement location, @NotNull Line line) {
        if ((line.isHorizontal() && isHorizontal()) || (line.isVertical() && isVertical())) {
            return false;
        }
        if (line.isVertical() && isHorizontal()) {
            if (lineX(line) <= left(location)) {
                return left(location, x) < lineX(line);
            } else if (right(location) <= lineX(line)) {
                return lineX(line) < right(location, x);
            }
        } else if (line.isHorizontal() && isVertical()) {
            if (lineY(line) <= bot(location)) {
                return bot(location, y) < lineY(line);
            } else if (top(location) <= lineY(line)) {
                return lineY(line) < top(location, y);
            }
        }
        throw new RuntimeException(String.format("Unexpected request. Vector is %s, Line is %s, Location is %s",
            this, line, location));
    }

    public boolean intersectsElement(@NotNull MapElement location, @NotNull MapElement element) {
        if (intersectsVertically(location, element)) {
            if (right(element) <= left(location)) {
                return left(location, x) < right(element);
            } else if (right(location) <= left(element)){
                return left(element) < right(location, x);
            }
        } else if (intersectsHorizontally(location, element)) {
            if (top(element) <= bot(location)) {
                return bot(location, y) < top(element);
            } else if (top(location) <= bot(element)) {
                return bot(element) < top(location, y);
            }
        }
        return false;
    }

    private static boolean intersectsVertically(@NotNull MapElement fst, @NotNull MapElement snd) {
        return intervalsIntersects(bot(fst), top(fst), bot(snd), top(snd));
    }

    private static boolean intersectsHorizontally(@NotNull MapElement fst, @NotNull MapElement snd) {
        return intervalsIntersects(left(fst), right(fst), left(snd), right(snd));
    }

    private static boolean intervalsIntersects(int a1, int b1, int a2, int b2) {
        return (a1 <= a2 && a2 <= b1)
            || (a1 <= b2 && b2 <= b1)
            || (a2 <= a1 && b1 <= b2);
    }

    private static int left(@NotNull MapElement location) {
        return left(location, 0);
    }

    private static int left(@NotNull MapElement location, int val) {
        return location.leftBot().x() + val;
    }

    private static int right(@NotNull MapElement location) {
        return right(location, 0);
    }

    private static int right(@NotNull MapElement location, int val) {
        return location.rightBot().x() + val;
    }

    private static int top(@NotNull MapElement location) {
        return top(location, 0);
    }

    private static int top(@NotNull MapElement location, int val) {
        return location.leftTop().y() + val;
    }

    private static int bot(@NotNull MapElement location) {
        return bot(location, 0);
    }

    private static int bot(@NotNull MapElement location, int val) {
        return location.leftBot().y() + val;
    }

    private int lineX(@NotNull Line line) {
        assert line.isVertical();
        return line.first().x();
    }

    private int lineY(@NotNull Line line) {
        assert line.isHorizontal();
        return line.first().y();
    }
}
