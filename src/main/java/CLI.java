import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CLI {
    Lexer lexer;
    Parser parser;
    CmdManager cmdManager;

    public static void main(String[] args) throws Exception {
        while (true) {
            // need input stream
            // System.in.read();

            // give it to lexer
            // Lexer.getTokens();

            // pass tokens to parser
            // Parser.getCommandsFromTokens();
            // + detect exit()

            // pass commands to cmdManager
            // OutputStream out = CmdManager.startPipline();
            // getPipeOutputStream to System.out.print();
        }
    }

}
