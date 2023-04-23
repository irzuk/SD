package org.Roguelike.model.hero;

import org.Roguelike.collections.characteristics.Characteristic;
import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.geometry.Point;
import org.Roguelike.collections.inventory.Inventory;
import org.Roguelike.collections.inventory.SimpleInventory;
import org.Roguelike.collections.items.Item;
import org.Roguelike.collections.items.Thing;
import org.Roguelike.collections.map.elements.HeroElement;
import org.Roguelike.collections.map.elements.MapElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static org.Roguelike.collections.items.ItemType.THING;
import static org.Roguelike.collections.map.MapElementsParameters.MAP_HEIGHT;
import static org.Roguelike.collections.map.MapElementsParameters.MAP_WIDTH;
import static org.Roguelike.collections.map.MapElementsParameters.HERO_HEIGHT;
import static org.Roguelike.collections.map.MapElementsParameters.HERO_WIDTH;

public class SimpleHeroLogic implements HeroLogic {
    private final @NotNull CharacteristicsInfo characteristics;
    private final @NotNull Inventory inventory;
    private @NotNull MapElement location;
    private @Nullable Thing currentThing;
    public SimpleHeroLogic() {
        var cheerfullness = new Characteristic(30, 30);
        var satiety = new Characteristic(30, 30);
        var health = new Characteristic(50, 50);
        characteristics = new CharacteristicsInfo(cheerfullness, satiety, health);
        inventory = new SimpleInventory();
        location = HeroElement.heroFromPoint(new Point(MAP_WIDTH / 2 - HERO_WIDTH / 2, MAP_HEIGHT / 2 - HERO_HEIGHT / 2));
    }


    @Override
    public void processItem(@Nullable Item item) {
        if (item == null) {
            return;
        }
        if (item.getType() == THING) {
            inventory.add(item);
        } else {
            characteristics.cheerfullness.current += item.getCheerfullness();
            characteristics.satiety.current += item.getSatiety();
            if (characteristics.cheerfullness.current > characteristics.cheerfullness.full) {
                characteristics.cheerfullness.current = characteristics.cheerfullness.full;
            }
            if (characteristics.satiety.current > characteristics.satiety.full) {
                characteristics.satiety.current = characteristics.satiety.full;
            }
        }
    }

    @Override
    public boolean setItem(@NotNull Item item) {
        assert item.getType() == THING;
        if (!inventory.contains(item)) {
            return false;
        }
        if (currentThing != null) {
            if (!inventory.remove(item) || !inventory.add(currentThing)) {
                return false;
            }

            characteristics.cheerfullness.full -= currentThing.getCheerfullness();
            characteristics.satiety.full -= currentThing.getSatiety();
        }
        currentThing = (Thing) item;

        characteristics.cheerfullness.full += currentThing.getCheerfullness();
        characteristics.satiety.full += currentThing.getSatiety();
        return true;
    }

    @Override
    public boolean decreaseCharacteristics() {
        if (characteristics.cheerfullness.current == 0 && characteristics.satiety.current == 0) {
            if (characteristics.health.current == 0) {
                return false;
            } else {
                characteristics.health.current--;
                return true;
            }
        }
        if (characteristics.cheerfullness.current > 0) {
            characteristics.cheerfullness.current--;
        }
        if (characteristics.satiety.current > 0) {
            characteristics.satiety.current--;
        }
        return true;
    }

    @Override
    public @NotNull List<? extends Item> getAvailableItems() {
        return inventory.getAvailableItems();
    }

    @Override
    public @NotNull CharacteristicsInfo getCharacteristics() {
        return characteristics;
    }

    @Override
    public @NotNull MapElement getLocation() {
        return location;
    }

    @Override
    public void setLocation(@NotNull MapElement location) {
        this.location = location;
    }
}
