package com.buildmuse.concurrency.level4_concurrent_collections;

import java.util.concurrent.ConcurrentHashMap;

/**
 * PROBLEM: ConcurrentHashMap for Thread-Safe Cache
 * 
 * Implement a thread-safe cache using ConcurrentHashMap.
 * Multiple threads read/write concurrently.
 * Use atomic operations: putIfAbsent, computeIfAbsent.
 * 
 * EXPECTED OUTPUT:
 * Thread-1: Computing value for key1
 * Thread-2: Computing value for key2
 * Thread-1: Retrieved: Value-key1
 * Thread-2: Retrieved: Value-key2
 * Thread-1: Cache hit for key1: Value-key1
 * 
 * LEARNING:
 * - ConcurrentHashMap vs synchronized HashMap
 * - Atomic operations: putIfAbsent, computeIfAbsent
 * - Lock striping (internal implementation)
 */
public class Q02_ConcurrentHashMap {
    
    static class Cache {
        // TODO: Create ConcurrentHashMap
        private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
        
        // Simulate expensive computation
        private String computeValue(String key) {
            System.out.println(Thread.currentThread().getName() + ": Computing value for " + key);
            try {
                Thread.sleep(1000); // Simulate expensive operation
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Value-" + key;
        }
        
        // TODO: Implement get with computeIfAbsent
        public String get(String key) {
            // Use computeIfAbsent - only computes if key absent (atomic operation)

            return cache.computeIfAbsent(key, (k) -> computeValue(k));
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        Cache cache = new Cache();
        
        // TODO: Create 3 threads
        // Thread-1: get("key1") twice
        // Thread-2: get("key2") once
        // Thread-3: get("key1") once
        
        Thread t1 = new Thread(() -> {
            cache.get("key1");
            cache.get("key1"); // Should be cache hit
        }, "Thread-1");
        
        Thread t2 = new Thread(() -> {
            cache.get("key2");
        }, "Thread-2");
        
        Thread t3 = new Thread(() -> {
            try {
                Thread.sleep(1500); // Start after key1 is cached
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            String value = cache.get("key1"); // Cache hit
            System.out.println("Thread-3: Cache hit for key1: " + value);
        }, "Thread-3");
        
        t1.start();
        t2.start();
        t3.start();
        
        t1.join();
        t2.join();
        t3.join();
    }
}

/*
SOLUTION HINTS:
1. new ConcurrentHashMap<>()
2. cache.computeIfAbsent(key, k -> computeValue(k))
3. computeIfAbsent is atomic - only one thread computes
4. Other methods: putIfAbsent, replace, remove (with value check)

KEY CONCEPT: ConcurrentHashMap provides thread-safe operations
without locking entire map
*/
