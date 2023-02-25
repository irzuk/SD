package org.hw1;

import java.io.InputStream;
import java.text.ParseException;
import java.util.*;
import java.util.function.Predicate;

public class Lexer {
    private final Scanner scanner;
    private static final Predicate<Character> shouldSkip = x -> x == ' ' || x == '\t' || x == '|';
    char[] quote_kinds = {'"', '\''};

    public Lexer(InputStream is) {
        // TODO(mkornaukov03)
        // * Support multiline
        this.scanner = new Scanner(is);
    }

    public List<Token> getTokens() throws ParseException {
        List<String> rawResult = new ArrayList<>();
        String cur_line = scanner.nextLine().strip();
        main_loop:
        for (int pos = 0; pos < cur_line.length(); ) {
            if (cur_line.charAt(pos) == '|') {
                pos++;
                rawResult.add("|");
                continue;
            }
            while (pos < cur_line.length() && cur_line.charAt(pos) != '|' && shouldSkip.test(cur_line.charAt(pos))) {
                pos++;
            }
            if (cur_line.charAt(pos) == '|') {
                pos++;
                rawResult.add("|");
                continue;
            }
            if (pos == cur_line.length()) {
                break;
            }
            for (char quote_kind : quote_kinds) {
                if (cur_line.charAt(pos) == quote_kind) {
                    int start_token = pos;
                    pos++;
                    while (pos < cur_line.length() && cur_line.charAt(pos) != quote_kind) {
                        pos++;
                    }
                    if (pos == cur_line.length()) {
                        throw new ParseException("Unpaired " + quote_kind, start_token);
                    }
                    pos++;

                    rawResult.add(cur_line.substring(start_token, pos));
                    continue main_loop;
                }
            }
            int start_token = pos;
            while (pos < cur_line.length() && !shouldSkip.test(cur_line.charAt(pos))) {
                pos++;
            }
            rawResult.add(cur_line.substring(start_token, pos));
            if (pos != cur_line.length() && cur_line.charAt(pos) != '|') {
                pos++;
            }
        }
        List<Token> result = new ArrayList<>();
        for (var data : rawResult) {
            if (data.equals("|")) {
                result.add(new Token(TokenType.Pipe, data));
            }
            else if (data.endsWith("\"")) {
                result.add(new Token(TokenType.DoubleQuotes, data));
            }
            else if (data.endsWith("\'")) {
                result.add(new Token(TokenType.SingleQuotes, data));
            }
            else if (data.contains("=")) {
                result.add(new Token(TokenType.EnvVarDef, data));
            }
            else if (data.contains("$")) {
                result.add(new Token(TokenType.EnvVarUse, data));
            }
            else {
                result.add(new Token(TokenType.Name, data));
            }

        }
        return result;
    }

}
