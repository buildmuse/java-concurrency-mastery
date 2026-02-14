package com.buildmuse.concurrency.level1_basics;

/**
 * PROBLEM: Thread Lifecycle States
 * 
 * Create a thread and print its state at different points:
 * - After creation (NEW)
 * - After start (RUNNABLE)
 * - While sleeping (TIMED_WAITING)
 * - After completion (TERMINATED)
 * 
 * EXPECTED OUTPUT:
 * State after creation: NEW
 * State after start: RUNNABLE
 * Worker: Starting work
 * State while sleeping: TIMED_WAITING
 * Worker: Work completed
 * State after completion: TERMINATED
 * 
 * LEARNING:
 * - Thread.State enum
 * - getState() method
 * - Thread lifecycle transitions
 */
public class Q03_ThreadLifecycle {
    
    public static void main(String[] args) throws InterruptedException {
        
        Thread worker = new Thread(() -> {
            System.out.println("Worker: Starting work");
            try {
                Thread.sleep(2000); // Sleep for 2 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Worker: Work completed");
        });
        
        // TODO: Print state after creation (should be NEW)
        System.out.println("State after creation: " + "TODO");
        
        // TODO: Start the thread
        
        
        // TODO: Print state after start (should be RUNNABLE)
        System.out.println("State after start: " + "TODO");
        
        // Give thread time to start sleeping
        Thread.sleep(500);
        
        // TODO: Print state while sleeping (should be TIMED_WAITING)
        System.out.println("State while sleeping: " + "TODO");
        
        // TODO: Wait for thread to complete
        
        
        // TODO: Print state after completion (should be TERMINATED)
        System.out.println("State after completion: " + "TODO");
    }
}

/*
SOLUTION HINTS:
1. Use thread.getState()
2. Thread.State is enum with: NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED
3. Call start() to move from NEW to RUNNABLE
4. Thread.sleep() causes TIMED_WAITING
5. join() waits for TERMINATED

KEY CONCEPT: Understanding thread states helps debug concurrency issues
*/
