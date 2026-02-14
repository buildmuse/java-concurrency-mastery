package com.buildmuse.concurrency.level3_communication;

/**
 * PROBLEM: Wait and Notify Basics
 * 
 * Thread-1: Waits for a signal
 * Thread-2: Does work, then sends signal
 * 
 * EXPECTED OUTPUT:
 * Thread-1: Waiting for signal...
 * Thread-2: Working...
 * Thread-2: Sending signal
 * Thread-1: Received signal, proceeding
 * 
 * LEARNING:
 * - wait() releases lock and waits for notification
 * - notify() wakes up one waiting thread
 * - Must be called within synchronized block
 * - Must synchronize on same object
 */
public class Q01_WaitNotifyBasics {
    
    private static final Object lock = new Object();
    
    public static void main(String[] args) {
        
        Thread thread1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Thread-1: Waiting for signal...");
                try {
                    // TODO: Call wait() on lock object
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Thread-1: Received signal, proceeding");
            }
        }, "Thread-1");
        
        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(2000); // Simulate work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            synchronized (lock) {
                System.out.println("Thread-2: Working...");
                System.out.println("Thread-2: Sending signal");
                // TODO: Call notify() on lock object
                
            }
        }, "Thread-2");
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

/*
SOLUTION HINTS:
1. In thread1: lock.wait()
2. In thread2: lock.notify()
3. Both must be inside synchronized (lock) block
4. wait() releases the lock
5. notify() wakes up one waiting thread

KEY CONCEPT: wait/notify must be called on the SAME object you synchronized on
*/
