package com.srikar.log;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Logger {
    private LogLevel minLevel;
    private final List<ILogAppender> appenders;
    private final ExecutorService executorService;

    public Logger(LogLevel minLevel, List<ILogAppender> appenders) {
        this.minLevel = minLevel;
        this.appenders = appenders;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void log(LogLevel logLevel, String message) {
        if(!logLevel.isAsSevereAs(minLevel)) return;
        Log log = new Log(message, logLevel);
        executorService.submit(() -> appenders.forEach(appender -> appender.append(log)));
    }

    public void setMinLevel(LogLevel minLevel) {
        this.minLevel = minLevel;
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void fatal(String message) {
        log(LogLevel.FATAL, message);
    }
}
