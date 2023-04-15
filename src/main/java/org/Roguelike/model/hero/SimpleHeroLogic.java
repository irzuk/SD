package org.Roguelike.model.hero;

import org.Roguelike.collections.characteristics.Characteristic;
import org.Roguelike.collections.characteristics.CharacteristicsInfo;
import org.Roguelike.collections.Point;
import org.Roguelike.collections.inventory.Inventory;
import org.Roguelike.collections.inventory.SimpleInventory;
import org.Roguelike.collections.items.Item;
import org.Roguelike.collections.items.Thing;

import java.util.List;

import static org.Roguelike.collections.items.ItemType.THING;
import static org.Roguelike.collections.map.MapElementsParameters.MAP_HEIGHT;
import static org.Roguelike.collections.map.MapElementsParameters.MAP_WIDTH;
import static org.Roguelike.collections.map.MapElementsParameters.HERO_HEIGHT;
import static org.Roguelike.collections.map.MapElementsParameters.HERO_WIDTH;

public class SimpleHeroLogic implements HeroLogic {
    private final CharacteristicsInfo characteristics;
    private final Inventory inventory;
    private Point position;
    private Thing currentThing;
    public SimpleHeroLogic() {
        var cheerfullness = new Characteristic(30, 30);
        var satiety = new Characteristic(30, 30);
        var health = new Characteristic(50, 50);
        characteristics = new CharacteristicsInfo(cheerfullness, satiety, health);
        inventory = new SimpleInventory();
        position = new Point(MAP_WIDTH / 2 - HERO_WIDTH / 2, MAP_HEIGHT / 2 - HERO_HEIGHT / 2);
    }


    @Override
    public void addItem(Item item) {
        inventory.add(item);
    }

    @Override
    public boolean setItem(Item item) {
        if (item.getType() != THING) {
            throw new RuntimeException("Incorrect Item: must be THING, got " + item.getType().name());
        }

        if (!inventory.remove(item) || !inventory.add(currentThing)) {
            return false;
        }

        characteristics.cheerfullness.full -= currentThing.getCheerfullness();
        characteristics.satiety.full -= currentThing.getSatiety();

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
    public List<Item> getAvailableItems() {
        return inventory.getAvailableItems();
    }

    @Override
    public CharacteristicsInfo getCharacteristics() {
        return characteristics;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setPosition(Point position) {
        this.position = position;
    }
}
