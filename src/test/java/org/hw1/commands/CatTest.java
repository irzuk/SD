package org.hw1.commands;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hw1.commands.CommandsTestUtils.checkFileAndWrite;
import static org.hw1.commands.CommandsTestUtils.readAllSmall;
import static org.junit.jupiter.api.Assertions.*;

public class CatTest {
    private static final Path dirPath = Paths.get("src/test/resources/commands/cat");
    private static final Path fileSimple = Paths.get(dirPath + "/CatTestSimple.txt");
    private static final Path fileSimpleSecond = Paths.get(dirPath + "/CatTestSimpleSecond.txt");
    private static final Path fileBig = Paths.get(dirPath + "/CatTestBig.txt");
    private static final int FILE_BIG_SIZE = 30000;

    // creating files to avoid encoding/decoding problems
    @BeforeAll
    public static void checkFiles() throws IOException {
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        checkFileAndWrite(fileSimple, "It's a simple test\nIt must work");
        checkFileAndWrite(fileSimpleSecond, "It's a second simple test\nIt must work too");
        if (!Files.exists(fileBig)) {
            Files.createFile(fileBig);
            var buf = new byte[FILE_BIG_SIZE];
            for (int i = 0; i < FILE_BIG_SIZE; ++i) {
                buf[i] = (byte) (i % 10);
            }
            Files.write(fileBig, buf);
        }
    }

    @Test
    public void testSimpleCatFile() throws IOException {
        var is = new PipedInputStream();
        var os = new PipedOutputStream(is);
        var cat = new Cat(List.of(fileSimple.toString()));
        cat.setOutputStream(os);
        cat.run();
        var str = readAllSmall(is);
        assertEquals("It's a simple test\nIt must work\n", str);
    }

    @Test
    public void testSimilarCatFile() throws IOException {
        var is = new PipedInputStream();
        var os = new PipedOutputStream(is);
        var cat = new Cat(List.of(fileSimple.toString(), fileSimpleSecond.toString()));
        cat.setOutputStream(os);
        cat.run();
        var str = readAllSmall(is);
        assertEquals("It's a simple test\nIt must work\nIt's a second simple test\nIt must work too\n", str);
    }

    @Test
    public void testBigCatFile() {
        var bufRes = new byte[FILE_BIG_SIZE + 1];
        for (int i = 0; i < FILE_BIG_SIZE; ++i) {
            bufRes[i] = (byte) (i % 10);
        }
        bufRes[FILE_BIG_SIZE] = 10; // '\n'
        try {
            var is = new PipedInputStream();
            var os = new PipedOutputStream(is);
            var cat = new Cat(List.of(fileBig.toString()));
            cat.setOutputStream(os);
            var worker = new Thread(cat);
            worker.start();
            var buf = new byte[1024];
            int res = is.read(buf);
            var off = 0;
            while (res != -1) {
                for (int i = 0; i < res; ++i) {
                    assertEquals(bufRes[off + i], buf[i]);
                }
                off += res;
                res = is.read(buf);
            }
            assertEquals(FILE_BIG_SIZE + 1, off);
            worker.join();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testCatInputStream() throws IOException, InterruptedException {
        var toCatRead = new PipedInputStream();
        var toWrite = new PipedOutputStream(toCatRead);
        var toRead = new PipedInputStream();
        var toCatWrite = new PipedOutputStream(toRead);
        var cat = new Cat();
        cat.setInputStream(toCatRead);
        cat.setOutputStream(toCatWrite);
        var writer = new Thread(() -> {
            try {
                toWrite.write("Hello cat!\n".getBytes(StandardCharsets.UTF_8));
                toWrite.flush();
                toWrite.write("Hello dog...\n".getBytes(StandardCharsets.UTF_8));
                toWrite.flush();
                toWrite.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        var worker = new Thread(cat);
        writer.start();
        worker.start();
        var str = readAllSmall(toRead);
        assertEquals("Hello cat!\nHello dog...\n", str);
        writer.join();
        worker.join();
    }
}
