package org.hw1.commands;

import org.jetbrains.annotations.NotNull;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class CommandsTestUtils {
    public static final int BUF_SIZE = 1024;
    public static @NotNull String readAllSmall(@NotNull InputStream is) throws IOException {
        var buf = new byte[BUF_SIZE];
        int res = is.read(buf);
        int off = 0;
        while(res != -1) {
            off += res;
            res = is.read(buf, off, BUF_SIZE - off);
        }
        return new String(Arrays.copyOfRange(buf, 0, off), StandardCharsets.UTF_8);
    }

    public static void checkDirAndCreate(@NotNull Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    public static void checkFileAndWrite(@NotNull Path path, @NotNull String text) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
            try (var writer = new FileWriter(path.toFile())) {
                writer.write(text);
            }
        }
    }
}
