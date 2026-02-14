# Java Concurrency Mastery

A hands-on, scenario-based repository to master Java concurrency from basics to advanced concepts.

## Philosophy

**NO THEORY. ONLY CODE.**

Each problem is a real-world scenario requiring you to implement thread-safe solutions. Learn by building, not by reading.

## Structure

### Level 1: Basics (Thread Creation & Lifecycle)
- Thread creation (Thread class vs Runnable)
- Thread lifecycle states
- sleep, join, interrupt
- Daemon threads

### Level 2: Synchronization
- Race conditions
- synchronized methods vs blocks
- volatile keyword
- Deadlock scenarios

### Level 3: Inter-Thread Communication ⭐
- wait/notify mechanism
- Producer-Consumer pattern
- Thread data exchange
- **Covers your interview gap!**

### Level 4: Concurrent Collections
- BlockingQueue
- ConcurrentHashMap
- CopyOnWriteArrayList
- Thread-safe data structures

### Level 5: Executors Framework
- ThreadPoolExecutor
- Callable and Future
- CompletableFuture
- Custom thread pools

### Level 6: Advanced Synchronizers
- CountDownLatch
- CyclicBarrier
- Semaphore
- ReentrantLock, ReadWriteLock
- Phaser

### Level 7: Real-World Patterns
- Bounded buffer
- Rate limiter
- Connection pool
- Task scheduler

## How to Use

1. **Start from Level 1** - Even if basics seem trivial, they build muscle memory
2. **Read problem statement** - Understand what needs to be built
3. **Implement solution** - Fill in TODOs in skeleton code
4. **Run tests** - Validate your implementation
5. **Review solution notes** - After solving, check key learnings

## Running Problems

Each problem is a standalone Java file with:
- Problem description in comments
- Skeleton code with TODOs
- main() method to test manually
- Expected output

```bash
# Compile and run a problem
cd src/main/java
javac com/buildmuse/concurrency/level1_basics/Q01_CreateThread.java
java com.buildmuse.concurrency.level1_basics.Q01_CreateThread
```

## Progress Tracking

- [ ] Level 1: Basics (8 problems)
- [ ] Level 2: Synchronization (10 problems)
- [ ] Level 3: Communication (12 problems)
- [ ] Level 4: Collections (8 problems)
- [ ] Level 5: Executors (10 problems)
- [ ] Level 6: Advanced (12 problems)
- [ ] Level 7: Patterns (10 problems)

**Total: 70 problems**

## Interview Preparation

After completing Level 3, you'll be able to:
- Implement thread communication mechanisms
- Build producer-consumer patterns
- Handle data exchange between threads
- Debug concurrency issues

Level 6+ prepares you for senior-level questions.

## Tips

- **Don't skip levels** - Each builds on previous concepts
- **Run code, don't just read** - Concurrency is about behavior
- **Break things intentionally** - Remove synchronization to see race conditions
- **Time yourself** - Interview problems should take 15-20 minutes

---

Let's master concurrency. Start with Level 1, Problem 1.