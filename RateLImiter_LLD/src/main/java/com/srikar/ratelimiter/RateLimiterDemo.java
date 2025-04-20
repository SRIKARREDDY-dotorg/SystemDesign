package com.srikar.ratelimiter;

import com.srikar.ratelimiter.factory.RateLimiterFactory;

public class RateLimiterDemo {
    public static void main(String[] args) throws InterruptedException {
        RateLimiter rateLimiter = RateLimiterFactory.getRateLimiter(Strategy.SLIDING_WINDOW);
        for (int i = 0; i < 10; i++) {
            System.out.println(rateLimiter.allowRequest("client1"));
            Thread.sleep(300);
        }
        System.out.println("====================================");
        rateLimiter = RateLimiterFactory.getRateLimiter(Strategy.FIXED_WINDOW);
        for (int i = 0; i < 10; i++) {
            System.out.println(rateLimiter.allowRequest("client1"));
            Thread.sleep(300);
        }
        System.out.println("====================================");
        rateLimiter = RateLimiterFactory.getRateLimiter(Strategy.TOKEN_BUCKET);
        for (int i = 0; i < 10; i++) {
            System.out.println(rateLimiter.allowRequest("client1"));
            Thread.sleep(300);
        }
    }
}
