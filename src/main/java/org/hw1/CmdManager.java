package org.hw1;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

import org.hw1.commands.*;

public class CmdManager {
    // private static ThreadPoolExecutor executor; TODO 2 phase

    static OutputStream startPipline(List<Command> commands) throws IOException {
        PipedInputStream previous;
        for (Command command : commands) {

            PipedOutputStream out = new PipedOutputStream();
            PipedInputStream in = new PipedInputStream(out);

            command.setInputStream(null);
            command.setOutputStream(null);
            Thread executor = new Thread(command);
            executor.start();

        }
        return null;
    }
}
