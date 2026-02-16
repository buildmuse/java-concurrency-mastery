package com.buildmuse.concurrency.level7_patterns;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * PROBLEM: Priority Task Scheduler
 *
 * Implement a task scheduler where:
 * - Tasks have priorities (HIGH=1, MEDIUM=2, LOW=3)
 * - Worker threads pick up highest-priority tasks first
 * - Multiple producers submit tasks, multiple workers process them
 * - Graceful shutdown with poison pill or flag
 *
 * This is similar to how thread pools with priority queues work,
 * and how job schedulers (Quartz, Celery) prioritize work.
 *
 * EXPECTED OUTPUT:
 * Submitted: [HIGH] Critical bugfix
 * Submitted: [LOW] Update docs
 * Submitted: [MEDIUM] Add feature
 * Submitted: [HIGH] Security patch
 * Worker-1: Processing [HIGH] Critical bugfix
 * Worker-2: Processing [HIGH] Security patch
 * Worker-1: Processing [MEDIUM] Add feature
 * Worker-2: Processing [LOW] Update docs
 *
 * LEARNING:
 * - PriorityBlockingQueue for priority-based scheduling
 * - Comparable interface for task ordering
 * - Worker thread pool pattern
 * - Graceful shutdown strategies
 */
public class Q04_TaskScheduler {

    // TODO: Implement Task that implements Comparable<Task>
    // Tasks with lower priority number should be processed first (HIGH=1 before LOW=3)
    static class Task implements Comparable<Task> {
        enum Priority { HIGH(1), MEDIUM(2), LOW(3);
            final int value;
            Priority(int value) { this.value = value; }
        }

        private final String name;
        private final Priority priority;
        private final long submittedAt;

        public Task(String name, Priority priority) {
            this.name = name;
            this.priority = priority;
            this.submittedAt = System.nanoTime();
        }

        // TODO: Implement compareTo
        // Compare by priority first. If same priority, earlier submission goes first (FIFO).
        @Override
        public int compareTo(Task other) {
            // Hint: Compare priority values, then submittedAt for tie-breaking
            return 0;
        }

        public void execute() {
            System.out.println(Thread.currentThread().getName()
                + ": Processing [" + priority + "] " + name);
            try {
                Thread.sleep(300); // simulate work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        @Override
        public String toString() {
            return "[" + priority + "] " + name;
        }
    }

    static class TaskScheduler {
        // TODO: Create a PriorityBlockingQueue<Task>
        private final PriorityBlockingQueue<Task> queue = null;
        private final Thread[] workers;
        private final AtomicBoolean running = new AtomicBoolean(true);

        public TaskScheduler(int workerCount) {
            // TODO: Initialize the PriorityBlockingQueue
            workers = new Thread[workerCount];

            for (int i = 0; i < workerCount; i++) {
                workers[i] = new Thread(() -> {
                    // TODO: Worker loop
                    // While running (or queue not empty):
                    //   1. Poll from queue with timeout (e.g., 500ms)
                    //   2. If task != null, execute it
                    //   3. If null and not running, break
                }, "Worker-" + (i + 1));
            }
        }

        public void start() {
            for (Thread w : workers) w.start();
        }

        public void submit(Task task) {
            // TODO: Add task to the queue
            System.out.println("Submitted: " + task);
        }

        public void shutdown() throws InterruptedException {
            // TODO:
            // 1. Set running to false
            // 2. Wait for all workers to finish (join)
            System.out.println("\nScheduler shutting down...");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TaskScheduler scheduler = new TaskScheduler(2);
        scheduler.start();

        // Submit tasks in mixed order — scheduler should process by priority
        scheduler.submit(new Task("Update documentation", Task.Priority.LOW));
        scheduler.submit(new Task("Critical bugfix", Task.Priority.HIGH));
        scheduler.submit(new Task("Add search feature", Task.Priority.MEDIUM));
        scheduler.submit(new Task("Security patch", Task.Priority.HIGH));
        scheduler.submit(new Task("Refactor utils", Task.Priority.LOW));
        scheduler.submit(new Task("API endpoint", Task.Priority.MEDIUM));

        // Let workers process
        Thread.sleep(3000);

        scheduler.shutdown();
        System.out.println("All tasks completed!");
    }
}

/*
SOLUTION HINTS:
1. compareTo:
   int result = Integer.compare(this.priority.value, other.priority.value);
   if (result == 0) result = Long.compare(this.submittedAt, other.submittedAt);
   return result;

2. queue = new PriorityBlockingQueue<>()

3. Worker loop:
   while (running.get() || !queue.isEmpty()) {
       Task task = queue.poll(500, TimeUnit.MILLISECONDS);
       if (task != null) task.execute();
   }

4. submit: queue.offer(task)

5. shutdown:
   running.set(false);
   for (Thread w : workers) w.join();

KEY CONCEPT: PriorityBlockingQueue + worker threads = priority task scheduler.
This pattern is used in job queues (Sidekiq, Celery), OS schedulers, and
ScheduledThreadPoolExecutor internally.

BONUS: Add these import for the worker loop:
import java.util.concurrent.TimeUnit;
*/