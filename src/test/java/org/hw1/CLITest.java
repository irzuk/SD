package org.hw1;

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
    public void testExit() throws Throwable {

        String commandWithArgs = "exit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        assertEquals("", out.toString());
    }

    @Test
    public void testCat() throws Throwable {
        String commandWithArgs = "cat " + fileSimple + "\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        assertEquals("It's a simple test\nIt must work", out.toString());
    }

    @Test
    public void testPwd() throws Throwable {
        String commandWithArgs = "pwd\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        assertEquals(System.getProperty("user.dir"), out.toString());
    }

    @Test
    public void testEcho() throws Throwable {
        String commandWithArgs = "echo \"Hello world!\"\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        assertEquals("Hello world!", out.toString());
    }

    @Test
    public void testWc() throws Throwable {
        String commandWithArgs = "wc " + fileSimple + "\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        assertEquals("2 7 31 " + fileSimple, out.toString());
    }

    @Test
    public void testExec() throws Throwable {
        // script test
        String commandWithArgs = scriptFile + "\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        assertEquals("      1       2      13\n", out.toString());
    }

    @Test
    public void testVar() throws Throwable {
        String commandWithArgs = "FILE=" + fileSimple + "\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        assertEquals(0, out.size());
    }


    @Test
    public void testVarWithCat() throws Throwable {
        String commandWithArgs = "FILE=" + fileSimple + "\ncat $FILE\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        assertEquals("It's a simple test\nIt must work", out.toString());
    }

    @Test
    public void testVarWithEcho() throws Throwable {
        String commandWithArgs = "VAR=Hello\necho $VAR\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        assertEquals("Hello", out.toString());
    }

    @Test
    public void testVarWithEchoDoubleQuotes() throws Throwable {
        String commandWithArgs = "VAR=\"Hello World!\"\necho $VAR\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        assertEquals("Hello World!", out.toString());
    }

    @Test
    public void testTwoVarsWithEcho() throws Throwable {
        //  x=ex y=it $x$y
        String commandWithArgs = "x=ex\ny=it\necho $x$y\necho $x\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        assertEquals("exitex", out.toString());
    }


    @Test
    public void testEchoWithWc() throws Throwable {
        // echo 123 | wc 1 1 3
        String commandWithArgs = "echo \"123\" | wc\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        assertEquals("     1      1      3", out.toString());
    }


    @Test
    public void testCatWithWc() throws Throwable {
        // cat CatTestSimple.txt | wc 2 7 31
        String commandWithArgs = "cat " + fileSimple + " | wc\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        assertEquals("     2      7     31", out.toString());
    }


    @Test
    public void testCatWithCat() throws Throwable {
        String commandWithArgs = "cat " + fileSimpleSecond + " | cat " + fileSimple + "\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        assertEquals("It's a simple test\nIt must work", out.toString());
    }
}