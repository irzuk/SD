package org.hw1.commands;

import org.hw1.CLILogger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.hw1.commands.CommandUtils.processException;

public class Pwd implements Command {
    private static final CLILogger LOG = new CLILogger("Exec");
    private PipedOutputStream os;
    private PrintStream errS;

    @Override
    public void setInputStream(PipedInputStream inputStream) {
    }

    @SuppressWarnings("unused")
    public Pwd(String[] parametrs) { // Need for parser
    }

    public Pwd() {
    }

    @Override
    public void setOutputStream(PipedOutputStream outputStream) {
        os = outputStream;
    }

    @Override
    public void setErrorStream(@NotNull PrintStream errorStream) {
        errS = errorStream;
    }

    @Override
    public void run() {
        var path = System.getProperty("user.dir");
        try {
            os.write(path.getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            processException(errS, LOG, e, "Pwd", "Can't write path");
        }
        try {
            os.close();
        } catch (IOException e) {
            processException(errS, LOG, e, "Pwd", "Pwd can't stop correctly");
        }
    }
}
