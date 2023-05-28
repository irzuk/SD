package org.Roguelike.UI.commands;

import org.Roguelike.model.GameModel;

public interface GameCommand {
    void execute(GameModel model);
}
