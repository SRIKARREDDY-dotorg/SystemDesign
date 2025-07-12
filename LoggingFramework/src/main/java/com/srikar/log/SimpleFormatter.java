package com.srikar.log;

public class SimpleFormatter implements ILogFormatter {
    @Override
    public String format(Log log) {
        return String.format("[%s] [%s] [%s]: %s", log.getTimestamp(), log.getLogLevel(), log.getThreadName(), log.getMessage());
    }
}
