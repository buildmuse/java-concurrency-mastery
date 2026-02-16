package com.buildmuse.concurrency.level3_communication;

import java.util.LinkedList;
import java.util.Queue;

/**
 * PROBLEM: Producer-Consumer Pattern
 * 
 * Implement classic producer-consumer using wait/notify.
 * Producer adds items to queue (max capacity 5).
 * Consumer removes items from queue.
 * 
 * EXPECTED OUTPUT:
 * Producer: Produced 1, Queue size: 1
 * Consumer: Consumed 1, Queue size: 0
 * Producer: Produced 2, Queue size: 1
 * Producer: Produced 3, Queue size: 2
 * Consumer: Consumed 2, Queue size: 1
 * ...
 * 
 * LEARNING:
 * - wait/notify for coordination
 * - Always use while loop with wait (spurious wakeups)
 * - notifyAll vs notify
 */
public class Q02_ProducerConsumer {
    
    static class SharedQueue {
        private final Queue<Integer> queue = new LinkedList<>();
        private final int capacity;
        
        public SharedQueue(int capacity) {
            this.capacity = capacity;
        }
        
        public synchronized void produce(int item) throws InterruptedException {
            // TODO: Wait while queue is full
            // Use while loop: while (queue.size() == capacity) { wait(); }

            while(queue.size() == capacity) {
                wait();
            }
            
            queue.add(item);
            System.out.println("Producer: Produced " + item + ", Queue size: " + queue.size());
            
            // TODO: Notify waiting consumers
            notifyAll();
        }
        
        public synchronized int consume() throws InterruptedException {
            // TODO: Wait while queue is empty
            // Use while loop: while (queue.isEmpty()) { wait(); }
            while(queue.isEmpty()) {
                wait();
            }
            
            int item = queue.poll();
            System.out.println("Consumer: Consumed " + item + ", Queue size: " + queue.size());
            
            // TODO: Notify waiting producers
            notifyAll();
            
            return item;
        }
    }
    
    public static void main(String[] args) {
        SharedQueue sharedQueue = new SharedQueue(5);
        
        // Producer thread
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    sharedQueue.produce(i);
                    Thread.sleep(100); // Slow down production
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");
        
        // Consumer thread
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    sharedQueue.consume();
                    Thread.sleep(300); // Consume slower than produce
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");
        
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
1. produce(): 
   while (queue.size() == capacity) { wait(); }
   queue.add(item);
   notifyAll();

2. consume():
   while (queue.isEmpty()) { wait(); }
   int item = queue.poll();
   notifyAll();

3. ALWAYS use while, not if (handles spurious wakeups)
4. Use notifyAll() to wake all waiting threads

KEY CONCEPT: Producer-Consumer is fundamental concurrency pattern
*/
