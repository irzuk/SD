package org.hw1.commands;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import static org.hw1.commands.CommandsTestUtils.readAllSmall;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PwdTest {
    @Test
    public void testSimple() throws IOException, InterruptedException {
        var is = new PipedInputStream();
        var os = new PipedOutputStream(is);
        var pwd = new Pwd();
        pwd.setOutputStream(os);
        var t = new Thread(pwd);
        t.start();
        var str = readAllSmall(is);
        assertEquals(System.getProperty("user.dir"), str);
        t.join();
    }
}
