package com.buildmuse.concurrency.level1_basics;

/**
 * PROBLEM: Daemon Threads
 * 
 * Create two threads:
 * 1. User thread that runs for 2 seconds
 * 2. Daemon thread that runs indefinitely
 * 
 * Daemon thread should automatically terminate when user thread completes.
 * 
 * EXPECTED OUTPUT:
 * User thread: Starting
 * Daemon thread: Iteration 1
 * Daemon thread: Iteration 2
 * Daemon thread: Iteration 3
 * Daemon thread: Iteration 4
 * User thread: Completed
 * [Program exits - daemon thread stops automatically]
 * 
 * LEARNING:
 * - Daemon vs User threads
 * - setDaemon(true)
 * - JVM exits when only daemon threads remain
 */
public class Q06_DaemonThread {
    
    public static void main(String[] args) throws InterruptedException {
        
        // User thread - normal thread
        Thread userThread = new Thread(() -> {
            System.out.println("User thread: Starting");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("User thread: Completed");
        });
        
        // TODO: Create daemon thread that runs indefinitely
        // Print "Daemon thread: Iteration X" every 500ms
        Thread daemonThread = new Thread(() -> {
            // TODO: Implement infinite loop
            
        });
        
        // TODO: Set daemonThread as daemon (MUST be done before start())
        
        
        userThread.start();
        daemonThread.start();
        
        // Wait for user thread to complete
        userThread.join();
        
        System.out.println("Main: Exiting (daemon will auto-terminate)");
        // No need to wait for daemon thread - JVM will exit
    }
}

/*
SOLUTION HINTS:
1. Create infinite loop in daemon thread
2. Call daemonThread.setDaemon(true) BEFORE start()
3. Daemon threads are terminated when all user threads finish
4. Use for background tasks like monitoring, logging

KEY CONCEPT: Daemon threads don't prevent JVM shutdown
*/
