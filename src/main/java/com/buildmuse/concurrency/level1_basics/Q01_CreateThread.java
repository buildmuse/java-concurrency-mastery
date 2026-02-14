package com.buildmuse.concurrency.level1_basics;

/**
 * PROBLEM: Create and Start a Thread
 * 
 * Create a thread that prints numbers 1 to 5 with 500ms delay between each.
 * Use Thread class (not Runnable).
 * 
 * EXPECTED OUTPUT:
 * Main thread: Starting worker thread
 * Worker: 1
 * Worker: 2
 * Worker: 3
 * Worker: 4
 * Worker: 5
 * Main thread: Worker completed
 * 
 * LEARNING: 
 * - Thread class extension
 * - run() method override
 * - start() vs run()
 * - Thread.sleep()
 */
public class Q01_CreateThread {
    
    static class WorkerThread extends Thread {
        // TODO: Override run() method
        // Print "Worker: {number}" for 1 to 5
        // Sleep 500ms between each print
        
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main thread: Starting worker thread");
        
        // TODO: Create and start WorkerThread
        
        
        // TODO: Wait for worker to complete using join()
        
        
        System.out.println("Main thread: Worker completed");
    }
}

/*
SOLUTION HINTS:
1. Extend Thread class in WorkerThread
2. Override run() method with loop 1-5
3. Use Thread.sleep(500) for delay
4. Create instance: new WorkerThread()
5. Call start() not run()
6. Use join() to wait for completion

KEY CONCEPT: start() creates new thread, run() executes in same thread
*/
