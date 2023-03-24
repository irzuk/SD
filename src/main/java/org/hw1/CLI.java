package org.hw1;

import org.hw1.commands.Cat;
import org.hw1.commands.Echo;
import org.hw1.commands.Pwd;
import org.hw1.commands.Wc;
import org.jetbrains.annotations.TestOnly;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CLI {
    private static final CLILogger LOG = new CLILogger("CLI");
    private static InputStream input = System.in;
    private static PrintStream output = System.out;
    private static PrintStream errStream = System.err;
    private static final String prompt;

    static {
        try {
            prompt = String.format("%s@%s:%s$ ", System.getProperty("user.name"),
                InetAddress.getLocalHost().getHostName(),
                System.getProperty("user.dir"));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        CmdManager.setErrStream(errStream);
    }

    private static boolean inTestMode = false;

    @TestOnly
    public static void setTestMode(boolean flag) {
        inTestMode = flag;
    }

    private static void printPrompt(PrintStream os) {
        if (!inTestMode) {
            os.print(prompt);
        }
    }

    @TestOnly
    public static void setInput(InputStream is) {
        input = is;
    }

    @TestOnly
    public static void setOutput(PrintStream os) {
        output = os;
    }

    @TestOnly
    public static void setErrStream(PrintStream errStream) {
        CLI.errStream = errStream;
        CmdManager.setErrStream(errStream);
    }

    private static void registerAllCommands(Parser p) {
        p.registerCommand("cat", Cat.class);
        p.registerCommand("echo", Echo.class);
        p.registerCommand("wc", Wc.class);
        p.registerCommand("pwd", Pwd.class);
    }

    public static void main(String[] args) throws Exception {

        var l = new Lexer(input);
        var p = new Parser();
        registerAllCommands(p);

        while (true) {
            printPrompt(output);
            // get tokens from InputStream
            var resp = l.getTokens();
            LOG.info("Get cmd:");
            for (var item : resp) {
                LOG.info(item.toString());
            }
            LOG.info("-- end of cmd");

            // parse tokens
            var cmds = p.parse(resp);
            if (cmds == null) {
                LOG.info("Got exit command");
                CmdManager.shutDown();
                return;
            }

            if (inTestMode) {
                CmdManager.startThreadPool();
            }

            // execute commands in CmdManager
            var finalInputStream = CmdManager.startPipeline(cmds);
            if (finalInputStream != null) { // in case of empty pipeline
                finalInputStream.transferTo(output);
            }
            if (!inTestMode) {
                output.println();
            }
            LOG.info("===== End of Line =====");
        }
    }
}
