package org.Roguelike.collections.items;

public enum Thing implements Item {
    CATEGORY_B_JOKE {
        @Override
        public int getCheerfullness() {
            return 0;
        }
        @Override
        public int getSatiety() {
            return 5;
        }
        @Override
        public String getDescription() {
            return "Category B joke";
        }
    },
    FINGER_PIN {
        @Override
        public int getCheerfullness() {
            return 0;
        }

        @Override
        public int getSatiety() {
            return 10;
        }
        @Override
        public String getDescription() {
            return "Finger pin";
        }
    },
    DIRTY_SOCKS {
        @Override
        public int getCheerfullness() {
            return 0;
        }

        @Override
        public int getSatiety() {
            return 15;
        }
        @Override
        public String getDescription() {
            return "Dirty socks";
        }
    },
    ELECTRIC_COLLAR {
        @Override
        public int getCheerfullness() {
            return 0;
        }

        @Override
        public int getSatiety() {
            return 20;
        }
        @Override
        public String getDescription() {
            return "Electric collar";
        }
    },
    UNFLATTERING_PHOTO {
        @Override
        public int getCheerfullness() {
            return 5;
        }

        @Override
        public int getSatiety() {
            return 0;
        }
        @Override
        public String getDescription() {
            return "Unflattering photo";
        }
    },
    TOOTHPICK {
        @Override
        public int getCheerfullness() {
            return 10;
        }

        @Override
        public int getSatiety() {
            return 0;
        }
        @Override
        public String getDescription() {
            return "Toothpick";
        }
    },
    WAISTBAND {
        @Override
        public int getCheerfullness() {
            return 15;
        }

        @Override
        public int getSatiety() {
            return 0;
        }
        @Override
        public String getDescription() {
            return "Waistband";
        }
    },
    GAG_IN_MOUTH {
        @Override
        public int getCheerfullness() {
            return 20;
        }

        @Override
        public int getSatiety() {
            return 0;
        }
        @Override
        public String getDescription() {
            return "Gag in mouth";
        }
    },
    DEMON_CONTRACT {
        @Override
        public int getCheerfullness() {
            return 10;
        }

        @Override
        public int getSatiety() {
            return 10;
        }
        @Override
        public String getDescription() {
            return "Demon contract";
        }
    },
    DEMIDOVICH_S_RESHEBNIK {
        @Override
        public int getCheerfullness() {
            return 15;
        }

        @Override
        public int getSatiety() {
            return 15;
        }
        @Override
        public String getDescription() {
            return "Reshebnik Demidovicha ";
        }
    };

    @Override
    public double getProbability() {
        return 0.1;
    }
    @Override
    public ItemType getType() {
        return ItemType.THING;
    }
}
