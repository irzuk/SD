package org.hw1.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

public class Exec implements Command {
    @NotNull
    private final String @NotNull [] cmdarray;
    @Nullable
    private PipedInputStream is;
    @Nullable
    private PipedOutputStream os;
    private final int BUF_SIZE = 1024;

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
    public void run() {
        try {
            var process = Runtime.getRuntime().exec(cmdarray);
            var processOut = process.getOutputStream();
            var processIn = process.getInputStream();
            writeInProcess(processOut);
            readFromProcess(processIn);
            if (os != null) {
                os.close();
            }
        } catch (IOException e) {
            System.out.printf("exec: can't run %s\n", String.join(" ", cmdarray));
        }
    }

    private void writeInProcess(@Nullable OutputStream processOut) throws IOException {
        if (is != null && processOut != null) {
            var buf = new byte[BUF_SIZE];
            int res = is.read(buf);
            while(res != -1) {
                processOut.write(buf, 0, res);
                res = is.read(buf);
            }
        }
    }

    private void readFromProcess(@Nullable InputStream processIn) throws IOException {
        if(os != null && processIn != null) {
            var buf = new byte[BUF_SIZE];
            int res = processIn.read(buf);
            while(res != -1) {
                os.write(buf, 0, res);
                res = processIn.read(buf);
            }
        }
    }
}
