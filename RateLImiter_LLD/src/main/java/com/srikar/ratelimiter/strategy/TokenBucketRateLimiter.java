package com.srikar.ratelimiter.strategy;

import com.srikar.ratelimiter.RateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketRateLimiter implements RateLimiter {
    private static TokenBucketRateLimiter instance;
    private final Integer maxRequests;
    private final Double refillRate;
    private final Map<String, Bucket> userBuckets;
    private TokenBucketRateLimiter() {
        this.maxRequests = 3;
        this.refillRate = 3.7;
        this.userBuckets = new ConcurrentHashMap<>();
    }

    private static class Bucket {
        private Integer tokens;
        private Long lastRefillTime;
        public Bucket(Integer maxRequests) {
            this.tokens = maxRequests;
            this.lastRefillTime = System.currentTimeMillis();
        }
    }
    public static TokenBucketRateLimiter getInstance() {
        if (instance == null) {
            synchronized (TokenBucketRateLimiter.class) {
                if (instance == null) {
                    instance = new TokenBucketRateLimiter();
                }
            }
        }
        return instance;
    }
    @Override
    public boolean allowRequest(String clientId) {
        long currentTime = System.currentTimeMillis();
        userBuckets.putIfAbsent(clientId, new Bucket(maxRequests));
        Bucket bucket = userBuckets.get(clientId);

        synchronized (bucket) {
            long tokensToAdd = (long) ((double) (currentTime - bucket.lastRefillTime) / 1000 * refillRate);
            bucket.tokens = Math.min(maxRequests, bucket.tokens + (int) tokensToAdd);
            bucket.lastRefillTime = currentTime;
            if (bucket.tokens > 0) {
                bucket.tokens--;
                return true;
            }
        }
        return false;
    }
}
