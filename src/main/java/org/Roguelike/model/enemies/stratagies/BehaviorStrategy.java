package org.Roguelike.model.enemies.stratagies;

import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.enemies.EnemyState;
import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;

/*
 * Интерфейс описывает стратегии поведения врагов.
 */
public interface BehaviorStrategy {
    int INIT_CHEER = 30;
    int INIT_SATIETY = 30;
    int DUR_MS = 200;

    /*
     * Arguments:
     *  heroLocation - позиция героя в текущий момент.
     *  curLocation - позиция врага в текущий момент.
     * Return:
     *  Vector - направление, в котором должен двигаться враг.
     * Description:
     *  Метод вычисляет направление движения врага, учитывая при этом положение героя на карте.
     */
    @NotNull Vector calculateDirection(@NotNull MapElement heroLocation, @NotNull MapElement enemyLocation);

    /*
     * Arguments:
     *  heroCharacteristics - ссылка на характеристики героя.
     * Return:
     *  int - урон, который герой нанес врагу.
     * Description:
     *  Метод разыгрывает бой между героем и врагом. Герой получает урон, соответствующий стратегии,
     *  метод также ИЗМЕНЯЕТ ЕГО ХАРАКТЕРИСТИКИ! (current)
     *  Бой должен выполняться с ЗАДЕРЖКОЙ ПО ВРЕМЕНИ, например, 200-300 мс.
     */
    int fight(@NotNull CharacteristicsInfo heroCharacteristics);

    /*
     * Return:
     *  bool - был ли враг за конфужен.
     * Description:
     *  Метод пытается вызвать конфузию у врага. Вероятность конфузии зависит от стратегии врага.
     */
    boolean tryConfuse();

}
