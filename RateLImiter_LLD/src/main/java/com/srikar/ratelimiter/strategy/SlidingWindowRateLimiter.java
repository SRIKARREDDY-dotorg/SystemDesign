package com.srikar.ratelimiter.strategy;

import com.srikar.ratelimiter.RateLimiter;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowRateLimiter implements RateLimiter {
    private static SlidingWindowRateLimiter instance;
    private final Integer maxRequests;
    private final Long windowSizeInMillis;
    private final Map<String, Deque<Long>> userRequests;

    private SlidingWindowRateLimiter() {
        this.maxRequests = 3;
        this.windowSizeInMillis = 1000L;
        this.userRequests = new ConcurrentHashMap<>();
    }
    public static SlidingWindowRateLimiter getInstance() {
        if (instance == null) {
            synchronized (SlidingWindowRateLimiter.class) {
                if (instance == null) {
                    instance = new SlidingWindowRateLimiter();
                }
            }
        }
        return instance;
    }
    @Override
    public boolean allowRequest(String clientId) {
        Long currentTime = System.currentTimeMillis();
        userRequests.putIfAbsent(clientId, new LinkedList<>());
        Deque<Long> requests = userRequests.get(clientId);

        synchronized (requests) {
            while (!requests.isEmpty() && currentTime - requests.peekFirst() > windowSizeInMillis) {
                requests.pollFirst();
            }
            if (requests.size() >= maxRequests) {
                return false;
            }
            requests.addLast(currentTime);
            return true;
        }
    }
}
