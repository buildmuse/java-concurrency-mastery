package com.buildmuse.concurrency.level4_concurrent_collections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.List;
import java.util.ArrayList;

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

        System.out.println("=== Competing Consumers (like Kafka consumer group) ===");
        competingConsumers();

        System.out.println("\n=== Pub/Sub (like separate Kafka consumer groups) ===");
        pubSub();
    }

    // Original: consumers compete for messages (each message goes to ONE consumer)
    static void competingConsumers() {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        Thread t1 = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    queue.put(i);
                    System.out.println("Producer: Produced " + i);
                    Thread.sleep(100);
                }
                queue.put(-1);
                queue.put(-1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        Thread t2 = new Thread(() -> {
            try {
                while (true) {
                    int item = queue.take();
                    if (item < 0) break;
                    System.out.println("Consumer1: Consumed " + item);
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer-1");

        Thread t3 = new Thread(() -> {
            try {
                while (true) {
                    int item = queue.take();
                    if (item < 0) break;
                    System.out.println("Consumer2: Consumed " + item);
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer-2");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Pub/Sub: every subscriber gets EVERY message
    // Each subscriber has its own queue. Producer fans out to all queues.
    static void pubSub() {
        List<BlockingQueue<Integer>> subscriberQueues = new ArrayList<>();
        BlockingQueue<Integer> sub1Queue = new LinkedBlockingQueue<>();
        BlockingQueue<Integer> sub2Queue = new LinkedBlockingQueue<>();
        subscriberQueues.add(sub1Queue);
        subscriberQueues.add(sub2Queue);

        // Producer fans out to ALL subscriber queues
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Producer: Published " + i);
                    for (BlockingQueue<Integer> q : subscriberQueues) {
                        q.put(i);
                    }
                    Thread.sleep(100);
                }
                for (BlockingQueue<Integer> q : subscriberQueues) {
                    q.put(-1); // poison pill to each subscriber
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Publisher");

        // Subscriber 1 - gets ALL messages
        Thread sub1 = new Thread(() -> {
            try {
                while (true) {
                    int item = sub1Queue.take();
                    if (item < 0) break;
                    System.out.println("Subscriber1: Received " + item);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Subscriber-1");

        // Subscriber 2 - also gets ALL messages
        Thread sub2 = new Thread(() -> {
            try {
                while (true) {
                    int item = sub2Queue.take();
                    if (item < 0) break;
                    System.out.println("Subscriber2: Received " + item);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Subscriber-2");

        producer.start();
        sub1.start();
        sub2.start();

        try {
            producer.join();
            sub1.join();
            sub2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
