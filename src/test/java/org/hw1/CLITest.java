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

    @Test
    public void testCat() throws Throwable {

        String commandWithArgs = "cat " + fileSimple.toString() + "\n";
        var input = new ByteArrayInputStream(commandWithArgs.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var output = new PrintStream(out);

        CLI.setInput(input);
        CLI.setOutput(output);
        CLI.main(null);

        Assert.assertArrayEquals("It's a simple test\nIt must work\n".getBytes(), out.toByteArray());

    }

}

