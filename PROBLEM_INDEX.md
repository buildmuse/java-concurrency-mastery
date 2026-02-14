# Java Concurrency Mastery - Problem Index

## Level 1: Basics (8 problems)
✅ Q01_CreateThread.java - Create thread using Thread class
✅ Q02_ThreadVsRunnable.java - Thread creation with Runnable interface
✅ Q03_ThreadLifecycle.java - Understanding thread states
✅ Q04_ThreadInterrupt.java - Thread interruption mechanism
✅ Q05_JoinTimeout.java - Join with timeout
✅ Q06_DaemonThread.java - Daemon vs user threads
✅ Q07_ThreadNaming.java - Thread naming and identity
✅ Q08_ThreadPriority.java - Thread priority

## Level 2: Synchronization (5 problems created, 5 more to add)
✅ Q01_RaceCondition.java - Demonstrate race condition
✅ Q02_SynchronizedMethod.java - Fix race condition with synchronized
✅ Q03_SynchronizedBlock.java - Fine-grained synchronization
✅ Q04_VolatileKeyword.java - Visibility with volatile
✅ Q05_Deadlock.java - Deadlock scenario and prevention

### TODO for Level 2:
- Static synchronization
- Double-checked locking
- Object vs class level locks
- Monitor concept
- Happens-before relationship

## Level 3: Communication (4 problems created, 8 more to add) ⭐ YOUR FOCUS
✅ Q01_WaitNotifyBasics.java - Basic wait/notify
✅ Q02_ProducerConsumer.java - Producer-Consumer pattern
✅ Q03_ThreadCommunication.java - **YOUR INTERVIEW QUESTION!**
✅ Q04_PrintSequence.java - Multi-thread coordination

### TODO for Level 3:
- Even-odd printing (2 threads)
- Print in order (3 threads with conditions)
- Transfer data between threads
- Bounded buffer implementation
- Multiple producer-consumer
- notify() vs notifyAll()
- Spurious wakeups handling
- Inter-thread messaging queue

## Level 4: Concurrent Collections (2 problems created, 6 more to add)
✅ Q01_BlockingQueue.java - BlockingQueue for producer-consumer
✅ Q02_ConcurrentHashMap.java - Thread-safe map operations

### TODO for Level 4:
- CopyOnWriteArrayList
- PriorityBlockingQueue
- DelayQueue
- ArrayBlockingQueue vs LinkedBlockingQueue
- ConcurrentSkipListMap
- TransferQueue

## Level 5: Executors (2 problems created, 8 more to add)
✅ Q01_ExecutorService.java - Thread pool basics
✅ Q02_CallableAndFuture.java - Callable and Future

### TODO for Level 5:
- ScheduledExecutorService
- CompletableFuture
- invokeAll / invokeAny
- ForkJoinPool
- Custom ThreadPoolExecutor
- Future cancellation
- Exception handling in executors
- ThreadFactory

## Level 6: Advanced Synchronizers (4 problems created, 8 more to add)
✅ Q01_CountDownLatch.java - One-time coordination
✅ Q02_CyclicBarrier.java - Repeating synchronization
✅ Q03_Semaphore.java - Resource limiting
✅ Q04_ReentrantLock.java - Advanced locking

### TODO for Level 6:
- ReadWriteLock
- StampedLock
- Condition variables
- Phaser
- Exchanger
- LockSupport
- AbstractQueuedSynchronizer
- Custom lock implementation

## Level 7: Real-World Patterns (0 problems created, 10 to add)

### TODO for Level 7:
- Thread-safe singleton (various approaches)
- Object pool pattern
- Rate limiter
- Circuit breaker
- Task scheduler
- Event bus
- Work stealing
- Leader-follower pattern
- Thread-local pattern
- Parallel divide and conquer

---

## Quick Start
1. Start with Level 1, Q01
2. Read problem statement
3. Fill in TODOs
4. Run and verify output
5. Move to next problem

## Critical for Interview Prep
- **Level 3 Q03** - Exactly your interview question
- Level 3 Q02 - Producer-Consumer (very common)
- Level 3 Q04 - Print sequence (common interview pattern)
- Level 6 Q01-Q04 - Advanced tools for senior roles

**Current Status: 25/70 problems created**

Focus on completing Level 1-3 first (your interview foundation).
Then move to Level 4-7 for senior-level concepts.
