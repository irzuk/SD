package org.Roguelike.UI.commands;

import org.Roguelike.model.GameModel;

public class StopCommand implements GameCommand {
    @Override
    public void execute(GameModel model) {
        model.stop();
    }
}
