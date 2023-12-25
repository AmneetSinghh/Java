package Collections.map.Concurrency;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/*
-- compute if absent :


If the specified key is not already associated with a value,
attempts to compute its value using the given mapping function and
enters it into this map unless null. The entire method invocation is performed atomically,
so the function is applied at most once per key. Some attempted update operations on this map by
other threads may be blocked while computation is in progress, so the computation should be short and simple,
and must not attempt to update any other mappings of this map.



*/
public class ComputeIfAbsentDemo {

    class Product {
        String id;
        String namn;
    }

    class ComputeRunnable implements Runnable {

        @Override
        public void run() {
         Product product  =  getProductDetails(1);
        }
    }

    private ConcurrentHashMap<Long, Product> productCache = new ConcurrentHashMap<>();

    // Method to get product details from cache or fetch from external service
    public Product getProductDetails(long productId) {

        System.out.println(Thread.currentThread().getName()+" getProductDetails");
        return productCache.computeIfAbsent(productId, key -> {
            try {
                Product product = fetchProductDetails(productId); // Fetch product details from the external service
                return product;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }


    private static void sleep(int timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Simulated method to fetch product details from an external service
    private Product fetchProductDetails(long productId) throws InterruptedException {
        // Simulate fetching product details from an external service
        Product product = new Product();// Logic to fetch product details
        System.out.println(Thread.currentThread().getName()+" Entering");
        sleep(5);
        System.out.println(Thread.currentThread().getName()+" exiting");
        return product;
    }

    public void run() throws InterruptedException {
        Thread[] thread = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread[i] = new Thread(new ComputeRunnable());
        }

        for (int i = 0; i < 10; i++) {
            thread[i].start();
        }

        for (int i = 0; i < 10; i++) {
            thread[i].join();
        }
        System.out.println("All completed");
    }

    public static void main(String args[]) throws InterruptedException {
        ComputeIfAbsentDemo computeIfAbsentDemo = new ComputeIfAbsentDemo();
        computeIfAbsentDemo.run();
    }
}

