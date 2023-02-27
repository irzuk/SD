package org.hw1;

import java.io.*;
import java.nio.channels.Pipe;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import org.hw1.commands.*;

public class CmdManager {
    // TODO 2 phase : add private static ThreadPoolExecutor executor;
    private static ThreadPoolExecutor executor;

    static InputStream startPipeline(List<Command> commands) throws IOException {
//        PipedInputStream previousInput;
//        ThreadPoolExecutor executor = new ThreadPoolExecutor();
//        for (Command command : commands) {
//
//            PipedOutputStream out = new PipedOutputStream();
//            PipedInputStream in = new PipedInputStream(out);
//
//            if (previousInput != null) {
//                command.setInputStream(previousInput);
//            }
//
//            command.setOutputStream(out);
//            previousInput = in;
//            executor.execute(command);
//
//        }
//  return previousInput;

        PipedOutputStream out = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream(out);

        Command command = commands.get(0);
        command.setOutputStream(out);
        Thread executor = new Thread(command);
        executor.start();
        return in;
    }
}
