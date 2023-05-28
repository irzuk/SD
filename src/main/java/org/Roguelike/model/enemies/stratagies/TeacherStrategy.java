package org.Roguelike.model.enemies.stratagies;

import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.map.MapElementsParameters;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

public class TeacherStrategy implements BehaviorStrategy {
    private LocalDateTime lastFight;

    public TeacherStrategy() {
        lastFight = LocalDateTime.now();
    }
    @Override
    public @NotNull Vector calculateDirection(@NotNull MapElement heroLocation, @NotNull MapElement enemyLocation) {
        var x = Math.abs(heroLocation.leftTop().x() - enemyLocation.leftBot().x());
        var y = Math.abs(heroLocation.leftTop().y() - enemyLocation.leftBot().y());
        if (x == 0) {
            return new Vector(0, y * MapElementsParameters.CELL_BORDER);
        }
        if (y == 0) {
            return new Vector(x * MapElementsParameters.CELL_BORDER, 0);
        }
        if (ThreadLocalRandom.current().nextInt(0, 2) == 0) {
            return new Vector(0, y * MapElementsParameters.CELL_BORDER);
        }
        return new Vector(x * MapElementsParameters.CELL_BORDER, 0);
    }

    @Override
    public int fight(@NotNull CharacteristicsInfo heroCharacteristics) {
        if (ChronoUnit.MILLIS.between(LocalDateTime.now(), lastFight) < DUR_MS) {
            return 0;
        }
        lastFight = LocalDateTime.now();
        var res = (heroCharacteristics.cheerfullness.current - INIT_CHEER) + (heroCharacteristics.satiety.current - INIT_SATIETY);
        var DAMAGE_CHEER = 10;
        var DAMAGE_SATIETY = 10;
        heroCharacteristics.decreaseCheerfullness(DAMAGE_CHEER);
        heroCharacteristics.decreaseSatiety(DAMAGE_SATIETY);
        return res;
    }

    @Override
    public boolean tryConfuse() {
        var CONFUSE_PROB = 15;
        return ThreadLocalRandom.current().nextInt(0, 100) < CONFUSE_PROB;
    }
}
