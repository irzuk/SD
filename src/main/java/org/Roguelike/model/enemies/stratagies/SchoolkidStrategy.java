package org.Roguelike.model.enemies.stratagies;

import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.enemies.EnemyState;
import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.map.MapElementsParameters;
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
        int x = (int) Math.signum(heroLocation.leftTop().x() - enemyLocation.leftBot().x());
        int y = (int) Math.signum(heroLocation.leftTop().y() - enemyLocation.leftBot().y());
        if (x == 0) {
            return new Vector(0, -y);
        }
        if (y == 0) {
            return new Vector(-x, 0);
        }
        if (ThreadLocalRandom.current().nextInt(0, 2) == 0) {
            return new Vector(0, -y);
        }
        System.out.printf("Hero  location: (%d, %d)\n", heroLocation.leftBot().x(), heroLocation.leftBot().y());
        System.out.printf("Enemy location: (%d, %d)\n", enemyLocation.leftBot().x(), enemyLocation.leftBot().y());
        return new Vector(-x, 0);
    }

    @Override
    public int fight(@NotNull CharacteristicsInfo heroCharacteristics) {
        if (ChronoUnit.MILLIS.between(lastFight, LocalDateTime.now()) < DUR_MS) {
            System.out.println("Timing fallback");
            return 0;
        }
        lastFight = LocalDateTime.now();

        var res = (heroCharacteristics.cheerfullness.current - INIT_CHEER) + (heroCharacteristics.satiety.current - INIT_SATIETY);

        var DAMAGE_CHEER = -5;
        var DAMAGE_SATIETY = -5;
        heroCharacteristics.decreaseCheerfullness(DAMAGE_CHEER);
        heroCharacteristics.decreaseSatiety(DAMAGE_SATIETY);
        return res;
    }


    @Override
    public boolean tryConfuse() {
        var CONFUSE_PROB = 10;
        return ThreadLocalRandom.current().nextInt(0, 100) < CONFUSE_PROB;
    }

}
