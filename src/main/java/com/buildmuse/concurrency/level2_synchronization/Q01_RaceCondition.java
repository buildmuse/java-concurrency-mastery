package com.buildmuse.concurrency.level2_synchronization;

/**
 * PROBLEM: Race Condition
 * 
 * Create a Counter class with increment() method.
 * Launch 2 threads, each incrementing counter 1000 times.
 * Expected final count: 2000
 * Actual result: Less than 2000 (race condition!)
 * 
 * EXPECTED OUTPUT:
 * Thread-1 completed 1000 increments
 * Thread-2 completed 1000 increments
 * Final count: 1456 (or some random value < 2000)
 * Expected: 2000
 * Lost updates: 544
 * 
 * LEARNING:
 * - Race condition happens with shared mutable state
 * - count++ is NOT atomic (read-modify-write)
 * - Need synchronization (will fix in next problem)
 */
public class Q01_RaceCondition {
    
    static class Counter {
        private int count = 0;
        
        // TODO: Implement increment (DO NOT synchronize yet)
        public void increment() {
            // This is NOT atomic!
            ++count;
        }
        
        public int getCount() {
            return count;
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        
        // TODO: Create 2 threads, each calling counter.increment() 1000 times
        Thread t1 = new Thread(() -> {
            // TODO: Loop 1000 times, call increment()
            int i = 1;
            while(i <= 1000) {
                counter.increment();
                i++;
            }
            
            System.out.println("Thread-1 completed 1000 increments");
        });
        
        Thread t2 = new Thread(() -> {
            // TODO: Loop 1000 times, call increment()
            int i = 1;
            while(i <= 1000) {
                counter.increment();
                i++;
            }
            
            System.out.println("Thread-2 completed 1000 increments");
        });
        
        // TODO: Start threads and wait for completion
        t1.start();
        t2.start();
        
        
        // TODO: Print results
        int finalCount = counter.getCount();
        System.out.println("Final count: " + finalCount);
        System.out.println("Expected: 2000");
        System.out.println("Lost updates: " + (2000 - finalCount));
    }
}

/*
SOLUTION HINTS:
1. increment() just does: count++
2. Create simple for loop in each thread
3. Start both, join both
4. Run multiple times - you'll get different results each time!

KEY CONCEPT: count++ translates to 3 operations:
- Read count
- Add 1
- Write count
Without synchronization, these can interleave causing lost updates
*/
