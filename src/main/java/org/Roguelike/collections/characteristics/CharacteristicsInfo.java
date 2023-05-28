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

    public void decreaseCheerfullness(int val) {
        cheerfullness.current = Math.max(0, cheerfullness.current - val);
    }

    public void decreaseSatiety(int val) {
        satiety.current = Math.max(0, satiety.current - val);
    }
}
