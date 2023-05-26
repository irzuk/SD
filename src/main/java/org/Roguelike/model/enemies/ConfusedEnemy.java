package org.Roguelike.model.enemies;

import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;

/*
 * TODO: Миша
 * Декоратор для сконфуженного врага. Должен двигаться в произвольных направлениях параллельных осям координат.
 */
public class ConfusedEnemy extends Enemy {
    private final Enemy entryEnemy;

    public ConfusedEnemy(Enemy entryEnemy) {
        this.entryEnemy = entryEnemy;
    }

    /*
     * Arguments:
     *  heroLocation - положение героя на карте.
     * Return:
     *  Vector - направление в котором будет двигаться враг.
     * Description:
     *  Движется в произвольном направлении.
     */
    public @NotNull Vector findDirection(MapElement heroLocation) {
        return null;
    }
}
