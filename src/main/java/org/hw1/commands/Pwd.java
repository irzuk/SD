package org.hw1.commands;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;

public class Pwd implements Command {
    private PipedOutputStream os;

    @Override
    public void setInputStream(PipedInputStream inputStream) {

        //throw new UnsupportedOperationException();
    }

    public Pwd(String[] parametrs) {
    }

    public Pwd() {
    }

    @Override
    public void setOutputStream(PipedOutputStream outputStream) {
        os = outputStream;
    }

    @Override
    public void run() {
        var path = System.getProperty("user.dir");
        try {
            os.write(path.getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            System.out.println("Can't write path");
        }
        try {
            os.close();
        } catch (IOException e) {
            System.out.println("Pwd can't stop correctly");
        }
    }
}
