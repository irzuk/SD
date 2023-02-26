package org.hw1.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Cat implements Command{
    @Nullable private PipedInputStream is;
    private PipedOutputStream os;
    @Nullable private final List<@NotNull String> files;
    private final int BUF_SIZE = 1024;

    public Cat(@Nullable List<@NotNull String> files) {
        this.files = files;
    }

    public Cat(String[] files) {
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
            System.out.println("cat: can't close output");
        }
    }

    private void catFiles() {
        assert files != null;
        for (var fileName : files) {
            var file = new File(fileName);
            byte[] buf = new byte[BUF_SIZE];
            try {
                try(var fis = new FileInputStream(file)) {
                    int res = fis.read(buf);
                    while(res != -1) {
                        os.write(buf, 0, res);
                        res = fis.read(buf);
                    }
                    os.write("\n".getBytes(StandardCharsets.UTF_8));
                } catch (FileNotFoundException e) {
                    os.write(String.format("cat: %s: No such file or directory", fileName).getBytes(StandardCharsets.UTF_8));
                }
            } catch (IOException e) {
                System.out.printf("cat: can't read file: %s\n", fileName);
            }
        }
    }

    private void catIs() {
        assert is != null;
        byte[] buf = new byte[BUF_SIZE];
        try {
            while (is.available() > 0) {
                int res = is.read(buf, 0, BUF_SIZE);
                os.write(buf, 0, res);
            }
        } catch (IOException e) {
            System.out.println("cat: can't read input");
        }
    }
}
