package org.hw1.commands;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

public interface Command extends Runnable {
   void setInputStream(PipedInputStream inputStream);
   void setOutputStream(PipedOutputStream outputStream);
   void setErrorStream(PrintStream errorStream);
}
