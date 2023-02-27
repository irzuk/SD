package org.hw1;

import org.hw1.commands.Cat;
import org.hw1.commands.Echo;
import org.hw1.commands.Pwd;
import org.hw1.commands.Wc;

import java.io.*;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CLI {
    Lexer lexer;
    Parser parser;
    CmdManager cmdManager;


    private static void printPrompt(PrintStream os) {
        //os.print("user@machine:/cur/dir$ ");
    }

    private static InputStream input = System.in;
    private static PrintStream output = System.out;

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
        Logger logger = Logger.getLogger("CLILog");
        FileHandler fh;
        try {

            fh = new FileHandler("src/main/resources/CLILog.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Lexer l = new Lexer(input);
        // while (true) {

        printPrompt(output);
        // get tokens from InputStream
        List<Token> resp = l.getTokens();
        logger.info("Get cmd:");
        for (var item : resp) {
            logger.info(item.toString());
        }
        logger.info("-- end of cmd");

        Parser p = new Parser();
        registerAllCommands(p);

        // parse tokens
        var cmds = p.parse(resp);

        // execute commands in CmdManager
        InputStream finalInputStream = CmdManager.startPipeline(cmds);
        finalInputStream.transferTo(output);

        //  }

    }

}
