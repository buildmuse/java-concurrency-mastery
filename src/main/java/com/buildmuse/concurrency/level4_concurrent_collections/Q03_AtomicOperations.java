package com.buildmuse.concurrency.level4_concurrent_collections;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * PROBLEM: Atomic Variables
 *
 * Atomic classes provide lock-free, thread-safe operations on single variables.
 * They use CPU-level Compare-And-Swap (CAS) instructions — no synchronized needed.
 *
 * EXERCISES:
 * 1. Fix the race condition using AtomicInteger
 * 2. Build a thread-safe counter with AtomicLong
 * 3. Use AtomicReference for lock-free state updates
 * 4. Use compareAndSet for optimistic locking
 *
 * LEARNING:
 * - AtomicInteger, AtomicLong, AtomicReference
 * - incrementAndGet, compareAndSet, getAndUpdate
 * - Why Atomic is faster than synchronized for simple operations
 * - CAS (Compare-And-Swap) concept
 */
public class Q03_AtomicOperations {

    // =============================================
    // EXERCISE 1: Fix the race condition
    // =============================================
    // This counter has a race condition. Fix it using AtomicInteger.
    // Do NOT use synchronized.

    static AtomicInteger unsafeCounter = null;

    static void exercise1_fixRaceCondition() throws InterruptedException {
        // TODO: Reset counter to 0
        unsafeCounter = new AtomicInteger(0);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                unsafeCounter.incrementAndGet(); // TODO: Use atomic increment
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                unsafeCounter.incrementAndGet(); // TODO: Use atomic increment
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Counter (should be 20000): " + unsafeCounter.get());
        // TODO: use .get() to print
    }

    // =============================================
    // EXERCISE 2: Thread-safe page view counter
    // =============================================
    // Multiple threads increment different page counters.
    // Use AtomicLong for the counts.

    static AtomicLong pageViews = null; // TODO: Initialize
    static AtomicLong apiCalls = null;  // TODO: Initialize

    static void exercise2_pageViewCounter() throws InterruptedException {
        // TODO: Initialize both counters to 0
        pageViews = new AtomicLong(0);
        apiCalls = new AtomicLong(0);
        // Simulate 5 threads hitting the website
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    // TODO: Increment pageViews
                    pageViews.incrementAndGet();
                    if (j % 3 == 0) {
                        // TODO: Increment apiCalls (every 3rd request makes an API call)
                        apiCalls.incrementAndGet();
                    }
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        // TODO: Print pageViews (should be 5000) and apiCalls (should be 1670)
        System.out.println("Page views: " + pageViews.get());
        System.out.println("API calls: " + apiCalls.get());
    }

    // =============================================
    // EXERCISE 3: AtomicReference — lock-free state machine
    // =============================================
    // Model a connection with states: DISCONNECTED → CONNECTING → CONNECTED → DISCONNECTED
    // Use AtomicReference<String> and compareAndSet to ensure valid transitions.

    static AtomicReference<String> connectionState = null; // TODO: Initialize with "DISCONNECTED"

    static void exercise3_atomicReference() throws InterruptedException {
        // TODO: Initialize connectionState to "DISCONNECTED"
        connectionState = new AtomicReference<>("DISCONNECTED");
        // Thread 1: tries to connect
        Thread connector = new Thread(() -> {
            // TODO: Use compareAndSet to transition DISCONNECTED → CONNECTING
            // Only transition if current state is DISCONNECTED
            // Print success or failure
            boolean success = connectionState.compareAndSet("DISCONNECTED", "CONNECTING");// TODO: compareAndSet("DISCONNECTED", "CONNECTING")
            System.out.println("Connector: DISCONNECTED → CONNECTING: " + success);

            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

            // TODO: Use compareAndSet to transition CONNECTING → CONNECTED
            success = connectionState.compareAndSet("CONNECTING", "CONNECTED"); // TODO: compareAndSet("CONNECTING", "CONNECTED")
            System.out.println("Connector: CONNECTING → CONNECTED: " + success);
        }, "Connector");

        // Thread 2: also tries to connect (should fail since Thread 1 already started)
        Thread connector2 = new Thread(() -> {
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

            // TODO: Use compareAndSet — this should FAIL because state is already CONNECTING
            boolean success = connectionState.compareAndSet("DISCONNECTED", "CONNECTING"); // TODO: compareAndSet("DISCONNECTED", "CONNECTING")
            System.out.println("Connector2: DISCONNECTED → CONNECTING: " + success + " (expected false)");
        }, "Connector2");

        connector.start();
        connector2.start();
        connector.join();
        connector2.join();

        System.out.println("Final state: " + connectionState);
    }

    // =============================================
    // EXERCISE 4: getAndUpdate / updateAndGet
    // =============================================
    // Use getAndUpdate to implement a thread-safe "max tracker"
    // Multiple threads report values, track the maximum seen.

    static AtomicInteger maxValue = null;// TODO: Initialize with Integer.MIN_VALUE

    static void exercise4_maxTracker() throws InterruptedException {
        // TODO: Initialize maxValue to Integer.MIN_VALUE
        maxValue = new AtomicInteger(Integer.MIN_VALUE);

        int[][] threadValues = {
            {3, 7, 1, 9, 2},
            {5, 8, 4, 6, 10},
            {15, 2, 11, 1, 3}
        };

        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            final int[] values = threadValues[i];
            threads[i] = new Thread(() -> {
                for (int val : values) {
                    maxValue.updateAndGet(current -> Math.max(current, val));
                    // TODO: Use updateAndGet to atomically update max
                    // Hint: maxValue.updateAndGet(current -> Math.max(current, val));
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Max value (should be 15): " + maxValue);
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Exercise 1: Fix Race Condition with AtomicInteger ===");
        exercise1_fixRaceCondition();

        System.out.println("\n=== Exercise 2: Page View Counter with AtomicLong ===");
        exercise2_pageViewCounter();

        System.out.println("\n=== Exercise 3: Lock-free State Machine with AtomicReference ===");
        exercise3_atomicReference();

        System.out.println("\n=== Exercise 4: Max Tracker with getAndUpdate ===");
        exercise4_maxTracker();
    }
}