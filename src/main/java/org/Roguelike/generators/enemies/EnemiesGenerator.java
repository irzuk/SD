package org.Roguelike.generators.enemies;

import org.Roguelike.collections.map.Map;
import org.Roguelike.model.enemies.Enemy;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/*
 * TODO: Миша
 * Класс для генерации врагов на карте
 */
public interface EnemiesGenerator {
    /*
     * Arguments:
     *  map - карта.
     * Return:
     *  список врагов.
     * Description:
     *  Генерирует врагов на карте. Количество врагов и их типы конкретно не определены (см. описание).
     */
    @NotNull List<@NotNull Enemy> generateEnemies(@NotNull Map map);
}
