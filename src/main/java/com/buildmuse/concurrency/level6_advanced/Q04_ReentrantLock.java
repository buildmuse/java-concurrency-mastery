package com.buildmuse.concurrency.level6_advanced;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

/**
 * PROBLEM: ReentrantLock Advanced Features
 * 
 * Use ReentrantLock with:
 * - tryLock() with timeout
 * - Fair locking (FIFO order)
 * - Manual lock/unlock control
 * 
 * EXPECTED OUTPUT:
 * Thread-1: Attempting to acquire lock
 * Thread-1: Lock acquired
 * Thread-2: Attempting to acquire lock
 * Thread-2: Could not acquire lock, will retry
 * Thread-1: Released lock
 * Thread-2: Lock acquired (on retry)
 * 
 * LEARNING:
 * - ReentrantLock vs synchronized
 * - tryLock() with timeout
 * - Fair vs unfair locking
 * - lock()/unlock() must be in try-finally
 */
public class Q04_ReentrantLock {
    
    // TODO: Create ReentrantLock with fairness = true
    private static final Lock lock = null;
    
    static void doWork(int threadId) {
        System.out.println("Thread-" + threadId + ": Attempting to acquire lock");
        
        try {
            // TODO: Use tryLock with 1 second timeout
            // boolean acquired = lock.tryLock(1, TimeUnit.SECONDS);
            
            boolean acquired = false;
            
            if (acquired) {
                try {
                    System.out.println("Thread-" + threadId + ": Lock acquired");
                    Thread.sleep(2000); // Hold lock for 2 seconds
                } finally {
                    // TODO: Release lock
                    
                    System.out.println("Thread-" + threadId + ": Released lock");
                }
            } else {
                System.out.println("Thread-" + threadId + ": Could not acquire lock, will retry");
                
                // Retry with blocking acquire
                // TODO: lock.lock() - blocks until available
                
                try {
                    System.out.println("Thread-" + threadId + ": Lock acquired (on retry)");
                    Thread.sleep(1000);
                } finally {
                    // TODO: Release lock
                    
                    System.out.println("Thread-" + threadId + ": Released lock (retry)");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        
        // TODO: Create 3 threads calling doWork
        Thread t1 = new Thread(() -> doWork(1));
        Thread t2 = new Thread(() -> doWork(2));
        Thread t3 = new Thread(() -> doWork(3));
        
        t1.start();
        Thread.sleep(100); // Stagger starts
        t2.start();
        Thread.sleep(100);
        t3.start();
        
        t1.join();
        t2.join();
        t3.join();
    }
}

/*
SOLUTION HINTS:
1. new ReentrantLock(true) - fair lock
2. tryLock(1, TimeUnit.SECONDS)
3. Always unlock in finally block
4. lock.lock() for blocking acquire

KEY CONCEPT: ReentrantLock offers more control than synchronized
tryLock, fairness, interruptible locking
*/
