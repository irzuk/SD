package org.Roguelike.model.enemies;

import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.enemies.ConfusedEnemy;
import org.Roguelike.collections.enemies.Enemy;
import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.map.Map;
import org.Roguelike.collections.map.elements.MapElement;
import org.Roguelike.generators.enemies.EnemiesGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SimpleEnemiesLogic implements EnemiesLogic {
    private List<@NotNull Enemy> enemies;

    public SimpleEnemiesLogic(@NotNull Map map) {
        processMap(map);
    }

    @Override
    public @NotNull List<@NotNull Vector> calculateDirections(@NotNull MapElement heroLocation) {
        List<Vector> result = new ArrayList<>();
        for (var enemy : enemies) {
            result.add(enemy.findDirection(heroLocation));
        }
        return result;
    }

    @Override
    public @NotNull List<@NotNull Enemy> getEnemies() {
        return enemies; //.stream().filter(enemy -> !enemy.isDead()).collect(Collectors.toList());
    }
    /*
     * Arguments:
     *  directions - список из направлений, в которых должны двигаться враги;
     *  heroLocation - положение героя на карте;
     *  heroCharacteristics - характеристики героя
     * Return:
     *  Количество опыта, полученное после столкновения
     * Description:
     *  Двигает врагов. Находит врагов, которые пересекаются с героем и выполняет фазу битвы между ними.
     *  Определяет убитых врагов, считает полученный за них опыт.
     */
    @Override
    public int processMovement(@NotNull List<@NotNull Vector> directions, @NotNull MapElement heroLocation, @NotNull CharacteristicsInfo heroCharacteristics) {
        assert directions.size() == enemies.size();
        for (int i = 0; i < directions.size(); i++) {
            enemies.get(i).move(directions.get(i));
        }

        int result = 0;
        for (int i = 0; i < enemies.size(); i++) {
            var enemy = enemies.get(i);
            if (enemy.getEnemyLocation().intersects(heroLocation.getBounds2D())) {
                System.out.println("Fight!");
                boolean confused = enemy.fight(heroCharacteristics);
                if (enemy.isDead()) {
                    enemies.remove(i);
                    i--;
                    result += enemy.getExperience();
                }
                else if (confused) {
                    enemies.set(i, new ConfusedEnemy(enemy));
                }
            }
        }
        return result;
    }

    @Override
    public final void processMap(@NotNull Map map) {
        EnemiesGenerator g = new EnemiesGenerator();
        enemies = g.generateEnemies(map);
    }
}
