package com.buildmuse.concurrency.level3_communication;

/**
 * PROBLEM: Print Sequence with Multiple Threads
 * 
 * Create 3 threads:
 * - Thread-1 prints: 1
 * - Thread-2 prints: 2
 * - Thread-3 prints: 3
 * 
 * Print sequence: 1 2 3 1 2 3 1 2 3 (repeat 5 times)
 * 
 * EXPECTED OUTPUT:
 * 1
 * 2
 * 3
 * 1
 * 2
 * 3
 * ...
 * 
 * LEARNING:
 * - Coordinating multiple threads
 * - State-based wait conditions
 * - Round-robin execution
 */
public class Q04_PrintSequence {
    
    static class SequencePrinter {
        private int currentNumber = 1;
        private final int maxNumber = 3;
        private final Object lock = new Object();
        
        public void printNumber(int number) throws InterruptedException {
            synchronized (lock) {
                // TODO: Wait while it's not this thread's turn
                // while (currentNumber != number) { wait(); }
                
                
                System.out.println(number);
                
                // TODO: Update currentNumber for next thread
                // currentNumber = (currentNumber % maxNumber) + 1
                
                
                // TODO: Notify all waiting threads
                
            }
        }
    }
    
    public static void main(String[] args) {
        SequencePrinter printer = new SequencePrinter();
        int iterations = 5;
        
        // TODO: Create 3 threads, each calling printNumber() 5 times
        Thread t1 = new Thread(() -> {
            // TODO: Loop 5 times, call printer.printNumber(1)
            
        }, "Thread-1");
        
        Thread t2 = new Thread(() -> {
            // TODO: Loop 5 times, call printer.printNumber(2)
            
        }, "Thread-2");
        
        Thread t3 = new Thread(() -> {
            // TODO: Loop 5 times, call printer.printNumber(3)
            
        }, "Thread-3");
        
        // TODO: Start all threads and wait for completion
        
    }
}

/*
SOLUTION HINTS:
1. printNumber(int number):
   while (currentNumber != number) { wait(); }
   System.out.println(number);
   currentNumber = (currentNumber % 3) + 1;
   notifyAll();

2. Each thread loops and calls printNumber with its assigned number

KEY CONCEPT: State-based coordination between multiple threads
*/
