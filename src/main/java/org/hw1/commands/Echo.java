package org.hw1.commands;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Echo implements Command{
    private static final Logger LOG = Logger.getLogger("Echo");
    @NotNull
    private final List<String> args;
    private PipedOutputStream os;

    public Echo(@NotNull List<@NotNull String> args) {
        this.args = args;
    }

    @SuppressWarnings("unused")
    public Echo(String[] parametrs) { // Need for parser
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
                LOG.warning(String.format("Can't write %s\n", str));
            }
        }
        try {
            os.write("\n".getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
        } catch (IOException e) {
            LOG.warning("Echo: can't stop correctly");
        }
    }
}
