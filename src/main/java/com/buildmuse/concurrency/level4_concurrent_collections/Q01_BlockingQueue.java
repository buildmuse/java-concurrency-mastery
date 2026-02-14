package com.buildmuse.concurrency.level4_concurrent_collections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * PROBLEM: Producer-Consumer with BlockingQueue
 * 
 * Reimplement producer-consumer using BlockingQueue instead of wait/notify.
 * BlockingQueue handles all synchronization automatically.
 * 
 * EXPECTED OUTPUT:
 * Producer: Produced 1
 * Consumer: Consumed 1
 * Producer: Produced 2
 * Producer: Produced 3
 * Consumer: Consumed 2
 * ...
 * 
 * LEARNING:
 * - BlockingQueue interface (put/take)
 * - LinkedBlockingQueue implementation
 * - Higher-level abstraction over wait/notify
 * - Thread-safe by design
 */
public class Q01_BlockingQueue {
    
    public static void main(String[] args) {
        // TODO: Create BlockingQueue with capacity 5
        BlockingQueue<Integer> queue = null;
        
        // Producer
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    // TODO: Use queue.put(i) - blocks if queue is full
                    
                    System.out.println("Producer: Produced " + i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        // Consumer
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    // TODO: Use queue.take() - blocks if queue is empty
                    
                    System.out.println("Consumer: Consumed " + "TODO" /* item */);
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        producer.start();
        consumer.start();
        
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

/*
SOLUTION HINTS:
1. new LinkedBlockingQueue<>(5) for capacity 5
2. queue.put(item) - blocks when full
3. queue.take() - blocks when empty
4. No need for synchronized, wait, notify!

KEY CONCEPT: Use BlockingQueue instead of manual wait/notify
Much simpler and less error-prone
*/
