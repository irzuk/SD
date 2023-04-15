package org.Roguelike.model;

import org.Roguelike.collections.items.Thing;

import static org.Roguelike.collections.items.Thing.*;

interface ThingGetter{
    Thing getThing();
}

public enum KeyEvent implements ThingGetter{
    GO_UP{
        public Thing getThing() { return null; }
    },
    GO_DOWN {
        public Thing getThing() { return null; }
    },
    GO_LEFT{
        public Thing getThing() { return null;}
    },
    GO_RIGHT {
        public Thing getThing() { return null; }
    },
    USE_THING_1{
        public Thing getThing() { return CATEGORY_B_JOKE; }
    },
    USE_THING_2{
        public Thing getThing() { return FINGER_PIN; }
    },
    USE_THING_3{
        public Thing getThing() { return DIRTY_SOCKS; }
    },
    USE_THING_4{
        public Thing getThing() { return ELECTRIC_COLLAR; }
    },
    USE_THING_5{
        public Thing getThing() { return UNFLATTERING_PHOTO; }
    },
    USE_THING_6{
        public Thing getThing() { return TOOTHPICK; }
    },
    USE_THING_7{
        public Thing getThing() { return WAISTBAND; }
    },
    USE_THING_8{
        public Thing getThing() { return GAG_IN_MOUTH; }
    },
    USE_THING_9{
        public Thing getThing() { return DEMON_CONTRACT; }
    },
    USE_THING_10{
        public Thing getThing() { return DEMIDOVICH_S_RESHEBNIK; }
    }
}
