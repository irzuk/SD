package org.Roguelike.model.enemies;

import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

/*
 * Декоратор для сконфуженного врага. Должен двигаться в произвольных направлениях параллельных осям координат.
 */
public class ConfusedEnemy extends Enemy {

    public ConfusedEnemy(Enemy entryEnemy) {
        super(entryEnemy.experience, entryEnemy.health, entryEnemy.enemyLocation, entryEnemy.strategy);
    }

    /*
     * Arguments:
     *  heroLocation - положение героя на карте.
     * Return:
     *  Vector - направление в котором будет двигаться враг.
     * Description:
     *  Движется в произвольном направлении.
     */
    @Override
    public @NotNull Vector findDirection(MapElement heroLocation) {
        var side = ThreadLocalRandom.current().nextInt(0, 4);
        if (side == 0) return new Vector(0, 1);
        if (side == 1) return new Vector(0, -1);
        if (side == 2) return new Vector(1, 0);
        return new Vector(-1, 0);
    }

}
