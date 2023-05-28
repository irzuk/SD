package org.Roguelike.UI.commands;

import org.Roguelike.collections.geometry.Vector;
import org.Roguelike.model.GameModel;
import org.Roguelike.model.KeyEvent;
import org.jetbrains.annotations.NotNull;

import static org.Roguelike.collections.map.MapElementsParameters.CELL_BORDER;

public class MoveCommand implements GameCommand {
    @NotNull
    private final KeyEvent keyEvent;
    public MoveCommand(@NotNull KeyEvent keyEvent) {
        this.keyEvent = keyEvent;
    }
    @Override
    public void execute(GameModel model) {
        var vector =  switch (keyEvent) {
            case GO_UP -> Vector.vectorDown(CELL_BORDER);
            case GO_DOWN -> Vector.vectorUp(CELL_BORDER);
            case GO_LEFT -> Vector.vectorLeft(CELL_BORDER);
            case GO_RIGHT -> Vector.vectorRight(CELL_BORDER);
            default -> throw new RuntimeException("Incorrect keyEvent");
        };
        model.addTask(model.new MoveTask(vector));
    }
}
