package org.Roguelike.collections.characteristics;

import org.jetbrains.annotations.NotNull;

public class CharacteristicsInfo {
    public Characteristic cheerfullness;
    public Characteristic satiety;
    public Characteristic health;

    public CharacteristicsInfo(@NotNull Characteristic cheerfullness,
                               @NotNull Characteristic satiety,
                               @NotNull Characteristic health) {
        this.cheerfullness = cheerfullness;
        this.satiety = satiety;
        this.health = health;
    }
}
