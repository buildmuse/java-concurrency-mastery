package com.buildmuse.concurrency.level4_concurrent_collections;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * PROBLEM: Advanced ConcurrentHashMap Operations
 *
 * Practice merge, replace, bulk operations, and real-world patterns
 * like thread-safe counters and multimaps.
 *
 * EXERCISES:
 * 1. Word frequency counter using merge()
 * 2. Optimistic update using replace()
 * 3. Thread-safe multimap using computeIfAbsent
 * 4. Bulk parallel operations (forEach, search, reduce)
 *
 * LEARNING:
 * - merge() for atomic read-modify-write
 * - replace() for compare-and-swap on maps
 * - computeIfAbsent for building complex structures
 * - Parallel bulk operations on ConcurrentHashMap
 */
public class Q04_ConcurrentHashMapAdvanced {

    // =============================================
    // EXERCISE 1: Word frequency counter using merge()
    // =============================================
    // Multiple threads count word frequencies concurrently.
    // Use merge() to atomically increment counts.

    static void exercise1_wordFrequency() throws InterruptedException {
        ConcurrentHashMap<String, Integer> wordCount = new ConcurrentHashMap<>();

        String[][] threadWords = {
            {"java", "thread", "java", "lock", "thread", "java"},
            {"thread", "atomic", "java", "lock", "atomic"},
            {"java", "concurrent", "thread", "java", "concurrent", "lock"}
        };

        // Expected: java=6, thread=4, lock=3, atomic=2, concurrent=2

        Thread[] threads = new Thread[3];

        for(int i = 0; i < threadWords.length; i++) {
            final String[] words = threadWords[i];
            threads[i] = new Thread(() -> {
                for(String s : words) {
                    wordCount.merge(s, 1, Integer::sum);
                }
            });

            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Word counts: " + wordCount);
        System.out.println("Expected: java=6, thread=4, lock=3, atomic=2, concurrent=2");
    }

    // =============================================
    // EXERCISE 2: Optimistic update using replace()
    // =============================================
    // Simulate a bank account balance update.
    // Use replace(key, expectedOldValue, newValue) — only updates if current value matches.

    static void exercise2_optimisticUpdate() throws InterruptedException {
        ConcurrentHashMap<String, Integer> accounts = new ConcurrentHashMap<>();
        accounts.put("alice", 1000);

        // Thread 1: Withdraw 200 (expects balance to be 1000)
        Thread t1 = new Thread(() -> {
            Integer current = accounts.get("alice");
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

            // TODO: Use replace(key, oldValue, newValue)
            // Only withdraw if balance is still what we read
            boolean success = accounts.replace("alice", current, current-200); // TODO: accounts.replace("alice", current, current - 200)
            System.out.println("T1 withdraw 200: " + (success ? "SUCCESS" : "FAILED (balance changed!)"));
        }, "T1");

        // Thread 2: Withdraw 300 (also expects balance to be 1000)
        Thread t2 = new Thread(() -> {
            Integer current = accounts.get("alice");
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

            // TODO: Use replace(key, oldValue, newValue)
            boolean success = accounts.replace("alice", current, current-300 );
            System.out.println("T2 withdraw 300: " + (success ? "SUCCESS" : "FAILED (balance changed!)"));
        }, "T2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final balance: " + accounts.get("alice"));
        System.out.println("(Only one withdrawal should succeed — prevents double-spending)");
    }

    // =============================================
    // EXERCISE 3: Thread-safe multimap
    // =============================================
    // Build a map where each key has a LIST of values.
    // Multiple threads add tags to users concurrently.
    // Use computeIfAbsent to lazily create the list.

    static void exercise3_multimap() throws InterruptedException {
        ConcurrentHashMap<String, List<String>> userTags = new ConcurrentHashMap<>();

        Thread t1 = new Thread(() -> {
            // TODO: Use computeIfAbsent to get/create the list, then add to it
            userTags.computeIfAbsent("alice", (k) -> new CopyOnWriteArrayList<>()).add("admin");
            userTags.computeIfAbsent("alice", k -> new CopyOnWriteArrayList<>()).add("active");
            userTags.computeIfAbsent("bob", k -> new CopyOnWriteArrayList<>()).add("user");
            // Add tags: alice→"admin", alice→"active", bob→"user"
        }, "T1");

        Thread t2 = new Thread(() -> {
            userTags.computeIfAbsent("alice", k -> new CopyOnWriteArrayList<>()).add("premium");
            userTags.computeIfAbsent("bob", k -> new CopyOnWriteArrayList<>()).add("active");
            userTags.computeIfAbsent("bob", k -> new CopyOnWriteArrayList<>()).add("premium");
            // TODO: Add tags: alice→"premium", bob→"active", bob→"premium"
        }, "T2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("User tags: " + userTags);
        System.out.println("Expected: alice=[admin, active, premium], bob=[user, active, premium]");
    }

    // =============================================
    // EXERCISE 4: Bulk parallel operations
    // =============================================
    // Use ConcurrentHashMap's built-in parallel operations.

    static void exercise4_bulkOperations() {
        ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<>();
        scores.put("alice", 85);
        scores.put("bob", 92);
        scores.put("charlie", 78);
        scores.put("diana", 95);
        scores.put("eve", 88);

        // TODO 1: Use forEach to print all entries
        // Hint: scores.forEach(1, (name, score) -> System.out.println(name + ": " + score));
        System.out.println("All scores:");
        scores.forEach(1, (name, score) -> System.out.println(name + " " + score));

        // TODO 2: Use search to find the first student with score > 90
        // Hint: scores.search(1, (name, score) -> score > 90 ? name : null);
        String topStudent = scores.search(1, (name, score) -> score > 90 ? name : null); // TODO
        System.out.println("A student with score > 90: " + topStudent);

        // TODO 3: Use reduce to find the total score
        // Hint: scores.reduce(1, (name, score) -> score, Integer::sum);
        Integer totalScore = scores.reduce(1, (name, score) -> score, Integer::sum); // TODO
        System.out.println("Total score: " + totalScore);

        // TODO 4: Use reduceValues to find the max score
        // Hint: scores.reduceValues(1, Integer::max);
        Integer maxScore = scores.reduceValues(1,Integer::max); // TODO
        System.out.println("Max score: " + maxScore);
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Exercise 1: Word Frequency Counter (merge) ===");
        exercise1_wordFrequency();

        System.out.println("\n=== Exercise 2: Optimistic Update (replace) ===");
        exercise2_optimisticUpdate();

        System.out.println("\n=== Exercise 3: Thread-safe Multimap (computeIfAbsent) ===");
        exercise3_multimap();

        System.out.println("\n=== Exercise 4: Bulk Parallel Operations ===");
        exercise4_bulkOperations();
    }
}