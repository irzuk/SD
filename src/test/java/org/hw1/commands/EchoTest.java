package org.hw1.commands;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoTest {
    @Test
    public void testSimple() throws IOException, InterruptedException {
        var is = new PipedInputStream();
        var os = new PipedOutputStream();
        var echo = new Echo(List.of("Hello echo"));
        os.connect(is);
        echo.setOutputStream(os);
        var t = new Thread(echo);
        t.start();
        var BUF_SIZE = 1024;
        var buf = new byte[BUF_SIZE];
        int res = is.read(buf);
        int off = 0;
        while(res != -1) {
            off += res;
            res = is.read(buf, off, BUF_SIZE - off);
        }
        assertEquals("Hello echo", new String(Arrays.copyOfRange(buf, 0, off), StandardCharsets.UTF_8));
        t.join();
    }
}
