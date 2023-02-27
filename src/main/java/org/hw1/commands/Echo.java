package org.hw1.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Echo implements Command{
    @NotNull
    private final List<String> args;
    private PipedOutputStream os;

    public Echo(@NotNull List<@NotNull String> args) {
        this.args = args;
    }

    public Echo(String[] parametrs) {
        this.args = Arrays.asList(parametrs);
    }


    @Override
    public void setInputStream(PipedInputStream inputStream) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setOutputStream(PipedOutputStream outputStream) {
        os = outputStream;
    }

    @Override
    public void run() {
        assert os != null;
        for (var str: args) {
            try {
                os.write(str.getBytes(StandardCharsets.UTF_8));
                os.flush();
            } catch (IOException e) {
                System.out.printf("Can't write %s\n", str);
            }
        }
        try {
            os.close();
        } catch (IOException e) {
            System.out.println("Echo can't stop correctly");
        }
    }
}
