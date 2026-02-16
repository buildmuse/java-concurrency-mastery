package com.buildmuse.concurrency.level7_patterns;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * PROBLEM: Connection Pool
 *
 * Implement a thread-safe connection pool that:
 * - Pre-creates a fixed number of connections
 * - Threads borrow and return connections
 * - Blocks if no connections available (with timeout)
 * - Tracks active vs idle connections
 *
 * This is what HikariCP, C3P0, and database connection pools do.
 *
 * EXPECTED OUTPUT:
 * Pool created with 3 connections
 * Thread-1: Borrowed Connection-1 (active: 1, idle: 2)
 * Thread-2: Borrowed Connection-2 (active: 2, idle: 1)
 * Thread-3: Borrowed Connection-3 (active: 3, idle: 0)
 * Thread-4: Waiting for connection...
 * Thread-1: Returned Connection-1 (active: 2, idle: 1)
 * Thread-4: Borrowed Connection-1 (active: 3, idle: 0)
 * ...
 *
 * LEARNING:
 * - BlockingQueue as a pool of reusable resources
 * - Borrow/return pattern
 * - Timeout-based waiting (poll with timeout)
 * - Connection pool internals (HikariCP, DBCP)
 */
public class Q03_ConnectionPool {

    // Simulates a database connection
    static class Connection {
        private final String name;
        private boolean inUse;

        public Connection(String name) {
            this.name = name;
            this.inUse = false;
        }

        public String getName() { return name; }

        public void execute(String query) {
            System.out.println(Thread.currentThread().getName() + ": Executing '" + query + "' on " + name);
            try {
                Thread.sleep(500); // simulate query execution
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        @Override
        public String toString() { return name; }
    }

    static class ConnectionPool {
        // TODO: Use a BlockingQueue to hold available connections
        private final BlockingQueue<Connection> pool = null;
        private final AtomicInteger activeCount = new AtomicInteger(0);
        private final int maxSize;

        public ConnectionPool(int size) {
            this.maxSize = size;
            // TODO: Initialize the BlockingQueue
            // TODO: Pre-create 'size' connections and add them to the pool
            // Hint: new LinkedBlockingQueue<>(size), then pool.offer(new Connection("Connection-" + i))
            System.out.println("Pool created with " + size + " connections");
        }

        // Borrow a connection (blocks up to 5 seconds if none available)
        public Connection borrow() throws InterruptedException {
            // TODO:
            // 1. Use pool.poll(timeout, TimeUnit) to wait for a connection
            // 2. If null (timeout), throw RuntimeException("Connection pool exhausted")
            // 3. Increment activeCount
            // 4. Print borrow info and return the connection
            System.out.println(Thread.currentThread().getName() + ": Waiting for connection...");
            return null;
        }

        // Return a connection to the pool
        public void release(Connection conn) {
            // TODO:
            // 1. Decrement activeCount
            // 2. Put the connection back in the pool using pool.offer()
            // 3. Print release info
        }

        public int getActiveCount() { return activeCount.get(); }
        public int getIdleCount() { return maxSize - activeCount.get(); }
    }

    public static void main(String[] args) throws InterruptedException {
        ConnectionPool pool = new ConnectionPool(3);

        // 5 threads competing for 3 connections
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            final int threadId = i + 1;
            threads[i] = new Thread(() -> {
                try {
                    Connection conn = pool.borrow();
                    conn.execute("SELECT * FROM users WHERE id=" + threadId);
                    pool.release(conn);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Thread-" + threadId);
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.println("\nAll done! Active: " + pool.getActiveCount() + ", Idle: " + pool.getIdleCount());
    }
}

/*
SOLUTION HINTS:
1. pool = new LinkedBlockingQueue<>(size)
2. In constructor: for (int i = 1; i <= size; i++) pool.offer(new Connection("Connection-" + i));
3. borrow():
   Connection conn = pool.poll(5, TimeUnit.SECONDS);
   if (conn == null) throw new RuntimeException("Connection pool exhausted - timeout after 5s");
   activeCount.incrementAndGet();
   return conn;
4. release():
   activeCount.decrementAndGet();
   pool.offer(conn);

KEY CONCEPT: This is the core of HikariCP and similar connection pools.
BlockingQueue naturally handles the waiting and thread-safety.
Real pools add: health checks, max lifetime, leak detection, metrics.
*/