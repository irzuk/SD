package org.Roguelike.collections;

public class CharacteristicsInfo {
    public static class Characteristic {
        public int current;
        public int full;

        public Characteristic(int current, int full) {
            this.current = current;
            this.full = full;
        }
    }

    public Characteristic cheerfullness;
    public Characteristic satiety;
    public Characteristic health;

    public CharacteristicsInfo(Characteristic cheerfullness, Characteristic satiety, Characteristic health) {
        this.cheerfullness = cheerfullness;
        this.satiety = satiety;
        this.health = health;
    }
}
