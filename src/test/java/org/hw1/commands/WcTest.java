package org.hw1.commands;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hw1.commands.CommandsTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WcTest {
    private static final Path dirPath = Paths.get("src/test/resources/commands/wc");
    private static final Path fileSimple = Paths.get(dirPath + "/WcTestSimple.txt");
    private static final Path fileSimpleSecond = Paths.get(dirPath + "/WcTestSimpleSecond.txt");

    @BeforeAll
    public static void checkFiles() throws IOException {
        checkDirAndCreate(dirPath);
        checkFileAndWrite(fileSimple, "Hello   Simple    \n");
        checkFileAndWrite(fileSimpleSecond, "    Hello Second\n Simple");
    }

    @Test
    public void testSimple() throws IOException, InterruptedException {
        var is = new PipedInputStream();
        var os = new PipedOutputStream(is);
        var wc = new Wc(List.of(fileSimple.toString()));
        wc.setOutputStream(os);
        var t = new Thread(wc);
        t.start();
        var str = readAllSmall(is);
        String checkString = String.format("2 2 19 %s\n", fileSimple);
        assertEquals(checkString, str);
        t.join();
    }

    @Test
    public void testSimilar() throws IOException, InterruptedException {
        var is = new PipedInputStream();
        var os = new PipedOutputStream(is);
        var wc = new Wc(List.of(fileSimple.toString(), fileSimpleSecond.toString()));
        wc.setOutputStream(os);
        var t = new Thread(wc);
        t.start();
        var str = readAllSmall(is);
        String checkString = String.format("""
            2 2 19 %s
            2 3 24 %s
            4 5 43 total
            """, fileSimple, fileSimpleSecond);
        assertEquals(checkString, str);
        t.join();
    }

    @Test
    public void testWcPipeline() throws IOException, InterruptedException {
        var toWcRead = new PipedInputStream();
        var toWrite = new PipedOutputStream(toWcRead);
        var toRead = new PipedInputStream();
        var toWcWrite = new PipedOutputStream(toRead);
        var wc = new Wc();
        wc.setInputStream(toWcRead);
        wc.setOutputStream(toWcWrite);
        var writer = new Thread(() -> {
            try {
                toWrite.write("Hello   wc!  \n  ".getBytes(StandardCharsets.UTF_8));
                toWrite.flush();
                toWrite.write("Count it".getBytes(StandardCharsets.UTF_8));
                toWrite.flush();
                toWrite.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        var worker = new Thread(wc);
        writer.start();
        worker.start();
        var str = readAllSmall(toRead);
        assertEquals("2 4 24\n", str);
        writer.join();
        worker.join();
    }
}
