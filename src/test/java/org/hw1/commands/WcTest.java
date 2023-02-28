package org.hw1.commands;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WcTest {
    private static final Path dirPath = Paths.get("src/test/resources/commands/wc");
    private static final Path fileSimple = Paths.get(dirPath + "/WcTestSimple.txt");
    private static final Path fileSimpleSecond = Paths.get(dirPath + "/WcTestSimpleSecond.txt");

    @BeforeAll
    public static void checkFiles() throws IOException {
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        if (!Files.exists(fileSimple)) {
            Files.createFile(fileSimple);
            try (var writer = new FileWriter(fileSimple.toFile())) {
                writer.write("Hello   Simple    \n");
            }
        }
        if (!Files.exists(fileSimpleSecond)) {
            Files.createFile(fileSimpleSecond);
            try (var writer = new FileWriter(fileSimpleSecond.toFile())) {
                writer.write("    Hello Second\n Simple");
            }
        }
    }

    @Test
    public void testSimple() throws IOException, InterruptedException {
        var is = new PipedInputStream();
        var os = new PipedOutputStream();
        os.connect(is);
        var wc = new Wc(List.of(fileSimple.toString()));
        wc.setOutputStream(os);
        var t = new Thread(wc);
        t.start();
        int BUF_SIZE = 100;
        var buf = new byte[BUF_SIZE];
        int res = is.read(buf);
        int off = res;
        while (res != -1) {
            res = is.read(buf, off, BUF_SIZE - off);
            off += res;
        }
        t.join();
        var str = new String(Arrays.copyOfRange(buf, 0, off + 1), StandardCharsets.UTF_8);
        String checkString = String.format("2 2 19 %s\n", fileSimple);
        assertEquals(checkString, str);
    }

    @Test
    public void testSimilar() throws IOException, InterruptedException {
        var is = new PipedInputStream();
        var os = new PipedOutputStream();
        os.connect(is);
        var wc = new Wc(List.of(fileSimple.toString(), fileSimpleSecond.toString()));
        wc.setOutputStream(os);
        var t = new Thread(wc);
        t.start();
        int BUF_SIZE = 1024;
        var buf = new byte[BUF_SIZE];
        int res = is.read(buf);
        int off = res;
        while (res != -1) {
            res = is.read(buf, off, BUF_SIZE - off);
            off += res;
        }
        t.join();
        var str = new String(Arrays.copyOfRange(buf, 0, off + 1), StandardCharsets.UTF_8);
        String checkString = String.format("""
            2 2 19 %s
            2 3 24 %s
            4 5 43 total
            """, fileSimple, fileSimpleSecond);
        assertEquals(checkString, str);
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
        int BUF_SIZE = 1024;
        var buf = new byte[BUF_SIZE];
        int res = toRead.read(buf);
        int off = res;
        while (res != -1) {
            res = toRead.read(buf, off, BUF_SIZE - off);
            off += res;
        }
        writer.join();
        worker.join();
        var str = new String(Arrays.copyOfRange(buf, 0, off + 1), StandardCharsets.UTF_8);
        assertEquals("2 4 24\n", str);
    }
}
