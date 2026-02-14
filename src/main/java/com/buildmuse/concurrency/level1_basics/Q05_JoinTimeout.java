package com.buildmuse.concurrency.level1_basics;

/**
 * PROBLEM: Join with Timeout
 * 
 * Create a long-running thread (sleeps for 5 seconds).
 * Main thread should wait max 2 seconds using join(timeout).
 * If thread doesn't finish in time, interrupt it.
 * 
 * EXPECTED OUTPUT:
 * Worker: Starting 5-second task
 * Main: Waiting max 2 seconds for worker
 * Main: Worker didn't finish in time, interrupting
 * Worker: Task interrupted
 * Main: Done
 * 
 * LEARNING:
 * - join(long millis)
 * - Timeout handling
 * - isAlive() check
 */
public class Q05_JoinTimeout {
    
    public static void main(String[] args) throws InterruptedException {
        
        Thread worker = new Thread(() -> {
            System.out.println("Worker: Starting 5-second task");
            try {
                Thread.sleep(5000);
                System.out.println("Worker: Task completed");
            } catch (InterruptedException e) {
                System.out.println("Worker: Task interrupted");
            }
        });
        
        worker.start();
        
        System.out.println("Main: Waiting max 2 seconds for worker");
        
        // TODO: Wait for worker with 2-second timeout
        
        
        // TODO: Check if worker is still alive
        // If alive, interrupt it
        
        
        System.out.println("Main: Done");
    }
}

/*
SOLUTION HINTS:
1. Use worker.join(2000) for 2-second timeout
2. After join, check worker.isAlive()
3. If alive, call worker.interrupt()

KEY CONCEPT: join(timeout) prevents indefinite waiting
*/
