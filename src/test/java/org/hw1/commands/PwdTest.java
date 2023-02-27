package org.hw1.commands;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PwdTest {
    @Test
    public void testSimple() throws IOException, InterruptedException {
        var is = new PipedInputStream();
        var os = new PipedOutputStream();
        var pwd = new Pwd();
        os.connect(is);
        pwd.setOutputStream(os);
        var t = new Thread(pwd);
        t.start();
        var BUF_SIZE = 1024;
        var buf = new byte[BUF_SIZE];
        int res = is.read(buf);
        int off = 0;
        while(res != -1) {
            off += res;
            res = is.read(buf, off, BUF_SIZE - off);
        }
        t.join();
        assertEquals(System.getProperty("user.dir"),
                new String(Arrays.copyOfRange(buf, 0, off), StandardCharsets.UTF_8));
    }
}
