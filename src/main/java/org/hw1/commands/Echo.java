package org.hw1.commands;

import org.hw1.CLILogger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.hw1.commands.CommandUtils.processException;

public class Echo implements Command{
    private static final CLILogger LOG = new CLILogger("Echo");
    @NotNull
    private final List<String> args;
    private PipedOutputStream os;
    private PrintStream errS;

    public Echo(@NotNull List<@NotNull String> args) {
        this.args = args;
    }

    @SuppressWarnings("unused")
    public Echo(String[] parametrs) { // Need for parser
        this.args = Arrays.asList(parametrs);
    }


    @Override
    public void setInputStream(PipedInputStream inputStream) {
        throw new UnsupportedOperationException();
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
        assert os != null;
        int i = 0;
        for (var str: args) {
            i++;
            try {
                os.write(str.getBytes(StandardCharsets.UTF_8));
                if (i != args.size()) {
                    os.write(" ".getBytes(StandardCharsets.UTF_8));
                }
                os.flush();
            } catch (IOException e) {
                processException(errS, LOG, e, "Echo", String.format("Can't write %s\n", str));
            }
        }
        try {
            os.close();
        } catch (IOException e) {
            processException(errS, LOG, e, "Echo", "Echo: can't stop correctly");
        }
    }
}
