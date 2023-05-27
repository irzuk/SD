package org.Roguelike.collections.enemies;

import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.map.MapElementsParameters;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

/*
 * Декоратор для сконфуженного врага. Должен двигаться в произвольных направлениях параллельных осям координат.
 */
public class ConfusedEnemy extends Enemy {
    @NotNull
    private final Enemy entryEnemy;

    public ConfusedEnemy(@NotNull Enemy entryEnemy) {
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
    @Override
    public @NotNull Vector findDirection(@NotNull MapElement heroLocation) {
        var side = ThreadLocalRandom.current().nextInt(0, 4);

        if (side == 0) return new Vector(0, MapElementsParameters.CELL_BORDER);
        if (side == 1) return new Vector(0, -MapElementsParameters.CELL_BORDER);
        if (side == 2) return new Vector(MapElementsParameters.CELL_BORDER, 0);
        return new Vector(-MapElementsParameters.CELL_BORDER, 0);
    }

    @Override
    public boolean isDead() {
        return entryEnemy.isDead();
    }

    @Override
    public @NotNull MapElement getEnemyLocation() {
        return entryEnemy.getEnemyLocation();
    }

    @Override
    public void move(Vector direction) {
        entryEnemy.move(direction);
    }

    @Override
    public boolean fight(CharacteristicsInfo heroCharacteristics) {
        return entryEnemy.fight(heroCharacteristics);
    }

    @Override
    public int getExperience() {
        return entryEnemy.getExperience();
    }
}
