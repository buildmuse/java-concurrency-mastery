package com.buildmuse.concurrency.level5_executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * PROBLEM: Thread Pool with ExecutorService
 * 
 * Create a fixed thread pool with 3 threads.
 * Submit 10 tasks to the pool.
 * Tasks should be executed by the 3 threads.
 * 
 * EXPECTED OUTPUT:
 * pool-1-thread-1: Executing task 1
 * pool-1-thread-2: Executing task 2
 * pool-1-thread-3: Executing task 3
 * pool-1-thread-1: Executing task 4
 * ...
 * All tasks completed
 * 
 * LEARNING:
 * - ExecutorService interface
 * - Fixed thread pool
 * - Task submission
 * - Graceful shutdown
 */
public class Q01_ExecutorService {
    
    public static void main(String[] args) throws InterruptedException {
        
        // TODO: Create ExecutorService with fixed thread pool of 3
        ExecutorService executor = null;
        
        // TODO: Submit 10 tasks
        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            // TODO: Use executor.submit() or executor.execute()
            
        }
        
        // TODO: Shutdown executor
        // Use shutdown() - doesn't accept new tasks but completes existing ones
        
        
        // TODO: Wait for all tasks to complete
        // Use awaitTermination(timeout, unit)
        
        
        System.out.println("All tasks completed");
    }
    
    static void executeTask(int taskId) {
        System.out.println(Thread.currentThread().getName() + ": Executing task " + taskId);
        try {
            Thread.sleep(500); // Simulate work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

/*
SOLUTION HINTS:
1. Executors.newFixedThreadPool(3)
2. executor.execute(() -> executeTask(taskId))
3. executor.shutdown() - initiates orderly shutdown
4. executor.awaitTermination(1, TimeUnit.MINUTES)

KEY CONCEPT: Thread pools reuse threads - more efficient than creating new threads
*/
