package org.hw1;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CLILogger {
    private static FileHandler fh = null;

    static {
        try {
            fh = new FileHandler("src/main/resources/CLI_LOG.log");
        } catch (SecurityException | IOException e) {
            System.err.println("Can't initialize Logger");
        }
    }

    private final Logger logger;

    public CLILogger(String name) {
        if (fh != null) {
            logger = Logger.getLogger(name);
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } else {
            logger = null;
        }
    }

    public void info(String message) {
        logger.info(message);
    }

    public void warning(String message) {
        logger.warning(message);
    }
}
