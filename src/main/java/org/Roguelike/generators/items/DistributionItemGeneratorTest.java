package org.Roguelike.generators.items;

import org.Roguelike.collections.items.Essence;
import org.Roguelike.collections.items.Item;
import org.Roguelike.collections.items.Thing;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DistributionItemGeneratorTest {

    @Test
    void BasicTest() {
        List<Item> items = new ArrayList<>();

        Collections.addAll(items, Thing.values());
        Collections.addAll(items, Essence.values());

        double total_prob = items.stream().map(x -> x.getProbability()).reduce(0.0, Double::sum);


        int ATTEMPTS = 100 * 100;

        DistributionItemGenerator gen = new DistributionItemGenerator();

        List<Item> res = new ArrayList<>();
        for (int att = 0; att < ATTEMPTS; att++) {
            res.add(gen.generateItem());
        }

        List<Integer> cnts = new ArrayList<>();
        for (var item : items) {
            int cnt = 0;
            for (var nitem : res) {
                if (item.getClass() == nitem.getClass()) {
                    cnt++;
                }
            }
            cnts.add(cnt);
            double eps = 0.3;
            assertTrue(item.getProbability() * ATTEMPTS / total_prob > (1 - eps) * cnt);
            assertTrue(item.getProbability() * ATTEMPTS / total_prob < (1 + eps) * cnt);
        }
    }
}