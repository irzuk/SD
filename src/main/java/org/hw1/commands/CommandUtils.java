package org.hw1.commands;

import org.hw1.CLILogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.PrintStream;

public class CommandUtils {
    public static void processException(@NotNull PrintStream errS,
                                  @NotNull CLILogger LOG,
                                  @NotNull Exception e,
                                  @NotNull String commandName,
                                  @Nullable String mes) {
        errS.print(mes);
        LOG.error(String.format("%s exception %s", commandName, e.getMessage()));
    }
}
