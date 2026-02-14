package com.buildmuse.concurrency.level1_basics;

/**
 * PROBLEM: Create Thread Using Runnable
 * 
 * Create TWO threads that print even and odd numbers from 1-10.
 * Use Runnable interface instead of extending Thread.
 * Both threads should run concurrently.
 * 
 * EXPECTED OUTPUT (order may vary):
 * Even: 2
 * Odd: 1
 * Even: 4
 * Odd: 3
 * Even: 6
 * Odd: 5
 * ...
 * 
 * LEARNING:
 * - Runnable interface
 * - Lambda expressions for Runnable
 * - Multiple threads running concurrently
 */
public class Q02_ThreadVsRunnable {
    
    public static void main(String[] args) throws InterruptedException {
        
        // TODO: Create Runnable for even numbers (2, 4, 6, 8, 10)
        Runnable evenTask = () -> {
            for(int i = )
        };
        
        
        // TODO: Create Runnable for odd numbers (1, 3, 5, 7, 9)
        Runnable oddTask = null; // Use lambda
        
        
        // TODO: Create Thread objects passing Runnable instances
        
        
        // TODO: Start both threads
        
        
        // TODO: Wait for both to complete
        
        
        System.out.println("Both threads completed");
    }
}

/*
SOLUTION HINTS:
1. Use lambda: () -> { ... }
2. Loop and print even/odd numbers
3. Add small sleep to see interleaving
4. new Thread(runnable)
5. start() both threads
6. join() both threads

KEY CONCEPT: Runnable is preferred over Thread extension (better OOP design)
*/
