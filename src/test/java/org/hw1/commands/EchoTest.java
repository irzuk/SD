package org.hw1.commands;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

import static org.hw1.commands.CommandsTestUtils.readAllSmall;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoTest {
    @Test
    public void testSimple() throws IOException, InterruptedException {
        var is = new PipedInputStream();
        var os = new PipedOutputStream(is);
        var echo = new Echo(List.of("Hello echo"));
        echo.setOutputStream(os);
        var t = new Thread(echo);
        t.start();
        var str = readAllSmall(is);
        assertEquals("Hello echo\n", str);
        t.join();
    }
}
