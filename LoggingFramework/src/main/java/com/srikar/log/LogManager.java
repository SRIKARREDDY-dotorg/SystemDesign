package com.srikar.log;

import java.util.ArrayList;
import java.util.List;

public class LogManager {
    public static volatile Logger logger;

    public static Logger getLogger() {
        if(logger == null) {
            synchronized (LogManager.class) {
                if(logger == null) {
                    logger = new LoggerBuilder()
                                .setLogLevel(LogLevel.DEBUG)
                                .addAppender(new ConsoleAppender(new SimpleFormatter()))
                                .addAppender(new FileAppender("log.txt", new SimpleFormatter()))
                                .build();
                }
            }
        }
        return logger;
    }

    public static class LoggerBuilder {
        private LogLevel logLevel = LogLevel.INFO;
        private final List<ILogAppender> appenders = new ArrayList<>();

        public LoggerBuilder setLogLevel(LogLevel logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public LoggerBuilder addAppender(ILogAppender appender) {
            appenders.add(appender);
            return this;
        }

        public Logger build() {
            return new Logger(logLevel, appenders);
        }
    }
}
