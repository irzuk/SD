package org.Roguelike.model.enemies;

import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

public class SchoolkidStrategy implements BehaviorStrategy {
    private LocalDateTime lastFight;

    public SchoolkidStrategy() {
        lastFight = LocalDateTime.now();
    }
    @Override
    public @NotNull Vector calculateDirection(@NotNull MapElement heroLocation, @NotNull MapElement enemyLocation) {
        var x = heroLocation.leftTop().x() - enemyLocation.leftBot().x();
        var y = heroLocation.leftTop().y() - enemyLocation.leftBot().y();
        return new Vector(-x, -y);
    }

    @Override
    public int fight(@NotNull CharacteristicsInfo heroCharacteristics) {
        if (ChronoUnit.MILLIS.between(LocalDateTime.now(), lastFight) > DUR_MS) {
            lastFight = LocalDateTime.now();
            return 0;
        }
        var res = (heroCharacteristics.cheerfullness.current - INIT_CHEER) + (heroCharacteristics.satiety.current - INIT_SATIETY);
        var DAMAGE_CHEER = -5;
        var DAMAGE_SATIETY = -5;
        /* Thread.sleep(200, 0);
         * Uncomment if needed, but it may throw exception
         * */
        heroCharacteristics.cheerfullness.current -= DAMAGE_CHEER;
        heroCharacteristics.satiety.current -= DAMAGE_SATIETY;
        return res;
    }


    @Override
    public boolean tryConfuse() {
        var CONFUSE_PROB = 20;
        return ThreadLocalRandom.current().nextInt(0, 100) < CONFUSE_PROB;
    }
}
