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
    protected BehaviorStrategy strategy;
    protected EnemyState state;

    public Enemy() {
        this.experience = 0;
        this.health = null;
        this.enemyLocation = null;
        this.strategy = null;
        state = null;
    }

    public Enemy(int experience,
                 @NotNull Characteristic health,
                 @NotNull MapElement enemyLocation,
                 @NotNull BehaviorStrategy strategy,
                 @NotNull EnemyState state) {
        this.experience = experience;
        this.health = health;
        this.enemyLocation = enemyLocation;
        this.strategy = strategy;
        this.state = state;
    }

    /*
     * Return:
     *  true, если враг мертв, т.е. current == 0
     *  false, иначе
     */
    public boolean isDead() {
        assert health != null;
        System.out.printf("Current health: %d\n", health.current);
        return health.current <= 0;
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
        var dir = strategy.calculateDirection(heroLocation, enemyLocation);
        System.out.printf("Direction: (%d, %d)\n", dir.getX(), dir.getY());
        if (state == EnemyState.MUTABLE_SNEAKY) {
            return new Vector(-dir.getX(), -dir.getY());
        }
        return dir;
    }

    /*
     * Arguments:
     *  direction - направление в котором нужно передвинуть положение врага.
     */
    public void move(Vector direction) {
        var STATE_LIMIT = 15;
        enemyLocation = enemyLocation.move(direction);
        if (state != EnemyState.IMMUTABLE) {
            assert health != null;
            if (health.current <= STATE_LIMIT) {
                state = EnemyState.MUTABLE_SNEAKY;
            }
            else {
                state = EnemyState.MUTABLE_AGGRESSIVE;
            }
        }
    }

    /*
     * Arguments:
     *  CharacteristicsInfo - характеристики героя, которые должны измениться.
     * Return:
     *  boolean - был ли враг сконфужен.
     * Description:
     *  Имитирует драку между врагом и героем. Отвечает за изменение состояния врага (state).
     */
    public boolean fight(CharacteristicsInfo heroCharacteristics) {
        assert strategy != null;
        var res = strategy.fight(heroCharacteristics);
        System.out.printf("Damage = %d\n", res);
        if (res > 0) {
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
