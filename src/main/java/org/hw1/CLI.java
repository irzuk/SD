package org.hw1;

import java.io.*;
import java.util.*;

public class CLI {
    Lexer lexer;
    Parser parser;
    CmdManager cmdManager;


    private static void printPrompt(PrintStream os) {
        os.print("user@machine:/cur/dir$ ");
    }

    public static void main(String[] args) throws Exception {
        PrintStream out = System.out;
        InputStream in = System.in;
        Lexer l = new Lexer(System.in);
        while (true) {
            printPrompt(out);
            List<Token> resp = l.getTokens();
            out.println("Get cmd:");
            for (var item : resp) {
                out.println(item);
            }
            out.println("-- end of cmd");
//            out.println("");
//             give it to lexer
//             org.hw1.Lexer.getTokens();
//
//             pass tokens to parser
//             org.hw1.Parser.getCommandsFromTokens();
//             + detect exit()
//
//             pass commands to cmdManager
//             OutputStream out = org.hw1.CmdManager.startPipline();
//             getPipeOutputStream to System.out.print();
        }

    }

}
