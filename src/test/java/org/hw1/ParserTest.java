package org.hw1;

import org.hw1.commands.Cat;
import org.hw1.commands.Echo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ParserTest {

    @Test
    public void testExitSimple() throws Throwable {
        Parser p = new Parser();
        List<Token> tokens = List.of(new Token(TokenType.Name, "exit"));
        Assertions.assertNull(p.parse(tokens));
    }

    @Test
    public void testExitEnvVars() throws Throwable {
        Lexer l = new Lexer(new ByteArrayInputStream("x=ex y=it $x$y\n".getBytes(StandardCharsets.UTF_8)));
        var tokens = l.getTokens();
        Parser p = new Parser();
        Assertions.assertNull(p.parse(tokens));
    }

    @Test
    public void testCatNoParams() throws Throwable {
        Lexer l = new Lexer(new ByteArrayInputStream("cat\n".getBytes(StandardCharsets.UTF_8)));
        var tokens = l.getTokens();
        Parser p = new Parser();
        p.registerCommand("cat", Cat.class);
        var cmds = p.parse(tokens);
        Assertions.assertTrue(cmds.size() == 1);
        Assertions.assertInstanceOf(Cat.class, cmds.get(0));
    }

    @Test
    public void testCatWithParams() throws Throwable {
        Lexer l = new Lexer(new ByteArrayInputStream("cat file.txt file2.txt\n".getBytes(StandardCharsets.UTF_8)));
        var tokens = l.getTokens();
        Parser p = new Parser();
        p.registerCommand("cat", Cat.class);
        var cmds = p.parse(tokens);
        Assertions.assertTrue(cmds.size() == 1);
        Assertions.assertInstanceOf(Cat.class, cmds.get(0));
    }

    @Test
    public void testCatPipes() throws Throwable {
        Lexer l = new Lexer(new ByteArrayInputStream("cat file.txt file2.txt | cat another.txt\n".getBytes(StandardCharsets.UTF_8)));
        var tokens = l.getTokens();
        Parser p = new Parser();
        p.registerCommand("cat", Cat.class);
        var cmds = p.parse(tokens);
        Assertions.assertTrue(cmds.size() == 2);
        Assertions.assertInstanceOf(Cat.class, cmds.get(0));
        Assertions.assertInstanceOf(Cat.class, cmds.get(1));
    }

    @Test
    public void testQuotedEnvVar() throws Throwable {
        Lexer l = new Lexer(new ByteArrayInputStream("FILE=\"KEK LOL\" echo $FILE\n".getBytes(StandardCharsets.UTF_8)));
        var tokens = l.getTokens();
        Parser p = new Parser();
        p.registerCommand("cat", Cat.class);
        p.registerCommand("echo", Echo.class);
        var cmds = p.parse(tokens);
        Assertions.assertTrue(cmds.size() == 1);
        Assertions.assertInstanceOf(Echo.class, cmds.get(0));
    }

}