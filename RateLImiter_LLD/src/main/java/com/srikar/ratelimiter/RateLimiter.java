package com.srikar.ratelimiter;

public interface RateLimiter {
    /**
     * Check if the request is allowed or not
     * @param clientId
     * @return
     */
    public boolean allowRequest(String clientId);
}