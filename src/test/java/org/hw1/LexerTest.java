package org.hw1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LexerTest {

    // TODO(mkornaukov03) remove boilerplate

    public static void checkOne(String input, List<Token> expected) throws Throwable {
        Lexer l = new Lexer(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        var resp = l.getTokens();
        Assertions.assertEquals(expected, resp);
    }

    @Test
    public void testEmpty() throws Throwable {
        checkOne("\n", List.of());
    }

    @Test
    public void testBasic() throws Throwable {
        checkOne("echo 42\n", List.of(new Token(TokenType.Name, "echo"), new Token(TokenType.Name, "42")));
    }

    @Test
    public void testQuotes() throws Throwable {
        checkOne("echo \"42\"\n", List.of(new Token(TokenType.Name, "echo"), new Token(TokenType.DoubleQuotes, "\"42\"")));
        checkOne("echo '42'\n", List.of(new Token(TokenType.Name, "echo"), new Token(TokenType.SingleQuotes, "'42'")));
        checkOne("echo \"kek'42'lol\"\n", List.of(new Token(TokenType.Name, "echo"), new Token(TokenType.DoubleQuotes, "\"kek'42'lol\"")));
    }

    @Test
    public void testEnvVar() throws Throwable {
        checkOne("FILE=file.txt cat $FILE\n", List.of(new Token(TokenType.EnvVarDef, "FILE=file.txt"), new Token(TokenType.Name, "cat"), new Token(TokenType.EnvVarUse, "$FILE")));
    }

    @Test
    public void testPipe() throws Throwable {
        checkOne("pwd| pwd\n", List.of(new Token(TokenType.Name, "pwd"), new Token(TokenType.Pipe, "|"), new Token(TokenType.Name, "pwd")));
        checkOne("pwd | pwd\n", List.of(new Token(TokenType.Name, "pwd"), new Token(TokenType.Pipe, "|"), new Token(TokenType.Name, "pwd")));
        checkOne("pwd |pwd\n", List.of(new Token(TokenType.Name, "pwd"), new Token(TokenType.Pipe, "|"), new Token(TokenType.Name, "pwd")));
        checkOne("echo '42'|pwd\n", List.of(new Token(TokenType.Name, "echo"), new Token(TokenType.SingleQuotes, "'42'"), new Token(TokenType.Pipe, "|"), new Token(TokenType.Name, "pwd")));
    }

    @Test
    public void testComplex() throws Throwable {
        checkOne("FILE=file.txt x=pwd echo \"42\"| wc | cat $file | $x",
                List.of(
                        new Token(TokenType.EnvVarDef, "FILE=file.txt"),
                        new Token(TokenType.EnvVarDef, "x=pwd"),
                        new Token(TokenType.Name, "echo"),
                        new Token(TokenType.DoubleQuotes, "\"42\""),
                        new Token(TokenType.Pipe, "|"),
                        new Token(TokenType.Name, "wc"),
                        new Token(TokenType.Pipe, "|"),
                        new Token(TokenType.Name, "cat"),
                        new Token(TokenType.EnvVarUse, "$file"),
                        new Token(TokenType.Pipe, "|"),
                        new Token(TokenType.EnvVarUse, "$x")
                ));
    }

    @Test
    public void testSpaces() throws Throwable {
        checkOne("echo       '42'   | \t \t \tpwd\n", List.of(
                new Token(TokenType.Name, "echo"),
                new Token(TokenType.SingleQuotes, "'42'"),
                new Token(TokenType.Pipe, "|"),
                new Token(TokenType.Name, "pwd")));

    }

    @Test
    public void testQuotedEnvVar() throws Throwable {
        checkOne("VAR=\"KEK LOL\"\n", List.of(
                new Token(TokenType.EnvVarDef, "VAR=\"KEK LOL\"")
        ));
        checkOne("VAR='KEK LOL'\n", List.of(
                new Token(TokenType.EnvVarDef, "VAR='KEK LOL'")
        ));
    }

}