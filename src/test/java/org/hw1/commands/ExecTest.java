package org.hw1.commands;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hw1.commands.CommandsTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExecTest {
    private static final Path dirPath = Paths.get("src/test/resources/commands/exec");
    private static final Path lsCheck = Paths.get(dirPath + "/lsCheck");
    private static final Path lsCheckA = Paths.get(lsCheck + "/a.txt");
    private static final Path lsCheckB = Paths.get(lsCheck + "/b.txt");
    private static final Path scriptFile = Paths.get(dirPath + "/script.sh");

    @BeforeAll
    public static void checkFiles() throws IOException {
        checkDirAndCreate(dirPath);
        checkDirAndCreate(lsCheck);
        checkFileAndWrite(lsCheckA, "a");
        checkFileAndWrite(lsCheckB, "b");
        checkFileAndWrite(scriptFile, """
                    #!/bin/bash
                                        
                    echo "Hello, exec!" | wc
                    """);
        assertTrue(scriptFile.toFile().setExecutable(true));
    }

    @Test
    public void testExecLs() throws IOException {
        if (!System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            var is = createAndStartExec(new String[]{"ls", lsCheck.toString()});
            var str = readAllSmall(is);
            assertEquals("""
                a.txt
                b.txt
                """, str);
        }
    }

    @Test
    public void testBashScript() throws IOException {
        if (!System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            var is = createAndStartExec(new String[]{scriptFile.toString()});
            var str = readAllSmall(is);
            var checkStr = "      1       2      13\n";
            assertEquals(checkStr, str);
        }
    }

    @Test
    public void testIncorrectCommand() throws InterruptedException {
        var errStream = new ByteArrayOutputStream();
        var exec = new Exec(new String[]{"abc"});
        exec.setErrorStream(new PrintStream(errStream));
        var checker = new Thread(exec);
        checker.start();
        checker.join();
        assertEquals("Command abc not found\n", errStream.toString());
    }

    private static @NotNull InputStream createAndStartExec(@NotNull String @NotNull[] cmdarray) throws IOException {
        var exec = new Exec(cmdarray);
        var is = new PipedInputStream();
        var os = new PipedOutputStream(is);
        exec.setOutputStream(os);
        exec.setErrorStream(System.err);
        var checker = new Thread(exec);
        checker.start();
        return is;
    }
}
