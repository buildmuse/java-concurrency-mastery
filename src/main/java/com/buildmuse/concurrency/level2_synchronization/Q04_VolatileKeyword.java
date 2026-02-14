package com.buildmuse.concurrency.level2_synchronization;

/**
 * PROBLEM: Volatile Keyword
 * 
 * Create a flag-based thread control mechanism.
 * Worker thread runs until flag is set to false.
 * Without volatile, worker might not see the flag change (visibility issue).
 * 
 * EXPECTED OUTPUT:
 * Worker: Started
 * Worker: Iteration 1
 * Worker: Iteration 2
 * Main: Setting stop flag
 * Worker: Stop flag detected, exiting
 * Main: Worker stopped
 * 
 * LEARNING:
 * - volatile keyword ensures visibility across threads
 * - volatile prevents CPU caching
 * - volatile != synchronized (doesn't provide atomicity)
 */
public class Q04_VolatileKeyword {
    
    // TODO: Add volatile keyword to ensure visibility
    private static boolean running = true;
    
    public static void main(String[] args) throws InterruptedException {
        
        Thread worker = new Thread(() -> {
            System.out.println("Worker: Started");
            int iteration = 0;
            
            // TODO: Check 'running' flag in loop
            while (running) {
                iteration++;
                System.out.println("Worker: Iteration " + iteration);
                
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            
            System.out.println("Worker: Stop flag detected, exiting");
        });
        
        worker.start();
        
        // Let worker run for 2 seconds
        Thread.sleep(2000);
        
        System.out.println("Main: Setting stop flag");
        // TODO: Set running to false
        running = false;
        
        worker.join();
        System.out.println("Main: Worker stopped");
    }
}

/*
SOLUTION HINTS:
1. Add volatile: private static volatile boolean running = true;
2. Without volatile, worker thread might cache 'running' value
3. volatile ensures changes are visible to all threads immediately
4. Use volatile for simple flags, not for compound operations

KEY CONCEPT: volatile guarantees visibility, NOT atomicity
For atomicity, use synchronized or AtomicBoolean
*/
