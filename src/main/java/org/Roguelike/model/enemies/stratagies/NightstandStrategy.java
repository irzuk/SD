package org.Roguelike.model.enemies.stratagies;

import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

public class NightstandStrategy implements BehaviorStrategy {
    private LocalDateTime lastFight;
    public NightstandStrategy() {
        lastFight = LocalDateTime.now();
    }
    @Override
    public @NotNull Vector calculateDirection(@NotNull MapElement heroLocation, @NotNull MapElement enemyLocation) {
        return new Vector(0, 0);
    }

    @Override
    public int fight(@NotNull CharacteristicsInfo heroCharacteristics) {
        if (ChronoUnit.MILLIS.between(LocalDateTime.now(), lastFight) < DUR_MS) {
            return 0;
        }
        lastFight = LocalDateTime.now();
        var res = (heroCharacteristics.cheerfullness.current - INIT_CHEER) + (heroCharacteristics.satiety.current - INIT_SATIETY);
        var DAMAGE_CHEER = 5;
        var DAMAGE_SATIETY = 5;
        heroCharacteristics.decreaseCheerfullness(DAMAGE_CHEER);
        heroCharacteristics.decreaseSatiety(DAMAGE_SATIETY);
        return res;
    }

    @Override
    public boolean tryConfuse() {
        var CONFUSE_PROB = 25;
        return ThreadLocalRandom.current().nextInt(0, 100) < CONFUSE_PROB;
    }
}
