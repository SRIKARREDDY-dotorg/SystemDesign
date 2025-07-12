package com.srikar.log;

public class ConsoleAppender implements ILogAppender {
    private final ILogFormatter formatter;

    public ConsoleAppender(ILogFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void append(Log log) {
        System.out.println(formatter.format(log));
    }
}
