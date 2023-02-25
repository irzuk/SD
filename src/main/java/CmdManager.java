import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import Commands.*;

public class CmdManager {
    // private static ThreadPoolExecutor executor; TODO 2 phase

    static OutputStream startPipline(List<Command> commands) throws IOException {
        PipedInputStream previous;
        for (Command command : commands) {

            PipedOutputStream out = new PipedOutputStream();
            PipedInputStream in = new PipedInputStream(out);

            command.setInputStream();
            command.setOutputStream();
            Thread executor = new Thread(command);
            executor.start();

        }
        return null;
    }
}
