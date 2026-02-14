package com.buildmuse.concurrency.level6_advanced;

import java.util.concurrent.Semaphore;

/**
 * PROBLEM: Semaphore for Connection Pool
 * 
 * Implement connection pool with max 3 concurrent connections.
 * 5 threads try to get connections.
 * Only 3 can hold connections at a time.
 * 
 * EXPECTED OUTPUT:
 * Thread-1: Acquired connection
 * Thread-2: Acquired connection
 * Thread-3: Acquired connection
 * Thread-1: Released connection
 * Thread-4: Acquired connection
 * Thread-2: Released connection
 * Thread-5: Acquired connection
 * ...
 * 
 * LEARNING:
 * - Semaphore for limiting concurrent access
 * - acquire() blocks if no permits available
 * - release() frees permit
 * - Use for resource pools (connections, threads, etc.)
 */
public class Q03_Semaphore {
    
    static class ConnectionPool {
        // TODO: Create Semaphore with 3 permits
        private final Semaphore semaphore = null;
        
        public void getConnection(int threadId) throws InterruptedException {
            // TODO: Acquire permit
            
            
            System.out.println("Thread-" + threadId + ": Acquired connection");
            
            // Simulate using connection
            Thread.sleep(2000);
            
            System.out.println("Thread-" + threadId + ": Released connection");
            
            // TODO: Release permit
            
        }
    }
    
    public static void main(String[] args) {
        ConnectionPool pool = new ConnectionPool();
        
        // TODO: Create 5 threads trying to get connections
        for (int i = 1; i <= 5; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    pool.getConnection(threadId);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}

/*
SOLUTION HINTS:
1. new Semaphore(3) - 3 permits
2. semaphore.acquire() before using resource
3. semaphore.release() in finally block
4. Use try-finally to ensure release

KEY CONCEPT: Semaphore limits concurrent access to resources
Perfect for connection pools, rate limiting
*/
