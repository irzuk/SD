package org.Roguelike.model.map;

import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.collections.items.Item;
import org.Roguelike.collections.map.Map;
import org.Roguelike.collections.map.MapLogicResult;
import org.Roguelike.collections.map.elements.HeroElement;
import org.Roguelike.collections.map.elements.MapElement;
import org.Roguelike.generators.items.DistributionItemGenerator;
import org.Roguelike.generators.items.ItemGenerator;
import org.Roguelike.generators.map.MapGenerator;
import org.Roguelike.generators.map.RoomGenerator;
import org.Roguelike.generators.map.SideWithDoor;
import org.Roguelike.collections.enemies.Enemy;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.Roguelike.collections.map.MapElementsParameters.*;
import static org.Roguelike.generators.map.SideWithDoor.*;

public class RoomLogic implements MapLogic {
    private @NotNull Map map;
    private final @NotNull MapGenerator mapGenerator;
    private final @NotNull ItemGenerator itemGenerator;
    private final @NotNull Lock chestsLock = new ReentrantLock();

    private final @NotNull AtomicBoolean mapChanged = new AtomicBoolean(true);

    public RoomLogic() {
        mapGenerator = new RoomGenerator();
        map = mapGenerator.generateMap(RIGHT);
        itemGenerator = new DistributionItemGenerator();
    }

    @Override
    public @NotNull MapLogicResult processHeroLocation(@NotNull MapElement location, @NotNull Vector vector) {
        for (var door : map.doors()) {
            if (vector.intersectsElement(location, door)) {
                var sideOfDoor = getSideByVector(vector);
                map = mapGenerator.generateMap(sideOfDoor);
                mapChanged.set(true);
                var newHeroLocation = getNewHeroLocationBySide(sideOfDoor);
                return new MapLogicResult(newHeroLocation, null);
            }
        }
        for (var line : map.roomLines()) {
            if (vector.intersectsLine(location, line)) {
                return new MapLogicResult(location, null);
            }
        }
        Item item = null;
        chestsLock.lock();
        for (var it = map.chests().iterator(); it.hasNext(); ) {
            var chest = it.next();
            if (vector.intersectsElement(location, chest)) {
                it.remove();
                item = itemGenerator.generateItem();
                break;
            }
        }
        chestsLock.unlock();
        var newHeroLocation = location.move(vector);
        return new MapLogicResult(newHeroLocation, item);
    }

    @Override
    public @NotNull List<@NotNull Vector> processEnemiesDirections(@NotNull List<@NotNull Enemy> enemies,
                                                                   @NotNull List<@NotNull Vector> directions) {

        return null;
    }

    @Override
    public @NotNull Map getMap() {
        chestsLock.lock();
        var chestsCopy = new LinkedList<>(List.copyOf(map.chests()));
        Map mapCopy = new Map(map.roomLines(), chestsCopy, map.doors());
        chestsLock.unlock();
        return mapCopy;
    }

    @Override
    public boolean pollMapChanged() {
        return mapChanged.compareAndSet(true, false);
    }

    private @NotNull SideWithDoor getSideByVector(Vector vector) {
        if (vector.isVertical()) {
            return vector.goesDown() ? TOP : BOT;
        } else {
            return vector.goesLeft() ? RIGHT : LEFT;
        }
    }

    private @NotNull MapElement getNewHeroLocationBySide(@NotNull SideWithDoor sideWithDoor) {
        assert map.doors().size() > 0;
        MapElement targetDoor = null;
        for (var door : map.doors()) {
            if (targetDoor == null) {
                targetDoor = door;
            }
            if (sideWithDoor == LEFT) {
                targetDoor = door.leftBot().x() < targetDoor.leftBot().x() ? door : targetDoor;
            } else if (sideWithDoor == RIGHT) {
                targetDoor = targetDoor.leftBot().x() < door.leftBot().x() ? door : targetDoor;
            } else if (sideWithDoor == TOP) {
                targetDoor = targetDoor.leftBot().y() < door.leftBot().y() ? door : targetDoor;
            } else if (sideWithDoor == BOT) {
                targetDoor = door.leftBot().y() < targetDoor.leftBot().y() ? door : targetDoor;
            }
        }
        Vector targetVector = null;
        if (sideWithDoor == LEFT) {
            targetVector = Vector.vectorRight(HERO_WIDTH + CELL_BORDER);
        } else if (sideWithDoor == RIGHT) {
            targetVector = Vector.vectorLeft(HERO_WIDTH + CELL_BORDER);
        } else if (sideWithDoor == TOP) {
            targetVector = Vector.vectorDown(HERO_HEIGHT + CELL_BORDER);
        } else if (sideWithDoor == BOT) {
            targetVector = Vector.vectorUp(HERO_HEIGHT + CELL_BORDER);
        }
        var heroLocation = HeroElement.fromPoint(targetDoor.leftBot());
        return heroLocation.move(targetVector);
    }
}
