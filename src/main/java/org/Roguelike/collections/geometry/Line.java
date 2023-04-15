package org.Roguelike.collections.geometry;

import org.jetbrains.annotations.NotNull;

public record Line (@NotNull Point first, @NotNull Point second){
    public boolean isHorizontal() {
        return first.y() == second.y();
    }

    public boolean isVertical() {
        return first.x() == second.x();
    }

    @Override
    public @NotNull String toString() {
        return String.format("{%s, %s}", first, second);
    }
}