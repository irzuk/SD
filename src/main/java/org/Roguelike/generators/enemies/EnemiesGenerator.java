package org.Roguelike.generators.enemies;

import org.Roguelike.collections.characteristics.Characteristic;
import org.Roguelike.collections.enemies.Enemy;
import org.Roguelike.model.enemies.stratagies.BehaviorStrategy;
import org.Roguelike.model.enemies.stratagies.NightstandStrategy;
import org.Roguelike.model.enemies.stratagies.SchoolkidStrategy;
import org.Roguelike.model.enemies.stratagies.TeacherStrategy;
import org.Roguelike.collections.geometry.Point;
import org.Roguelike.collections.map.Map;
import org.Roguelike.collections.map.MapElementsParameters;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Класс для генерации врагов на карте
 */
public class EnemiesGenerator {
    /*
     * Arguments:
     *  map - карта.
     * Return:
     *  список врагов.
     * Description:
     *  Генерирует врагов на карте. Количество врагов и их типы конкретно не определены (см. описание).
     */
    public @NotNull List<@NotNull Enemy> generateEnemies(@NotNull Map map) {

        List<Enemy> enemyList = new ArrayList<>();
        var enemy_percent = 2;
        for (int row = MapElementsParameters.CHEST_HEIGHT;
             row + MapElementsParameters.CHEST_HEIGHT < MapElementsParameters.MAP_HEIGHT;
             row += MapElementsParameters.CHEST_HEIGHT) {
            for (int col = MapElementsParameters.CHEST_WIDTH;
                 col + MapElementsParameters.CHEST_WIDTH < MapElementsParameters.MAP_WIDTH;
                 col += MapElementsParameters.CHEST_WIDTH) {
                var tmp = ThreadLocalRandom.current().nextInt(0, 100);

                boolean ok = true;
                for (var chest : map.chests()) {
                    if (chest.leftBot().equals(new Point(row, col))) {
                        ok = false;
                        break;
                    }
                }

                if (!ok) {
                    continue;
                }

                if (tmp < enemy_percent) {
                    var type = ThreadLocalRandom.current().nextInt(0, 3);
                    BehaviorStrategy strat;
                    int exp;
                    int health;
                    if (type == 0) {
                        strat = new TeacherStrategy();
                        exp = 15;
                        health = 35;
                    } else if (type == 1) {
                        strat = new NightstandStrategy();
                        exp = 10;
                        health = 25;
                    } else {
                        strat = new SchoolkidStrategy();
                        exp = 5;
                        health = 30;
                    }

                    enemyList.add(new Enemy(exp, new Characteristic(health, health), MapElement.fromPoint(new Point(row, col)), strat));
                }
            }
        }
        return enemyList;
    }
}
