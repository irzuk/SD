package org.hw1.commands;

import org.hw1.CLILogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

import static org.hw1.commands.CommandUtils.processException;

public class Exec implements Command {
    private static final CLILogger LOG = new CLILogger("Exec");
    @NotNull
    private final String @NotNull [] cmdarray;
    @Nullable
    private PipedInputStream is;
    @Nullable
    private PipedOutputStream os;
    private PrintStream errS;

    // first param is name of command
    public Exec(String[] cmdarray) {
        assert cmdarray.length != 0;
        this.cmdarray = cmdarray;
    }

    @Override
    public void setInputStream(@Nullable PipedInputStream inputStream) {
        is = inputStream;
    }

    @Override
    public void setOutputStream(@Nullable PipedOutputStream outputStream) {
        os = outputStream;
    }

    @Override
    public void setErrorStream(@NotNull PrintStream errorStream) {
        errS = errorStream;
    }

    @Override
    public void run() {
        try {
            try {
                var process = Runtime.getRuntime().exec(cmdarray);
                var processOut = process.getOutputStream();
                var processIn = process.getInputStream();
                writeReadProcess(processIn, processOut);
            } catch (IOException e) {
                if (e.getMessage().startsWith("Cannot run program")) {
                    errS.printf("Command %s not found\n", cmdarray[0]);
                } else {
                    throw e;
                }
            }
            if (os != null) {
                os.close();
            }
        } catch (IOException e) {
            processException(errS, LOG, e, "Exec", String.format("exec: can't run %s\n", String.join(" ", cmdarray)));
        }
    }

    private void writeReadProcess(@Nullable InputStream processIn, @Nullable OutputStream processOut) throws IOException {
        int BUF_SIZE = 1024;
        var buf = new byte[BUF_SIZE];
        if (processOut != null && is != null) {
            int res = is.read(buf);
            while(res != -1) {
                processOut.write(buf, 0, res);
                if (processIn != null && os != null) {
                    while(processIn.available() > 0) {
                        int resRead = processIn.read(buf);
                        os.write(buf, 0, resRead);
                    }
                }
                res = is.read(buf);
            }
        }
        if (processIn != null && os != null) {
            int res = processIn.read(buf);
            while(res != -1) {
                os.write(buf, 0, res);
                res = processIn.read(buf);
            }
        }
    }
}
