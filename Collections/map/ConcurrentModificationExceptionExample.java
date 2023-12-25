package Collections.map;

import java.util.HashMap;
import java.util.Map;

public class ConcurrentModificationExceptionExample {
}

class Hashmap_Concurrently_Failure_Example_1 {
    public static void main(String args[]){
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, "Three");

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            map.remove(entry.getKey()); // ConcurrentModificationException will be thrown here
        }
    }
}

class Hashmap_Concurrently_Failure_Example_2 {

    /*
    1. We can't change hashmap while iterating over it, because it changes the structure of hashmap.
     */
    public static void main(String[] args) throws InterruptedException {
        final HashMap<Integer, String> map = new HashMap<>();

        // Thread 1: Adding elements to the HashMap
        Thread thread1 = new Thread(() -> {
            System.out.println("here 1");
            for (int i = 0; i < 1000; i++) {
                map.put(i, "Value" + i);
            }
        });

        // Thread 2: Iterating and modifying elements in the HashMap
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                // Concurrent modification during iteration
                for (Integer key : map.keySet()) {
                    if (key % 2 == 0) {
                        map.remove(key);
                    }
                }
            }
        });

        thread1.start();
        Thread.sleep(2000);
        thread2.start();

        // Wait for both threads to finish
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Printing the HashMap size after concurrent access
        System.out.println("HashMap size: " + map.size());
    }
}



