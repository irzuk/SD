package org.hw1;

import org.hw1.commands.Cat;
import org.hw1.commands.Echo;
import org.hw1.commands.Pwd;
import org.hw1.commands.Wc;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

public class CLI {
    private static final CLILogger LOG = new CLILogger("CLI");
    private static InputStream input = System.in;
    private static PrintStream output = System.out;

    private static boolean inTestMode = false;

    public static void isInTestMode(boolean flag) {
        inTestMode = flag;
    }

    private static void printPrompt(PrintStream os) {
        if (!inTestMode) {
            os.print("user@machine:/cur/dir$ ");
        }
    }

    public static void setInput(InputStream is) {
        input = is;
    }

    public static void setOutput(PrintStream os) {
        output = os;
    }

    private static void registerAllCommands(Parser p) {
        p.registerCommand("cat", Cat.class);
        p.registerCommand("echo", Echo.class);
        p.registerCommand("wc", Wc.class);
        p.registerCommand("pwd", Pwd.class);
    }

    public static void main(String[] args) throws Exception {

        Lexer l = new Lexer(input);
        Parser p = new Parser();
        registerAllCommands(p);

        while (true) {
            printPrompt(output);
            // get tokens from InputStream
            List<Token> resp = l.getTokens();
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
            InputStream finalInputStream = CmdManager.startPipeline(cmds);
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
