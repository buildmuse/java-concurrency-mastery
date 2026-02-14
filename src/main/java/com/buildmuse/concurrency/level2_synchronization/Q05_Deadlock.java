package com.buildmuse.concurrency.level2_synchronization;

/**
 * PROBLEM: Deadlock
 * 
 * Create a deadlock scenario with two locks.
 * Thread-1: Acquires lock1, then tries lock2
 * Thread-2: Acquires lock2, then tries lock1
 * Both threads will deadlock!
 * 
 * EXPECTED OUTPUT:
 * Thread-1: Acquired lock1
 * Thread-2: Acquired lock2
 * Thread-1: Waiting for lock2...
 * Thread-2: Waiting for lock1...
 * [Program hangs - deadlock!]
 * 
 * LEARNING:
 * - Deadlock happens when circular dependency exists
 * - Thread A waits for B, B waits for A
 * - Always acquire locks in same order to prevent deadlock
 */
public class Q05_Deadlock {
    
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();
    
    public static void main(String[] args) {
        
        // TODO: Create Thread-1
        // Acquire lock1, then lock2
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread-1: Acquired lock1");
                
                // Small delay to ensure both threads get their first lock
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                System.out.println("Thread-1: Waiting for lock2...");
                synchronized (lock2) {
                    System.out.println("Thread-1: Acquired lock2");
                }
            }
        }, "Thread-1");
        
        // TODO: Create Thread-2
        // Acquire lock2, then lock1 (OPPOSITE ORDER - causes deadlock!)
        Thread thread2 = new Thread(() -> {
            // TODO: First acquire lock2, then lock1
            
        }, "Thread-2");
        
        thread1.start();
        thread2.start();
        
        // Try to wait for threads (they'll never finish)
        try {
            thread1.join(3000); // Wait max 3 seconds
            thread2.join(3000);
            
            if (thread1.isAlive() || thread2.isAlive()) {
                System.out.println("\n⚠ DEADLOCK DETECTED!");
                System.out.println("Thread-1 alive: " + thread1.isAlive());
                System.out.println("Thread-2 alive: " + thread2.isAlive());
                System.out.println("\nFix: Always acquire locks in same order");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

/*
SOLUTION HINTS:
1. Thread-2 should do:
   synchronized (lock2) {
       sleep(50);
       synchronized (lock1) { ... }
   }
2. This creates circular dependency
3. Fix: Make both threads acquire in same order (lock1, then lock2)

KEY CONCEPT: Prevent deadlock by establishing lock ordering
*/
