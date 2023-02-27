package org.hw1;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CLITest {
    private static final Path dirPath = Paths.get("src/test/resources/commands/cat");
    private static final Path fileSimple = Paths.get(dirPath + "/CatTestSimple.txt");
    private static final Path fileSimpleSecond = Paths.get(dirPath + "/CatTestSimpleSecond.txt");

//    x=ex y=it $x$y

    @Test
    public void testExit() throws Throwable {

        String commandWithArgs = "exit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        Assert.assertArrayEquals("".getBytes(), out.toByteArray());

    }

    @Test
    public void testCat() throws Throwable {

        String commandWithArgs = "cat " + fileSimple.toString() + "\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        Assert.assertArrayEquals("It's a simple test\nIt must work\n".getBytes(), out.toByteArray());

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

        Assert.assertArrayEquals(System.getProperty("user.dir").getBytes(), out.toByteArray());

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

        Assert.assertArrayEquals("Hello world!".getBytes(), out.toByteArray());

    }

    @Test
    public void testWc() throws Throwable {
        String commandWithArgs = "wc " + fileSimple.toString() + "\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        String ans = "1 7 31 " + fileSimple.toString() + "\n";
        Assert.assertArrayEquals(ans.getBytes(), out.toByteArray());

    }

    @Test
    public void testExec() throws Throwable {
        // cat example.txt | head
        String commandWithArgs = "echo \"Hello world!\"\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        Assert.assertArrayEquals("Hello world!".getBytes(), out.toByteArray());

    }

    @Test
    public void testVarWithCat() throws Throwable {
// TODO: java.lang.IndexOutOfBoundsException at org.hw1.Parser.parse(Parser.java:99)
        String commandWithArgs = "FILE=" + fileSimple.toString() + "\ncat $FILE\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        Assert.assertArrayEquals("It's a simple test\nIt must work\n".getBytes(), out.toByteArray());

    }

    @Test
    public void testVarWithEcho() throws Throwable {
// TODO: java.lang.IndexOutOfBoundsException at org.hw1.Parser.parse(Parser.java:99)
        String commandWithArgs = "VAR=Hello\necho $VAR\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        Assert.assertArrayEquals("Hello".getBytes(), out.toByteArray());

    }

    @Test
    public void testVarWithEchoDoubleQuotes() throws Throwable {
// TODO: java.lang.AssertionError at org.hw1.Parser.parse(Parser.java:104)
        String commandWithArgs = "VAR=\"Hello World!\"\necho $VAR\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        Assert.assertArrayEquals("Hello World!".getBytes(), out.toByteArray());

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

        Assert.assertArrayEquals("1 1 3\n".getBytes(), out.toByteArray());

    }


    @Test
    public void testCatWithWc() throws Throwable {
        // cat example.txt | wc 1 7 31
        String commandWithArgs = "cat " + fileSimple.toString() + " | wc\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        Assert.assertArrayEquals("3 7 32\n".getBytes(), out.toByteArray());

    }


    @Test
    public void testCatWithCat() throws Throwable {
        String commandWithArgs = "cat " + fileSimpleSecond.toString() + " | cat " + fileSimple.toString() + "\nexit\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        Assert.assertArrayEquals("It's a simple test\nIt must work\n".getBytes(), out.toByteArray());

    }

}

