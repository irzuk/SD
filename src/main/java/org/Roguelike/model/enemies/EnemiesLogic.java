package org.Roguelike.model.enemies;

import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.enemies.Enemy;
import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.map.Map;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;
/*
 * TODO: Миша
 * Класс, инкапсулирующий в себе логику работы врагов.
 */
public interface EnemiesLogic {
    /*
     * Arguments:
     *  heroLocation - положение героя на карте.
     * Return:
     *  List<Vector> - направления, в которых должны двигаться враги.
     */
    @NotNull List<@NotNull Vector> calculateDirections(@NotNull MapElement heroLocation);

    /*
     * Return:
     *  список врагов.
     */
    @NotNull List<@NotNull Enemy> getEnemies();

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
    int processMovement(@NotNull List<@NotNull Vector> directions,
                        @NotNull MapElement heroLocation,
                        @NotNull CharacteristicsInfo heroCharacteristics);

    /*
     * Arguments:
     *  map - карта
     * Description:
     *  Генерирует врагов на новой карте. Старый список врагов удаляется.
     */
    void processMap(@NotNull Map map);
}
