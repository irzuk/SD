package org.Roguelike.model.enemies;

import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class NightstandStrategy implements BehaviorStrategy {
    @Override
    public @NotNull Vector calculateDirection(@NotNull MapElement heroLocation, @NotNull MapElement enemyLocation) {
        return new Vector(0, 0);
    }

    @Override
    public int fight(@NotNull CharacteristicsInfo heroCharacteristics) {
        var res = (heroCharacteristics.cheerfullness.current - INIT_CHEER) + (heroCharacteristics.satiety.current - INIT_SATIETY);
        var DAMAGE_CHEER = 5;
        var DAMAGE_SATIETY = 5;
        /* Thread.sleep(200, 0);
         * Uncomment if needed, but it may throw exception
         * */
        heroCharacteristics.cheerfullness.current -= DAMAGE_CHEER;
        heroCharacteristics.satiety.current -= DAMAGE_SATIETY;
        return res;
    }

    @Override
    public boolean tryConfuse() {
        var CONFUSE_PROB = 25;
        return ThreadLocalRandom.current().nextInt(0, 100) < CONFUSE_PROB;
    }
}
