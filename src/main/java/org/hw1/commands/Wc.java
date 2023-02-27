package org.hw1.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Wc implements Command {
    private final List<@NotNull String> files;
    private PipedInputStream is;
    private PipedOutputStream os;
    private final int BUF_SIZE = 1024;

    public Wc() {
        files = null;
    }

    public Wc(@NotNull List<@NotNull String> files) {
        this.files = files;
    }

    @Override
    public void setInputStream(@Nullable PipedInputStream inputStream) {
        is = inputStream;
    }

    @Override
    public void setOutputStream(@NotNull PipedOutputStream outputStream) {
        os = outputStream;
    }

    @Override
    public void run() {
        if (files != null) {
            wcFiles();
        } else {
            wcIs();
        }
        try {
            os.close();
        } catch (IOException e) {
            System.out.println("wc: can't close output");
        }
    }

    private void wcFiles() {
        assert files != null;
        int totalLines = 0;
        int totalWords = 0;
        int totalBytes = 0;
        byte[] buf = new byte[BUF_SIZE];
        for (var fileName : files) {
            int lines = 0;
            int words = 0;
            int bytes = 0;
            if (!Files.exists(Paths.get(fileName))) {
                try {
                    os.write(String.format("wc: %s: No such file or directory", fileName).getBytes(StandardCharsets.UTF_8));
                    os.flush();
                } catch (IOException e) {
                    System.out.printf("wc: I/O exception in file: %s\n", fileName);
                }
                continue;
            }
            try (var fis = new FileInputStream(fileName)) {
                int res = fis.read(buf);
                while (res != -1) {
                    lines += getLines(buf, res);
                    words += getWords(buf, res);
                    bytes += res;
                    res = fis.read(buf);
                }
                lines++;
                os.write(String.format("%d %d %d %s\n", lines, words, bytes, fileName).getBytes(StandardCharsets.UTF_8));
                os.flush();
            } catch (IOException e) {
                System.out.printf("wc: I/O exception in file: %s\n", fileName);
            }
            totalLines += lines;
            totalWords += words;
            totalBytes += bytes;
        }
        if (files.size() > 1) {
            try {
                os.write(String.format("%d %d %d total\n", totalLines, totalWords, totalBytes).getBytes(StandardCharsets.UTF_8));
                os.flush();
            } catch (IOException e) {
                System.out.println("wc: can't write in output");
            }
        }
    }

    private void wcIs() {
        assert is != null;
        var buf = new byte[BUF_SIZE];
        int lines = 0;
        int words = 0;
        int bytes = 0;
        try {
            int res = is.read(buf);
            while (res != -1) {
                lines += getLines(buf, res);
                words += getWords(buf, res);
                bytes += res;
                res = is.read(buf);
            }
            lines++;
            os.write(String.format("%d %d %d\n", lines, words, bytes).getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            System.out.println("wc: exception in pipeline");
        }
    }

    private static int getLines(byte[] bytes, int to) {
        int lines = 0;
        for (int i = 0; i < to; ++i) {
            if (bytes[i] == '\n') {
                lines++;
            }
        }
        return lines;
    }

    private static int getWords(byte[] bytes, int to) {
        var str = new String(Arrays.copyOfRange(bytes, 0, to), StandardCharsets.UTF_8);
        str = str.trim();
        return str.isEmpty() ? 0 : str.split("\\s+").length;
    }
}
