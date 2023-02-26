package org.hw1;

import org.hw1.commands.Command;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Parser {
    private final HashMap<String, Class> registeredCommands;
    private final HashMap<String, String> envVars;

    public Parser() {
        this.registeredCommands = new HashMap<>();
        this.envVars = new HashMap<>();
    }

    public List<Command> parse(List<Token> tokens) throws RuntimeException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        /*
         * Algorithm:
         * 1. Fill `envVars` table
         * 2. Replace each `EnvVarUse` token according to `envVars` table
         * 3. Replace each use of env. vars in `DoubleQuotes` token according to `envVars` table (if needed)
         * 4. Omit quotes
         * 5. Separate by `Pipe` token
         * 6. Form commands
         * */

        // Idea: omit double quotes, mark these tokens as `EnvVarUse`before step 2-4

        tokens = tokens.stream().peek(token -> {
                    if (token.type == TokenType.EnvVarDef) {
                        System.out.println("HERE!!!");
                        String data = token.data;
                        if (!data.contains("=")) {
                            throw new RuntimeException(new ParseException("Invalid env. var definition", -1));
                        }
                        String[] kv = data.split("=");
                        envVars.put(kv[0], kv[1]);
                    }
                }).filter(token -> token.type != TokenType.EnvVarDef)
                .map(token -> {
                    if (token.type == TokenType.DoubleQuotes) {
                        String data = token.data.substring(1, token.data.length() - 1);
                        return new Token(TokenType.Name, data);
                    }
                    return token;
                })
                .map(token -> {
                    if (token.type == TokenType.EnvVarUse) {
                        String oldData = token.data;
                        StringBuilder newData = new StringBuilder();
                        for (int startKey = 0; startKey < oldData.length(); ) {
                            while (startKey < oldData.length() && oldData.charAt(startKey) != '$') {
                                newData.append(oldData.charAt(startKey));
                                startKey++;
                            }
                            if (oldData.charAt(startKey) == '$') {
                                startKey++;
                                int endKey = startKey;
                                while (endKey < oldData.length() && oldData.charAt(endKey) != '$') {
                                    endKey++;
                                }
                                String val = this.envVars.get(oldData.substring(startKey, endKey));
                                if (val == null) {
                                    val = "";
                                }
                                newData.append(val);
                                startKey = endKey;
                            }
                        }

                        return new Token(TokenType.Name, newData.toString());
                    }
                    if (token.type == TokenType.SingleQuotes) {
                        return new Token(TokenType.Name, token.data.substring(1, token.data.length() - 1));
                    }
                    return token;
                }).toList();

        if (tokens.size() == 1 && tokens.get(0).type == TokenType.Name && tokens.get(0).data.strip().equals("exit")) {
            return null; // End of session
        }
        // Last step of algorithm
        List<List<String>> strResult = new ArrayList<>();
        strResult.add(new ArrayList<>());
        for (var token : tokens) {
            if (token.type == TokenType.Pipe) {
                strResult.add(new ArrayList<>());
            } else {
                strResult.get(strResult.size() - 1).add(token.data);
            }
        }

        List<Command> result = new ArrayList<>();
        for (var cmdAndParams : strResult) {
            Class cmd = this.registeredCommands.get(cmdAndParams.get(0));
            /*
             * TODO
             * if cmd == null then create Exec command
             * */
            assert (cmd != null);
            cmdAndParams.remove(0);
            String[] asArr = cmdAndParams.toArray(new String[0]);
            result.add((Command) cmd.getConstructor(String[].class).newInstance(new Object[]{asArr}));
        }


        return result;
    }

    // TODO restrict clazz to be an implementation of `Command` (is it possible?)
    public void registerCommand(String key, Class clazz) {
        this.registeredCommands.put(key, clazz);
    }
}
