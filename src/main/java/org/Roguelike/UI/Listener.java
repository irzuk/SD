package org.Roguelike.UI;

import org.Roguelike.UI.commands.MoveCommand;
import org.Roguelike.UI.commands.SetItemCommand;
import org.Roguelike.UI.commands.StopCommand;
import org.Roguelike.model.GameModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static org.Roguelike.model.KeyEvent.*;

public class Listener implements KeyListener {
    GameModel gameModel;

    public Listener(GameModel model) {
        gameModel = model;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 87 || code == 83 || code == 65 || code == 68) {
            var event = switch (code) {
                case 87 -> GO_UP;
                case 83 -> GO_DOWN;
                case 65 -> GO_LEFT;
                case 68 -> GO_RIGHT;
                default -> throw new IllegalStateException("Unexpected value: " + code);
            };
            var command = new MoveCommand(event);
            command.execute(gameModel);
        } else if (48 <= code && code <= 57) {
            var event = switch (code) {
                case 49 -> USE_THING_1;
                case 50 -> USE_THING_2;
                case 51 -> USE_THING_3;
                case 52 -> USE_THING_4;
                case 53 -> USE_THING_5;
                case 54 -> USE_THING_6;
                case 55 -> USE_THING_7;
                case 56 -> USE_THING_8;
                case 57 -> USE_THING_9;
                case 48 -> USE_THING_10;
                default -> throw new IllegalStateException("Unexpected value: " + code);
            };
            var command = new SetItemCommand(event);
            command.execute(gameModel);
        } else if (code == 27) {
            var command = new StopCommand();
            command.execute(gameModel);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
