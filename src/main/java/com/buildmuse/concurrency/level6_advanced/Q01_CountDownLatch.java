package com.buildmuse.concurrency.level6_advanced;

import java.util.concurrent.CountDownLatch;

/**
 * PROBLEM: CountDownLatch
 * 
 * 3 worker threads perform initialization.
 * Main thread waits for all workers to finish before proceeding.
 * 
 * EXPECTED OUTPUT:
 * Main: Waiting for workers to initialize...
 * Worker-1: Initializing...
 * Worker-2: Initializing...
 * Worker-3: Initializing...
 * Worker-1: Initialization complete
 * Worker-2: Initialization complete
 * Worker-3: Initialization complete
 * Main: All workers initialized, starting application
 * 
 * LEARNING:
 * - CountDownLatch for one-time coordination
 * - await() blocks until count reaches zero
 * - countDown() decrements count
 */
public class Q01_CountDownLatch {
    
    public static void main(String[] args) throws InterruptedException {
        
        // TODO: Create CountDownLatch with count 3 (for 3 workers)
        CountDownLatch latch = null;
        
        System.out.println("Main: Waiting for workers to initialize...");
        
        // TODO: Create 3 worker threads
        for (int i = 1; i <= 3; i++) {
            final int workerId = i;
            new Thread(() -> {
                System.out.println("Worker-" + workerId + ": Initializing...");
                try {
                    Thread.sleep(1000 + workerId * 500); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Worker-" + workerId + ": Initialization complete");
                
                // TODO: Signal completion using latch.countDown()
                
            }).start();
        }
        
        // TODO: Wait for all workers to complete using latch.await()
        
        
        System.out.println("Main: All workers initialized, starting application");
    }
}

/*
SOLUTION HINTS:
1. new CountDownLatch(3)
2. In each worker: latch.countDown() after work
3. Main thread: latch.await() - blocks until count = 0

KEY CONCEPT: CountDownLatch for waiting on multiple threads
One-time use only (can't be reset)
*/
