package com.buildmuse.concurrency.level7_patterns;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * PROBLEM: Token Bucket Rate Limiter
 *
 * Implement a rate limiter that allows at most N requests per second.
 * Uses the Token Bucket algorithm:
 * - A bucket holds tokens (max capacity = N)
 * - Tokens are added at a fixed rate (N per second)
 * - Each request consumes one token
 * - If no tokens available, request is rejected
 *
 * EXPECTED OUTPUT:
 * [0ms] Request 1: ALLOWED (tokens: 4)
 * [0ms] Request 2: ALLOWED (tokens: 3)
 * [0ms] Request 3: ALLOWED (tokens: 2)
 * [0ms] Request 4: ALLOWED (tokens: 1)
 * [0ms] Request 5: ALLOWED (tokens: 0)
 * [0ms] Request 6: REJECTED (no tokens)
 * [1000ms] Request 7: ALLOWED (tokens refilled)
 * ...
 *
 * LEARNING:
 * - Token Bucket algorithm (used in API gateways, Nginx, AWS)
 * - Thread-safe rate limiting with synchronized or Atomic
 * - System.nanoTime() for timing
 * - Real-world pattern used everywhere
 */
public class Q02_RateLimiter {

    static class TokenBucketRateLimiter {
        private final int maxTokens;       // bucket capacity
        private final int refillRate;      // tokens added per second
        // TODO: Track current tokens and last refill time
        // Hint: use double or AtomicLong for tokens, and long for lastRefillTime

        public TokenBucketRateLimiter(int maxTokens, int refillRate) {
            this.maxTokens = maxTokens;
            this.refillRate = refillRate;
            // TODO: Initialize tokens to maxTokens (bucket starts full)
            // TODO: Initialize lastRefillTime to System.nanoTime()
        }

        // TODO: Implement tryAcquire() — returns true if request is allowed
        public synchronized boolean tryAcquire() {
            // 1. Calculate elapsed time since last refill
            // 2. Add new tokens based on elapsed time (tokens += elapsed * refillRate)
            // 3. Cap tokens at maxTokens
            // 4. Update lastRefillTime
            // 5. If tokens >= 1, consume one token and return true
            // 6. Otherwise return false (rate limited)
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Allow 5 requests per second
        TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(5, 5);
        long startTime = System.currentTimeMillis();

        // Simulate 10 threads making requests
        Thread[] threads = new Thread[10];
        AtomicInteger allowed = new AtomicInteger(0);
        AtomicInteger rejected = new AtomicInteger(0);

        for (int i = 0; i < 10; i++) {
            final int requestId = i + 1;
            threads[i] = new Thread(() -> {
                boolean result = limiter.tryAcquire();
                long elapsed = System.currentTimeMillis() - startTime;
                if (result) {
                    allowed.incrementAndGet();
                    System.out.println("[" + elapsed + "ms] Request " + requestId + ": ALLOWED");
                } else {
                    rejected.incrementAndGet();
                    System.out.println("[" + elapsed + "ms] Request " + requestId + ": REJECTED");
                }
            });
        }

        // Fire all 10 requests at once
        System.out.println("=== Burst: 10 requests at once (limit: 5/sec) ===");
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        System.out.println("Allowed: " + allowed.get() + ", Rejected: " + rejected.get());

        // Wait for tokens to refill
        System.out.println("\n=== Waiting 1 second for refill... ===");
        Thread.sleep(1000);

        // Try again after refill
        allowed.set(0);
        rejected.set(0);
        Thread[] threads2 = new Thread[3];
        for (int i = 0; i < 3; i++) {
            final int requestId = i + 11;
            threads2[i] = new Thread(() -> {
                boolean result = limiter.tryAcquire();
                long elapsed = System.currentTimeMillis() - startTime;
                if (result) {
                    allowed.incrementAndGet();
                    System.out.println("[" + elapsed + "ms] Request " + requestId + ": ALLOWED");
                } else {
                    rejected.incrementAndGet();
                    System.out.println("[" + elapsed + "ms] Request " + requestId + ": REJECTED");
                }
            });
        }
        for (Thread t : threads2) t.start();
        for (Thread t : threads2) t.join();
        System.out.println("Allowed: " + allowed.get() + ", Rejected: " + rejected.get());
    }
}

/*
SOLUTION HINTS:
1. Track: double tokens = maxTokens; long lastRefillNanos = System.nanoTime();
2. In tryAcquire():
   long now = System.nanoTime();
   double elapsed = (now - lastRefillNanos) / 1_000_000_000.0;
   tokens = Math.min(maxTokens, tokens + elapsed * refillRate);
   lastRefillNanos = now;
   if (tokens >= 1) { tokens--; return true; }
   return false;
3. synchronized on tryAcquire() handles thread safety
4. For higher throughput, could use AtomicLong with CAS loop instead of synchronized

KEY CONCEPT: Token bucket is the most common rate limiting algorithm.
Used in API gateways (Kong, Nginx), cloud services (AWS, GCP), and libraries (Guava RateLimiter).
*/