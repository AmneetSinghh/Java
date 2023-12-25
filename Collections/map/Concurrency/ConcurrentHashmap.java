package Collections.map.Concurrency;


import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Arrays.asList;

/*
1. Reads doesn't block
2. Writes are at map[key] level.
*/

public class ConcurrentHashmap {

    private Map<Integer, Integer> frequencyMap;

    public void setup() {
        frequencyMap = new ConcurrentHashMap<>();
        frequencyMap.put(0, 0);
        frequencyMap.put(1, 0);
        frequencyMap.put(2, 0);
    }

    private static void sleep(int timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    // compute run serially if key  same for both ( Locking of ConcurrentHashmap on same key)
    // compute run parallel if key  different for both. ( No Locking of ConcurrentHashmap on different key)
    public void givenOneThreadIsWriting_whenAnotherThreadWritesAtDifferentKey_thenNotWaitAndGetCorrectValue() throws Exception {
        ExecutorService threadExecutor = Executors.newFixedThreadPool(2);

        Callable<Integer> writeAfter5Sec = () -> frequencyMap.computeIfPresent(1, (k, v) -> {
            System.out.println("Entering first");
            sleep(5);
            return frequencyMap.get(k) + 1;
        });

        AtomicLong time = new AtomicLong(System.currentTimeMillis());
        Callable<Integer> writeAfter1Sec = () -> frequencyMap.computeIfPresent(2, (k, v) -> {
            System.out.println("Entering second");
            sleep(1);
            time.set((System.currentTimeMillis() - time.get()) / 1000);
            return frequencyMap.get(k) + 1;
        });

        threadExecutor.invokeAll(asList(writeAfter5Sec, writeAfter1Sec));

        if (!threadExecutor.awaitTermination(2, TimeUnit.SECONDS)) {
            threadExecutor.shutdown();
        }
        System.out.println(frequencyMap.get(1));
        System.out.println(frequencyMap.get(2));
    }

    public static void main(String args[]) throws Exception {
        ConcurrentHashmap map = new ConcurrentHashmap();
        map.setup();
//        map.test();
//        map.givenOneThreadIsWriting_whenAnotherThreadReads_thenGetCorrectValue();
//        map.givenOneThreadIsWriting_whenAnotherThreadReads_thenGetCorrectValue();
        map.givenOneThreadIsWriting_whenAnotherThreadWritesAtDifferentKey_thenNotWaitAndGetCorrectValue();
    }


}





