package com.buildmuse.concurrency.level2_synchronization;

/**
 * PROBLEM: Synchronized Block
 * 
 * Implement a BankAccount with deposit/withdraw operations.
 * Use synchronized blocks (not synchronized methods) for fine-grained control.
 * Only synchronize the critical section (balance update).
 * 
 * EXPECTED OUTPUT:
 * Starting balance: 1000
 * Thread-1: Depositing 50
 * Thread-2: Withdrawing 30
 * Thread-1: Depositing 50
 * Thread-2: Withdrawing 30
 * ...
 * Final balance: 1200
 * 
 * LEARNING:
 * - synchronized(object) { } blocks
 * - Lock on specific object (can use 'this' or separate lock object)
 * - Fine-grained synchronization
 */
public class Q03_SynchronizedBlock {
    
    static class BankAccount {
        private int balance;
        private final Object lock = new Object(); // Explicit lock object
        
        public BankAccount(int initialBalance) {
            this.balance = initialBalance;
        }
        
        public void deposit(int amount) {
            // TODO: Synchronize only the balance update using lock object
            // synchronized (lock) { ... }
            
            System.out.println(Thread.currentThread().getName() + ": Depositing " + amount);
            
            // Simulate some processing (outside critical section)
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            // TODO: Synchronized block here
            
                balance += amount;
            
        }
        
        public void withdraw(int amount) {
            // TODO: Similar to deposit, synchronize balance update
            
            System.out.println(Thread.currentThread().getName() + ": Withdrawing " + amount);
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            // TODO: Synchronized block here
            
                balance -= amount;
            
        }
        
        public int getBalance() {
            synchronized (lock) {
                return balance;
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount(1000);
        System.out.println("Starting balance: " + account.getBalance());
        
        // TODO: Create thread that deposits 50, 10 times
        Thread depositor = new Thread(() -> {
            
        }, "Thread-1");
        
        // TODO: Create thread that withdraws 30, 10 times
        Thread withdrawer = new Thread(() -> {
            
        }, "Thread-2");
        
        depositor.start();
        withdrawer.start();
        
        depositor.join();
        withdrawer.join();
        
        // Final: 1000 + (50 * 10) - (30 * 10) = 1200
        System.out.println("Final balance: " + account.getBalance());
    }
}

/*
SOLUTION HINTS:
1. synchronized (lock) { balance += amount; }
2. Use same lock object for both deposit and withdraw
3. Keep synchronized block small - only critical section
4. Processing outside synchronized block improves concurrency

KEY CONCEPT: Synchronized blocks allow locking only critical sections
*/
