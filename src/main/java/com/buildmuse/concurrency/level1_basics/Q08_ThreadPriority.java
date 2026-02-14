package com.buildmuse.concurrency.level1_basics;

/**
 * PROBLEM: Thread Priority
 * 
 * Create 3 threads with different priorities:
 * - High priority (10)
 * - Normal priority (5 - default)
 * - Low priority (1)
 * 
 * Each counts to 1 million and reports completion order.
 * Note: Priority is just a hint to OS scheduler.
 * 
 * EXPECTED OUTPUT (order may vary):
 * High-priority thread completed
 * Normal-priority thread completed
 * Low-priority thread completed
 * 
 * LEARNING:
 * - setPriority()
 * - Thread.MIN_PRIORITY (1)
 * - Thread.NORM_PRIORITY (5)
 * - Thread.MAX_PRIORITY (10)
 * - Priority is platform-dependent
 */
public class Q08_ThreadPriority {
    
    static class CountingTask implements Runnable {
        private final String name;
        
        public CountingTask(String name) {
            this.name = name;
        }
        
        @Override
        public void run() {
            long count = 0;
            for (int i = 0; i < 1_000_000; i++) {
                count++;
            }
            System.out.println(name + " completed (count: " + count + ")");
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        
        // TODO: Create 3 threads with CountingTask
        Thread highPriority = new Thread(new CountingTask("High-priority thread"));
        Thread normalPriority = new Thread(new CountingTask("Normal-priority thread"));
        Thread lowPriority = new Thread(new CountingTask("Low-priority thread"));
        
        // TODO: Set priorities
        // highPriority = MAX_PRIORITY (10)
        // normalPriority = NORM_PRIORITY (5) - default
        // lowPriority = MIN_PRIORITY (1)
        
        
        
        // TODO: Start all threads
        
        
        
        // TODO: Wait for all to complete
        
        
        
        System.out.println("All threads completed");
    }
}

/*
SOLUTION HINTS:
1. thread.setPriority(Thread.MAX_PRIORITY) before start()
2. Thread.MIN_PRIORITY = 1
3. Thread.NORM_PRIORITY = 5 (default)
4. Thread.MAX_PRIORITY = 10
5. Priority is just a hint - not guaranteed

KEY CONCEPT: Don't rely on priority for correctness, only optimization
*/
