package com.srikar.log;

import java.io.FileWriter;
import java.io.IOException;

public class FileAppender implements ILogAppender {
    private FileWriter writer;
    private final ILogFormatter formatter;

    public FileAppender(String filePath, ILogFormatter formatter) {
        this.formatter = formatter;
        try {
            writer = new FileWriter(filePath, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void append(Log log) {
        try {
            writer.write(formatter.format(log) + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Failed to write logs to the file, exception: " + e.getMessage());
        }
    }
}
