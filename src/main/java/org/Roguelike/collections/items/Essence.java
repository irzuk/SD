package org.Roguelike.collections.items;

import org.jetbrains.annotations.NotNull;

public enum Essence implements Item {
    POTATO{
        @Override
        public int getCheerfullness() {
            return 5;
        }

        @Override
        public int getSatiety() {
            return 0;
        }

        @Override
        public @NotNull String getDescription() {
            return "Potato";
        }

        @Override
        public double getProbability() {
            return 0.13;
        }
    },
    GOOD_COLA{
        @Override
        public int getCheerfullness() {
            return 10;
        }

        @Override
        public int getSatiety() {
            return 0;
        }

        @Override
        public @NotNull String getDescription() {
            return "Cool Cola";
        }

        @Override
        public double getProbability() {
            return 0.11;
        }
    },
    CHOCOLATE_RUSSIA_IS_A_GENEROUS_SOUL{
        @Override
        public int getCheerfullness() {
            return 15;
        }

        @Override
        public int getSatiety() {
            return 0;
        }

        @Override
        public @NotNull String getDescription() {
            return "Chocolate \"Russia is a generous soul\"";
        }

        @Override
        public double getProbability() {
            return 0.9;
        }
    },
    CHEESEBURGER_TASTY_AND_POINT{
        @Override
        public int getCheerfullness() {
            return 20;
        }

        @Override
        public int getSatiety() {
            return 0;
        }

        @Override
        public @NotNull String getDescription() {
            return "Cheeseburger from \"Tasty and Point\"";
        }

        @Override
        public double getProbability() {
            return 0.7;
        }
    },
    FUNNY_JOKE{
        @Override
        public int getCheerfullness() {
            return 0;
        }

        @Override
        public int getSatiety() {
            return 5;
        }

        @Override
        public @NotNull String getDescription() {
            return "Funny joke";
        }

        @Override
        public double getProbability() {
            return 0.13;
        }
    },
    MEME{
        @Override
        public int getCheerfullness() {
            return 0;
        }

        @Override
        public int getSatiety() {
            return 10;
        }

        @Override
        public @NotNull String getDescription() {
            return "Meme";
        }

        @Override
        public double getProbability() {
            return 0.11;
        }
    },
    CIGARETTE_WINSTON_RED{
        @Override
        public int getCheerfullness() {
            return 0;
        }

        @Override
        public int getSatiety() {
            return 15;
        }

        @Override
        public @NotNull String getDescription() {
            return "Cigarette \"Winston Red\"";
        }

        @Override
        public double getProbability() {
            return 0.9;
        }
    },
    ADRENALINE_BUBBLE{
        @Override
        public int getCheerfullness() {
            return 0;
        }

        @Override
        public int getSatiety() {
            return 20;
        }

        @Override
        public @NotNull String getDescription() {
            return "Adrenaline bubble";
        }

        @Override
        public double getProbability() {
            return 0.7;
        }
    },
    NESCAFE_3_IN_1{
        @Override
        public int getCheerfullness() {
            return 15;
        }

        @Override
        public int getSatiety() {
            return 15;
        }

        @Override
        public @NotNull String getDescription() {
            return "Coffee \"Nescafe 3 in 1\"";
        }

        @Override
        public double getProbability() {
            return 0.6;
        }
    },
    RED_BULL{
        @Override
        public int getCheerfullness() {
            return 25;
        }

        @Override
        public int getSatiety() {
            return 25;
        }

        @Override
        public @NotNull String getDescription() {
            return "\"Red Bull\"";
        }

        @Override
        public double getProbability() {
            return 0.4;
        }
    };

    @Override
    public @NotNull ItemType getType() {
        return ItemType.ESSENCE;
    }
}
