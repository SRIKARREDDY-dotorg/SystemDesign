package com.srikar.log;

public class Log {
    private final String message;
    private final Long timestamp;
    private final LogLevel logLevel;
    private final String threadName;

    public Log(String message, LogLevel logLevel) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.logLevel = logLevel;
        this.threadName = Thread.currentThread().getName();
    }

    public String getMessage() {
        return message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public String getThreadName() {
        return threadName;
    }

    @Override
    public String toString() {
        return "[" + logLevel + "] " + timestamp + " - " + message;
    }
}
