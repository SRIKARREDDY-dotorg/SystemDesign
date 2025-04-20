package com.srikar.ratelimiter.strategy;

import com.srikar.ratelimiter.RateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowRateLimiter implements RateLimiter {
    private static FixedWindowRateLimiter instance;
    private final Integer maxRequests;
    private final Long windowSizeInMillis;
    private final Map<String, Window> userWindows;

    private static class Window {
        private Integer requestCount;
        private Long startTimeInMillis;
        public Window() {
            this.requestCount = 0;
            this.startTimeInMillis = System.currentTimeMillis();
        }
    }
    private FixedWindowRateLimiter() {
        this.maxRequests = 3;
        this.windowSizeInMillis = 1000L;
        this.userWindows = new ConcurrentHashMap<>();
    }

    public static FixedWindowRateLimiter getInstance() {
        if (instance == null) {
            synchronized (FixedWindowRateLimiter.class) {
                if (instance == null) {
                    instance = new FixedWindowRateLimiter();
                }
            }
        }
        return instance;
    }
    @Override
    public boolean allowRequest(String clientId) {
        Long currentTime = System.currentTimeMillis();
        userWindows.putIfAbsent(clientId, new Window());
        Window window = userWindows.get(clientId);

        synchronized (window) {
            if (currentTime - window.startTimeInMillis > windowSizeInMillis) {
                window.requestCount = 0;
                window.startTimeInMillis = currentTime;
            }
            if (window.requestCount >= maxRequests) {
                return false;
            }
            window.requestCount++;
            return true;
        }
    }
}
