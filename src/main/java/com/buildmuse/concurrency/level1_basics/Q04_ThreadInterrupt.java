package com.buildmuse.concurrency.level1_basics;

/**
 * PROBLEM: Thread Interruption
 * 
 * Create a worker thread that runs indefinitely in a loop.
 * The main thread should interrupt it after 3 seconds.
 * Worker should handle interruption gracefully and exit.
 * 
 * EXPECTED OUTPUT:
 * Worker: Iteration 1
 * Worker: Iteration 2
 * Worker: Iteration 3
 * Worker: Iteration 4
 * Worker: Iteration 5
 * Worker: Iteration 6
 * Main: Interrupting worker
 * Worker: Interrupted! Cleaning up...
 * Worker: Exited gracefully
 * Main: Worker stopped
 * 
 * LEARNING:
 * - Thread interruption
 * - isInterrupted() check
 * - InterruptedException handling
 * - Graceful shutdown
 */
public class Q04_ThreadInterrupt {
    
    public static void main(String[] args) throws InterruptedException {
        
        Thread worker = new Thread(() -> {
            // TODO: Create infinite loop that checks for interruption
            // Print "Worker: Iteration X" every 500ms
            // When interrupted, print cleanup message and exit
            
            int iteration = 0;
            while (true /* TODO: check if not interrupted */) {
                iteration++;
                System.out.println("Worker: Iteration " + iteration);
                
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO: Handle interruption
                    // Print "Worker: Interrupted! Cleaning up..."
                    // Break the loop
                    
                }
            }
            
            System.out.println("Worker: Exited gracefully");
        });
        
        worker.start();
        
        // Let worker run for 3 seconds
        Thread.sleep(3000);
        
        System.out.println("Main: Interrupting worker");
        // TODO: Interrupt the worker thread
        
        
        // TODO: Wait for worker to finish
        
        
        System.out.println("Main: Worker stopped");
    }
}

/*
SOLUTION HINTS:
1. Loop condition: !Thread.currentThread().isInterrupted()
2. In catch block: Thread.currentThread().interrupt() to preserve status
3. Then break the loop
4. Use worker.interrupt() from main thread
5. Use worker.join() to wait

KEY CONCEPT: Always handle InterruptedException properly - it's a shutdown signal
*/
