package org.hw1.commands;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExecTest {
    static final Path dirPath = Paths.get("src/test/resources/commands/exec");
    static final Path lsCheck = Paths.get(dirPath + "/lsCheck");
    static final Path lsCheckA = Paths.get(lsCheck + "/a.txt");
    static final Path lsCheckB = Paths.get(lsCheck + "/b.txt");
    static final Path scriptFile = Paths.get(dirPath + "/script.sh");

    @BeforeAll
    public static void checkFiles() throws IOException {
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        if (!Files.exists(lsCheck)) {
            Files.createDirectories(lsCheck);
        }
        if (!Files.exists(lsCheckA)) {
            Files.createFile(lsCheckA);
        }
        if (!Files.exists(lsCheckB)) {
            Files.createFile(lsCheckB);
        }
        if (!Files.exists(scriptFile)) {
            Files.createFile(scriptFile);
            try (var writer = new FileWriter(scriptFile.toFile())) {
                writer.write("""
                    #!/bin/bash
                                        
                    echo "Hello, exec!" | wc
                    """);
            }
            assertTrue(scriptFile.toFile().setExecutable(true));
        }
    }

    @Test
    public void testExecLs() throws IOException {
        var exec = new Exec(new String[]{"ls", lsCheck.toString()});
        var is = new PipedInputStream();
        var os = new PipedOutputStream(is);
        exec.setOutputStream(os);
        var checker = new Thread(exec);
        checker.start();
        int BUF_SIZE = 1024;
        var buf = new byte[BUF_SIZE];
        int res = is.read(buf);
        int off = 0;
        while(res != -1) {
            off += res;
            res = is.read(buf, off, BUF_SIZE - off);
        }
        var str = new String(Arrays.copyOfRange(buf, 0, off), StandardCharsets.UTF_8);
        assertEquals("""
            a.txt
            b.txt
            """, str);
    }

    @Test
    public void testBashScript() throws IOException {
        var exec = new Exec(new String[]{scriptFile.toString()});
        var is = new PipedInputStream();
        var os = new PipedOutputStream(is);
        exec.setOutputStream(os);
        var checker = new Thread(exec);
        checker.start();
        int BUF_SIZE = 1024;
        var buf = new byte[BUF_SIZE];
        int res = is.read(buf);
        int off = 0;
        while(res != -1) {
            off += res;
            res = is.read(buf, off, BUF_SIZE - off);
        }
        var str = new String(Arrays.copyOfRange(buf, 0, off), StandardCharsets.UTF_8);
        var checkStr = "      1       2      13\n";
        assertEquals(checkStr, str);
    }
}
