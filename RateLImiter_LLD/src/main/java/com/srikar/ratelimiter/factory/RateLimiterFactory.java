package com.srikar.ratelimiter.factory;

import com.srikar.ratelimiter.RateLimiter;
import com.srikar.ratelimiter.Strategy;
import com.srikar.ratelimiter.strategy.FixedWindowRateLimiter;
import com.srikar.ratelimiter.strategy.SlidingWindowRateLimiter;
import com.srikar.ratelimiter.strategy.TokenBucketRateLimiter;

public class RateLimiterFactory {
    public static RateLimiter getRateLimiter(Strategy strategy) {
        switch (strategy) {
            case SLIDING_WINDOW:
                return SlidingWindowRateLimiter.getInstance();
            case FIXED_WINDOW:
                return FixedWindowRateLimiter.getInstance();
            case TOKEN_BUCKET:
                return TokenBucketRateLimiter.getInstance();
            default:
                throw new IllegalArgumentException("Invalid strategy");
        }
    }
}
