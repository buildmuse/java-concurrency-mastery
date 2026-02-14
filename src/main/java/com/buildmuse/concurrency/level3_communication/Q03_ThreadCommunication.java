package com.buildmuse.concurrency.level3_communication;

/**
 * PROBLEM: Inter-Thread Data Exchange (YOUR INTERVIEW QUESTION!)
 * 
 * Implement communication between two threads to exchange data:
 * - Thread-1 generates data and sends to Thread-2
 * - Thread-2 waits for data, processes it, sends response back
 * - Thread-1 waits for response
 * 
 * Exchange 5 messages back and forth.
 * 
 * EXPECTED OUTPUT:
 * Thread-1: Sending data: Message-1
 * Thread-2: Received: Message-1
 * Thread-2: Sending response: Processed-Message-1
 * Thread-1: Received response: Processed-Message-1
 * Thread-1: Sending data: Message-2
 * Thread-2: Received: Message-2
 * ...
 * 
 * LEARNING:
 * - Bidirectional communication
 * - Data handoff between threads
 * - Synchronization for data exchange
 */
public class Q03_ThreadCommunication {
    
    static class DataExchanger {
        private String data;
        private boolean dataAvailable = false;
        private String response;
        private boolean responseAvailable = false;
        
        // TODO: Implement sendData - Thread-1 sends data to Thread-2
        public synchronized void sendData(String data) throws InterruptedException {
            // Wait if previous data not consumed yet
            
            
            this.data = data;
            this.dataAvailable = true;
            System.out.println(Thread.currentThread().getName() + ": Sending data: " + data);
            
            // Notify Thread-2 that data is available
            
        }
        
        // TODO: Implement receiveData - Thread-2 receives data from Thread-1
        public synchronized String receiveData() throws InterruptedException {
            // Wait until data is available
            
            
            String receivedData = this.data;
            this.dataAvailable = false;
            System.out.println(Thread.currentThread().getName() + ": Received: " + receivedData);
            
            // Notify that data was consumed
            
            
            return receivedData;
        }
        
        // TODO: Implement sendResponse - Thread-2 sends response to Thread-1
        public synchronized void sendResponse(String response) throws InterruptedException {
            // Wait if previous response not consumed yet
            
            
            this.response = response;
            this.responseAvailable = true;
            System.out.println(Thread.currentThread().getName() + ": Sending response: " + response);
            
            // Notify Thread-1 that response is available
            
        }
        
        // TODO: Implement receiveResponse - Thread-1 receives response from Thread-2
        public synchronized String receiveResponse() throws InterruptedException {
            // Wait until response is available
            
            
            String receivedResponse = this.response;
            this.responseAvailable = false;
            System.out.println(Thread.currentThread().getName() + ": Received response: " + receivedResponse);
            
            // Notify that response was consumed
            
            
            return receivedResponse;
        }
    }
    
    public static void main(String[] args) {
        DataExchanger exchanger = new DataExchanger();
        
        // Thread-1: Sends data, waits for response
        Thread thread1 = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    exchanger.sendData("Message-" + i);
                    exchanger.receiveResponse();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Thread-1");
        
        // Thread-2: Receives data, processes, sends response
        Thread thread2 = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    String data = exchanger.receiveData();
                    String processed = "Processed-" + data;
                    exchanger.sendResponse(processed);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Thread-2");
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\n✓ Successfully exchanged 5 messages!");
    }
}

/*
SOLUTION HINTS:
1. sendData():
   while (dataAvailable) { wait(); }
   this.data = data;
   dataAvailable = true;
   notifyAll();

2. receiveData():
   while (!dataAvailable) { wait(); }
   String data = this.data;
   dataAvailable = false;
   notifyAll();
   return data;

3. Similar pattern for sendResponse/receiveResponse

KEY CONCEPT: This is your interview question!
Master this pattern - it's fundamental for thread coordination.
*/
