package org.Roguelike.model.enemies;

import org.Roguelike.collections.characteristics.Characteristic;
import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;

/*
 * Класс инкапсулирует в себе часть логики врагов и их состояние.
 * Вызывает различные стратегии, которые должен получить извне.
 */
public class Enemy {
    private final int experience;
    private final Characteristic health;
    private final MapElement enemyLocation;
    private final BehaviorStrategy strategy;

    public Enemy() {
        this.experience = 0;
        this.health = null;
        this.enemyLocation = null;
        this.strategy = null;
    }


    public Enemy(int experience, @NotNull Characteristic health, @NotNull MapElement enemyLocation, @NotNull BehaviorStrategy strategy) {
        this.experience = experience;
        this.health = health;
        this.enemyLocation = enemyLocation;
        this.strategy = strategy;
    }

    /*
     * Return:
     *  true, если враг мертв, т.е. current == 0
     *  false, иначе
     */
    public boolean isDead() {
        return false;
    }

    public @NotNull MapElement getEnemyLocation() {
        return enemyLocation;
    }

    /*
     * Arguments:
     *  heroLocation - положение героя на карте.
     * Return:
     *  Vector - направление в котором будет двигаться враг.
     */
    public @NotNull Vector findDirection(MapElement heroLocation) {
        return null;
    }

    /*
     * Arguments:
     *  direction - направление в котором нужно передвинуть положение врага.
     */
    public void move(Vector direction) {

    }

    /*
     * Arguments:
     *  CharacteristicsInfo - характеристики героя, которые должны измениться.
     * Return:
     *  boolean - был ли враг сконфужен.
     */
    public boolean fight(CharacteristicsInfo heroCharacteristics) {
        return false;
    }
}
