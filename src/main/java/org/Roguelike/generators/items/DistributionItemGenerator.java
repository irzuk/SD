package org.Roguelike.generators.items;

import org.Roguelike.collections.items.Essence;
import org.Roguelike.collections.items.Item;
import org.Roguelike.collections.items.Thing;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DistributionItemGenerator implements ItemGenerator{
    @Override
    public @NotNull Item generateItem() {
        List<Item> items = new ArrayList<>();

        Collections.addAll(items, Thing.values());
        Collections.addAll(items, Essence.values());

        int sum = 0;
        for (var item : items) {
            sum += item.getProbability() * 100;
        }

        int randomVal = ThreadLocalRandom.current().nextInt(0, sum + 1);

        for (var item : items) {
            if (randomVal < item.getProbability() * 100) {
                return item;
            }
            randomVal -= item.getProbability() * 100;
        }
        return items.get(items.size() - 1);
    }
}