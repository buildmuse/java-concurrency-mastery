package com.buildmuse.concurrency.level7_patterns;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * PROBLEM: Bounded Buffer (Fixed-size thread-safe buffer)
 *
 * Implement a bounded buffer from scratch using ReentrantLock and Conditions.
 * This is what ArrayBlockingQueue does internally.
 *
 * The buffer has a fixed capacity. Producers block when full, consumers block when empty.
 *
 * EXPECTED OUTPUT:
 * Producer-1: Put 0 [size=1]
 * Producer-1: Put 1 [size=2]
 * Producer-1: Put 2 [size=3]
 * Producer-1: BLOCKED (buffer full)
 * Consumer-1: Took 0 [size=2]
 * Producer-1: Put 3 [size=3]
 * Consumer-1: Took 1 [size=2]
 * ...
 *
 * LEARNING:
 * - ReentrantLock with multiple Conditions (notFull, notEmpty)
 * - How ArrayBlockingQueue works internally
 * - Circular buffer implementation
 * - Why separate conditions are better than a single lock
 */
public class Q01_BoundedBuffer {

    static class BoundedBuffer<T> {
        private final Object[] items;
        private int putIndex, takeIndex, count;

        // TODO: Create a ReentrantLock
        private final ReentrantLock lock = null;

        // TODO: Create two Conditions from the lock:
        // - notFull: signaled when an item is removed (space available)
        // - notEmpty: signaled when an item is added (data available)
        private final Condition notFull = null;
        private final Condition notEmpty = null;

        public BoundedBuffer(int capacity) {
            items = new Object[capacity];
        }

        public void put(T item) throws InterruptedException {
            // TODO:
            // 1. Acquire the lock
            // 2. While buffer is full (count == items.length), await on notFull
            // 3. Add item at putIndex, advance putIndex circularly, increment count
            // 4. Signal notEmpty (a consumer may be waiting)
            // 5. Release the lock in a finally block
        }

        @SuppressWarnings("unchecked")
        public T take() throws InterruptedException {
            // TODO:
            // 1. Acquire the lock
            // 2. While buffer is empty (count == 0), await on notEmpty
            // 3. Take item at takeIndex, advance takeIndex circularly, decrement count
            // 4. Signal notFull (a producer may be waiting)
            // 5. Release the lock in a finally block
            return null;
        }

        public int size() {
            return count;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BoundedBuffer<Integer> buffer = new BoundedBuffer<>(3);

        // Producer: produces 10 items
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    buffer.put(i);
                    System.out.println("Producer: Put " + i + " [size=" + buffer.size() + "]");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer-1");

        // Consumer: consumes 10 items (slower than producer)
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(200); // slower consumer
                    int item = buffer.take();
                    System.out.println("Consumer: Took " + item + " [size=" + buffer.size() + "]");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer-1");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        System.out.println("Done! Buffer size: " + buffer.size());
    }
}

/*
SOLUTION HINTS:
1. lock = new ReentrantLock()
2. notFull = lock.newCondition(), notEmpty = lock.newCondition()
3. put(): lock.lock() → while(count == items.length) notFull.await() → items[putIndex] = item
         → putIndex = (putIndex + 1) % items.length → count++ → notEmpty.signal() → lock.unlock()
4. take(): lock.lock() → while(count == 0) notEmpty.await() → item = items[takeIndex]
         → takeIndex = (takeIndex + 1) % items.length → count-- → notFull.signal() → lock.unlock()
5. Always use while loop (not if) for condition checks — guards against spurious wakeups

KEY CONCEPT: This is exactly how ArrayBlockingQueue is implemented.
Two conditions on one lock give fine-grained control over producer vs consumer waiting.
*/