package org.hw1.commands;

import org.hw1.CLILogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.hw1.commands.CommandUtils.processException;

public class Wc implements Command {
    private static final CLILogger LOG = new CLILogger("Cat");
    private final List<@NotNull String> files;
    private PipedInputStream is;
    private PipedOutputStream os;
    private PrintStream errS;
    private final int BUF_SIZE = 1024;

    public Wc() {
        files = null;
    }

    public Wc(@NotNull List<@NotNull String> files) {
        this.files = files;
    }

    @SuppressWarnings("unused")
    public Wc(String[] files) { // Need for parser
        if(files.length != 0) {
            this.files = Arrays.asList(files);
        } else {
            this.files = null;
        }
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
    public void setErrorStream(@NotNull PrintStream errorStream) {
        errS = errorStream;
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
            errS.println("wc: can't close output");
            LOG.error("Wc exception " + e.getMessage());
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
                    processException(errS, LOG, e, "Wc", String.format("wc: I/O exception in file: %s\n", fileName));
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
                os.write(String.format("%d %d %d %s", lines, words, bytes, fileName).getBytes(StandardCharsets.UTF_8));
                if (files.size() != 1) {
                    os.write("\n".getBytes(StandardCharsets.UTF_8));
                }
                os.flush();
            } catch (IOException e) {
                processException(errS, LOG, e, "Wc", String.format("wc: I/O exception in file: %s\n", fileName));
            }
            totalLines += lines;
            totalWords += words;
            totalBytes += bytes;
        }
        if (files.size() > 1) {
            try {
                os.write(String.format("%d %d %d total", totalLines, totalWords, totalBytes).getBytes(StandardCharsets.UTF_8));
                os.flush();
            } catch (IOException e) {
                processException(errS, LOG, e, "wc", "wc: can't write in output");
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
            os.write(String.format("%6d %6d %6d", lines, words, bytes).getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            processException(errS, LOG, e, "Wc", "wc: exception in pipeline");
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
