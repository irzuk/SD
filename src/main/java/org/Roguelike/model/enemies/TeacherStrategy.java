package org.Roguelike.model.enemies;

import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class TeacherStrategy implements BehaviorStrategy {
    @Override
    public @NotNull Vector calculateDirection(@NotNull MapElement heroLocation, @NotNull MapElement enemyLocation) {
        var x = heroLocation.leftTop().x() - enemyLocation.leftBot().x();
        var y = heroLocation.leftTop().y() - enemyLocation.leftBot().y();
        return new Vector(x, y);
    }

    @Override
    public int fight(@NotNull CharacteristicsInfo heroCharacteristics) {
        var res = (heroCharacteristics.cheerfullness.current - INIT_CHEER) + (heroCharacteristics.satiety.current - INIT_SATIETY);
        var DAMAGE_CHEER = 10;
        var DAMAGE_SATIETY = 10;
        /* Thread.sleep(200, 0);
         * Uncomment if needed, but it may throw exception
         * */
        heroCharacteristics.cheerfullness.current -= DAMAGE_CHEER;
        heroCharacteristics.satiety.current -= DAMAGE_SATIETY;
        return res;
    }

    @Override
    public boolean tryConfuse() {
        var CONFUSE_PROB = 15;
        return ThreadLocalRandom.current().nextInt(0, 100) < CONFUSE_PROB;
    }
}
