package com.buildmuse.concurrency.level6_advanced;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * PROBLEM: CyclicBarrier
 * 
 * 3 threads perform work in phases.
 * They must synchronize at barrier between phases.
 * All threads must reach barrier before any can proceed.
 * 
 * EXPECTED OUTPUT:
 * Thread-1: Phase 1
 * Thread-2: Phase 1
 * Thread-3: Phase 1
 * [All threads reached barrier]
 * Thread-1: Phase 2
 * Thread-2: Phase 2
 * Thread-3: Phase 2
 * [All threads reached barrier]
 * 
 * LEARNING:
 * - CyclicBarrier for synchronization points
 * - await() blocks until all parties arrive
 * - Reusable (unlike CountDownLatch)
 * - Optional barrier action
 */
public class Q02_CyclicBarrier {
    
    public static void main(String[] args) {
        final int NUM_THREADS = 3;
        final int NUM_PHASES = 3;
        
        // TODO: Create CyclicBarrier with barrier action
        CyclicBarrier barrier = null;
        
        // TODO: Create 3 worker threads
        for (int i = 1; i <= NUM_THREADS; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    for (int phase = 1; phase <= NUM_PHASES; phase++) {
                        System.out.println("Thread-" + threadId + ": Phase " + phase);
                        Thread.sleep(threadId * 300); // Simulate work
                        
                        // TODO: Wait at barrier using barrier.await()
                        
                    }
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}

/*
SOLUTION HINTS:
1. new CyclicBarrier(3, () -> {
       System.out.println("[All threads reached barrier]");
   });

2. barrier.await() in each thread after phase work

KEY CONCEPT: CyclicBarrier synchronizes threads at specific points
Reusable across multiple phases
*/
