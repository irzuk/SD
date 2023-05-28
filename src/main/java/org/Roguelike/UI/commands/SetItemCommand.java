package org.Roguelike.UI.commands;

import org.Roguelike.model.GameModel;
import org.Roguelike.model.KeyEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static org.Roguelike.model.KeyEvent.*;

public class SetItemCommand implements GameCommand {
    private static final @NotNull Map<@NotNull KeyEvent, @NotNull Integer> eventToInd;

    static {
        eventToInd = Map.of(
                USE_THING_1, 0,
                USE_THING_2, 1,
                USE_THING_3, 2,
                USE_THING_4, 3,
                USE_THING_5, 4,
                USE_THING_6, 5,
                USE_THING_7, 6,
                USE_THING_8, 7,
                USE_THING_9, 8,
                USE_THING_10, 9
        );
    }

    @NotNull
    private final KeyEvent keyEvent;

    public SetItemCommand(@NotNull KeyEvent keyEvent) {
        this.keyEvent = keyEvent;
    }

    @Override
    public void execute(GameModel model) {
        model.addTask(model.new SetItemTask(eventToInd.get(keyEvent)));
    }
}
