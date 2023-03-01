package org.hw1;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CLITest {
    private static final Path dirPath = Paths.get("src/test/resources/commands/cat");
    private static final Path fileSimple = Paths.get(dirPath + "/CatTestSimple.txt");
    private static final Path fileSimpleSecond = Paths.get(dirPath + "/CatTestSimpleSecond.txt");
    private static final Path dirPathExec = Paths.get("src/test/resources/commands/exec");
    private static final Path scriptFile = Paths.get(dirPathExec + "/script.sh");

    @BeforeAll
    public static void setTestMode() {
        CLI.setTestMode(true);
    }

    @Test
    public void testExit() throws Exception {
        var out = runCommand("exit\n");
        assertEquals("", out.toString());
    }

    @Test
    public void testCat() throws Exception {
        var out = runCommand("cat " + fileSimple + "\nexit\n");
        assertEquals("It's a simple test\nIt must work", out.toString());
    }

    @Test
    public void testPwd() throws Exception {
        var out = runCommand("pwd\nexit\n");
        assertEquals(System.getProperty("user.dir"), out.toString());
    }

    @Test
    public void testEcho() throws Exception {
        var out = runCommand("echo \"Hello world!\"\nexit\n");
        assertEquals("Hello world!", out.toString());
    }

    @Test
    public void testWc() throws Exception {
        var out = runCommand("wc " + fileSimple + "\nexit\n");
        assertEquals("2 7 31 " + fileSimple, out.toString());
    }

    @Test
    public void testExec() throws Exception {
        // script test
        var out = runCommand(scriptFile + "\nexit\n");
        assertEquals("      1       2      13\n", out.toString());
    }

    @Test
    public void testVar() throws Exception {
        var out = runCommand("FILE=" + fileSimple + "\nexit\n");
        assertEquals(0, out.size());
    }


    @Test
    public void testVarWithCat() throws Exception {
        var out = runCommand("FILE=" + fileSimple + "\ncat $FILE\nexit\n");
        assertEquals("It's a simple test\nIt must work", out.toString());
    }

    @Test
    public void testVarWithEcho() throws Exception {
        var out = runCommand("VAR=Hello\necho $VAR\nexit\n");
        assertEquals("Hello", out.toString());
    }

    @Test
    public void testVarWithEchoDoubleQuotes() throws Exception {
        var out = runCommand("VAR=\"Hello World!\"\necho $VAR\nexit\n");
        assertEquals("Hello World!", out.toString());
    }

    @Test
    public void testTwoVarsWithEcho() throws Exception {
        //  x=ex y=it $x$y
        var out = runCommand("x=ex\ny=it\necho $x$y\necho $x\nexit\n");
        assertEquals("exitex", out.toString());
    }


    @Test
    public void testEchoWithWc() throws Exception {
        // echo 123 | wc 1 1 3
        var out = runCommand("echo \"123\" | wc\nexit\n");
        assertEquals("     1      1      3", out.toString());
    }


    @Test
    public void testCatWithWc() throws Exception {
        // cat CatTestSimple.txt | wc 2 7 31
        var out = runCommand("cat " + fileSimple + " | wc\nexit\n");
        assertEquals("     2      7     31", out.toString());
    }


    @Test
    public void testCatWithCat() throws Exception {
        var out = runCommand("cat " + fileSimpleSecond + " | cat " + fileSimple + "\nexit\n");
        assertEquals("It's a simple test\nIt must work", out.toString());
    }

    @Test
    public void testIncorrectCommand() throws Exception {
        var err = runCommand("abc\nexit\n", true);
        assertEquals("Command abc not found\n", err.toString());
    }

    private static @NotNull ByteArrayOutputStream runCommand(@NotNull String commandWithArgs) throws Exception {
        return runCommand(commandWithArgs, false);
    }

    private static @NotNull ByteArrayOutputStream runCommand(@NotNull String commandWithArgs, boolean returnErr) throws Exception {
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var err = new ByteArrayOutputStream();

        CLI.setInput(input);
        CLI.setOutput(new PrintStream(out));
        CLI.setErrStream(new PrintStream(err));
        CLI.main(null);

        return returnErr ? err : out;
    }
}