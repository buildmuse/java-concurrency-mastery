package com.buildmuse.concurrency.level2_synchronization;

/**
 * PROBLEM: Synchronized Method
 * 
 * Fix the race condition from Q01 using synchronized method.
 * Now final count should ALWAYS be 2000.
 * 
 * EXPECTED OUTPUT:
 * Thread-1 completed 1000 increments
 * Thread-2 completed 1000 increments
 * Final count: 2000 ✓
 * 
 * LEARNING:
 * - synchronized keyword on methods
 * - Intrinsic lock (monitor) on object
 * - Only one thread can execute synchronized method at a time
 */
public class Q02_SynchronizedMethod {
    
    static class Counter {
        private int count = 0;
        
        // TODO: Add synchronized keyword to make it thread-safe
        public synchronized void increment() {
            count++;
        }
        
        public int getCount() {
            return count;
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
            System.out.println("Thread-1 completed 1000 increments");
        });
        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
            System.out.println("Thread-2 completed 1000 increments");
        });
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
        
        int finalCount = counter.getCount();
        System.out.println("Final count: " + finalCount);
        
        if (finalCount == 2000) {
            System.out.println("✓ Thread-safe!");
        } else {
            System.out.println("✗ Still has race condition");
        }
    }
}

/*
SOLUTION HINTS:
1. Add synchronized keyword: public synchronized void increment()
2. This acquires lock on 'this' object
3. Only one thread can execute synchronized method at a time
4. Other threads wait for lock to be released

KEY CONCEPT: synchronized method = synchronized(this) { method body }
*/
