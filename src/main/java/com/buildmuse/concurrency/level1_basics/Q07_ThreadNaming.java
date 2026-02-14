package com.buildmuse.concurrency.level1_basics;

/**
 * PROBLEM: Thread Naming and Identity
 * 
 * Create 3 worker threads with custom names.
 * Each thread should print its own name and ID.
 * 
 * EXPECTED OUTPUT:
 * Main thread: main (ID: 1)
 * Worker-1: Starting (ID: 15)
 * Worker-2: Starting (ID: 16)
 * Worker-3: Starting (ID: 17)
 * Worker-1: Completed
 * Worker-2: Completed
 * Worker-3: Completed
 * 
 * LEARNING:
 * - Thread.currentThread()
 * - setName() / getName()
 * - getId()
 * - Thread naming best practices
 */
public class Q07_ThreadNaming {
    
    public static void main(String[] args) throws InterruptedException {
        
        // Print main thread info
        Thread main = Thread.currentThread();
        System.out.println("Main thread: " + main.getName() + " (ID: " + main.getId() + ")");
        
        // TODO: Create 3 threads with names "Worker-1", "Worker-2", "Worker-3"
        // Each should print its name and ID, then sleep 1 second
        
        Thread worker1 = new Thread(() -> {
            // TODO: Get current thread and print its name and ID
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // TODO: Print completion message with thread name
            
        });
        
        // TODO: Set name for worker1
        
        
        // TODO: Create worker2 and worker3 similarly
        
        
        // TODO: Start all workers
        
        
        // TODO: Wait for all to complete
        
    }
}

/*
SOLUTION HINTS:
1. Thread.currentThread() returns current executing thread
2. thread.setName("Worker-1") before start()
3. Or pass name in constructor: new Thread(runnable, "Worker-1")
4. getName() and getId() for thread info

KEY CONCEPT: Named threads make debugging easier in logs and stack traces
*/
