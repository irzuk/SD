package org.hw1.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Cat implements Command{
    private static final Logger LOG = Logger.getLogger("Cat");
    @Nullable private PipedInputStream is;
    private PipedOutputStream os;
    @Nullable private final List<@NotNull String> files;
    private final int BUF_SIZE = 1024;

    public Cat() {
        files = null;
    }

    public Cat(@Nullable List<@NotNull String> files) {
        this.files = files;
    }

    @SuppressWarnings("unused")
    public Cat(String[] files) { // Need for parser
        this.files = Arrays.stream(files).toList();
    }

    @Override
    public void setInputStream(@NotNull PipedInputStream inputStream) {
        is = inputStream;
    }

    @Override
    public void setOutputStream(@NotNull PipedOutputStream outputStream) {
        os = outputStream;
    }

    @Override
    public void run() {
        assert os != null;
        if (files != null) {
            catFiles();
        } else {
            catIs();
        }
        try {
            os.close();
        } catch (IOException e) {
            LOG.warning("cat: can't close output");
        }
    }

    private void catFiles() {
        assert files != null;
        byte[] buf = new byte[BUF_SIZE];
        for (var fileName : files) {
            try {
                try(var fis = new FileInputStream(fileName)) {
                    int res = fis.read(buf);
                    while(res != -1) {
                        os.write(buf, 0, res);
                        os.flush();
                        res = fis.read(buf);
                    }
                    os.write("\n".getBytes(StandardCharsets.UTF_8));
                } catch (FileNotFoundException e) {
                    os.write(String.format("cat: %s: No such file or directory", fileName).getBytes(StandardCharsets.UTF_8));
                }
            } catch (IOException e) {
                LOG.warning(String.format("cat: I/O exception in file: %s\n", fileName));
            }
        }
    }

    private void catIs() {
        assert is != null;
        byte[] buf = new byte[BUF_SIZE];
        try {
            int res = is.read(buf, 0, BUF_SIZE);
            while (res != -1) {
                os.write(buf, 0, res);
                os.flush();
                res = is.read(buf, 0, BUF_SIZE);
            }
        } catch (IOException e) {
            LOG.warning("cat: I/O exception in input");
        }
    }
}
