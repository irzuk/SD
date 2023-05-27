package org.Roguelike.collections.enemies;

import org.Roguelike.collections.characteristics.Characteristic;
import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.map.elements.MapElement;
import org.Roguelike.model.enemies.stratagies.BehaviorStrategy;
import org.jetbrains.annotations.NotNull;

/*
 * Класс инкапсулирует в себе часть логики врагов и их состояние.
 * Вызывает различные стратегии, которые должен получить извне.
 */
public class Enemy {
    protected final int experience;
    protected final Characteristic health;
    protected MapElement enemyLocation;
    protected final BehaviorStrategy strategy;

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
        assert health != null;
        return health.current < 0;
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
    public @NotNull Vector findDirection(@NotNull MapElement heroLocation) {
        assert strategy != null;
        return strategy.calculateDirection(heroLocation, enemyLocation);
    }

    /*
     * Arguments:
     *  direction - направление в котором нужно передвинуть положение врага.
     */
    public void move(Vector direction) {
        enemyLocation = enemyLocation.move(direction);
    }

    /*
     * Arguments:
     *  CharacteristicsInfo - характеристики героя, которые должны измениться.
     * Return:
     *  boolean - был ли враг сконфужен.
     */
    public boolean fight(CharacteristicsInfo heroCharacteristics) {
        assert strategy != null;
        var res = strategy.fight(heroCharacteristics);
        if (res >= 0) {
            assert health != null;
            health.current -= res;
            return strategy.tryConfuse();
        }
        return false;
    }

    public int getExperience() {
        return experience;
    }
}
