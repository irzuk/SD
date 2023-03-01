package org.hw1;

import java.io.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.hw1.commands.*;

public class CmdManager {
    // TODO 2 phase : add private static ThreadPoolExecutor executor;
    //private static ThreadPoolExecutor executor;

    private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);


    static InputStream startPipeline(List<Command> commands) throws IOException {
        PipedInputStream previousInput = null;
        for (Command command : commands) {

            PipedOutputStream out = new PipedOutputStream();
            PipedInputStream in = new PipedInputStream(out);

            if (previousInput != null) {
                command.setInputStream(previousInput);
            }

            command.setOutputStream(out);
            previousInput = in;
            executor.execute(command);
        }
        return previousInput;
    }

    static public void shutDown() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    static public void startThreadPool() {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    }
}
