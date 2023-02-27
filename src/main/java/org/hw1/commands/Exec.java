package org.hw1.commands;

import org.jetbrains.annotations.Nullable;

import java.io.*;

public class Exec implements Command {

    private String commandToExec;
    @Nullable
    private PipedInputStream is;
    private PipedOutputStream os;

    // first param is name of command
    public Exec(String[] params) {
        if (params.length == 0) {
            throw new UnsupportedOperationException();
        } else {
            StringBuilder builder = new StringBuilder();
            for (var token : params) {
                builder.append(token);
            }
            commandToExec = builder.toString();
        }
    }

    @Override
    public void setInputStream(PipedInputStream inputStream) {
        is = inputStream;
    }

    @Override
    public void setOutputStream(PipedOutputStream outputStream) {
        os = outputStream;
    }

    @Override
    public void run() {
        String homeDirectory = System.getProperty("user.home");
        Process process;

        try {
            process = Runtime.getRuntime()
                    .exec(String.format(commandToExec, homeDirectory));
            OutputStream out = process.getOutputStream();
            InputStream in = process.getInputStream();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
