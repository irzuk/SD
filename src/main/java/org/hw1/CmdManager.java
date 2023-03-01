package org.hw1;

import java.io.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.hw1.commands.*;
import org.jetbrains.annotations.NotNull;

public class CmdManager {
    // TODO 2 phase : add private static ThreadPoolExecutor executor;
    //private static ThreadPoolExecutor executor;

    private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    private static PrintStream errStream;
    public static void setErrStream(@NotNull PrintStream errStream) {
        CmdManager.errStream = errStream;
    }

    public static InputStream startPipeline(@NotNull List<@NotNull Command> commands) throws IOException {
        PipedInputStream previousInput = null;
        for (Command command : commands) {

            PipedOutputStream out = new PipedOutputStream();
            PipedInputStream in = new PipedInputStream(out);

            if (previousInput != null) {
                command.setInputStream(previousInput);
            }

            command.setOutputStream(out);
            command.setErrorStream(errStream);
            previousInput = in;
            executor.execute(command);
        }
        return previousInput;
    }

    public static void shutDown() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    public static void startThreadPool() {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    }
}
