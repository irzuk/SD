package org.Roguelike.collections.map.elements;

import org.Roguelike.collections.geometry.Point;
import org.jetbrains.annotations.NotNull;

import static org.Roguelike.collections.map.MapElementsParameters.HERO_HEIGHT;
import static org.Roguelike.collections.map.MapElementsParameters.HERO_WIDTH;

public class HeroElement extends MapElement {
    public static HeroElement heroFromPoint(@NotNull Point point) {
        return (HeroElement) squareFromPoint(point, HERO_WIDTH, HERO_HEIGHT);
    }
}
