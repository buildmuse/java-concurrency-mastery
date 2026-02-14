package com.buildmuse.concurrency.level5_executors;

import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PROBLEM: Callable and Future
 * 
 * Submit 5 Callable tasks that return computed values.
 * Collect results using Future.
 * 
 * EXPECTED OUTPUT:
 * Submitting task 1
 * Submitting task 2
 * ...
 * Result from task 1: 2
 * Result from task 2: 4
 * Result from task 3: 6
 * Result from task 4: 8
 * Result from task 5: 10
 * 
 * LEARNING:
 * - Callable<V> interface (returns value)
 * - Future<V> for async result
 * - future.get() blocks until result available
 * - submit() vs execute()
 */
public class Q02_CallableAndFuture {
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // TODO: Create list to store Futures
        List<Future<Integer>> futures = new ArrayList<>();
        
        // TODO: Submit 5 Callable tasks
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            System.out.println("Submitting task " + taskId);
            
            // TODO: Create Callable that returns taskId * 2
            Callable<Integer> task = null;
            
            // TODO: Submit and store Future
            
        }
        
        // TODO: Collect results from Futures
        for (int i = 0; i < futures.size(); i++) {
            // TODO: Use future.get() to retrieve result (blocks if not ready)
            
            System.out.println("Result from task " + (i + 1) + ": " + "TODO" /* result */ );
        }
        
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}

/*
SOLUTION HINTS:
1. Callable<Integer> task = () -> {
       Thread.sleep(500);
       return taskId * 2;
   };

2. Future<Integer> future = executor.submit(task);
   futures.add(future);

3. Integer result = futures.get(i).get();

KEY CONCEPT: Callable returns value, Future holds async result
*/
