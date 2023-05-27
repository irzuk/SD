package org.Roguelike.collections.characteristics;

import org.jetbrains.annotations.NotNull;

public class CharacteristicsInfo {
    @NotNull
    public Characteristic cheerfullness;
    @NotNull
    public Characteristic satiety;
    @NotNull
    public Characteristic health;
    @NotNull
    public Characteristic experience;

    public CharacteristicsInfo(@NotNull Characteristic cheerfullness,
                               @NotNull Characteristic satiety,
                               @NotNull Characteristic health,
                               @NotNull Characteristic experience) {
        this.cheerfullness = cheerfullness;
        this.satiety = satiety;
        this.health = health;
        this.experience = experience;
    }
}
