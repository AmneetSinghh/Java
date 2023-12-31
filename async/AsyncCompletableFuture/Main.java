package AsyncCompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/*
https://www.baeldung.com/java-completablefuture
 */
public class Main {

    public static Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.complete("Hello");
            return null;
        });

        return completableFuture;
    }

    public static void main(String args[]) throws InterruptedException, ExecutionException {
        Future<String> completableFuture = calculateAsync();
        String result = completableFuture.get();
        System.out.println(result);


    }
}


class SupplyAsync {
    public static String omg(){
        /* This will run in different thread */
        for(int i=0;i<=10000;i++){
            System.out.println("hello");
        }
        System.out.println(Thread.currentThread().getName()+ " In omg functin"); // worker-future thread.
        return "omg";
    }
    public static void main(String args[]) throws InterruptedException, ExecutionException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> omg());
        System.out.println(Thread.currentThread().getName()+ " In main function"); // output = main-thread
        System.out.println(future.get());
    }
}


class Functions {
    public static String omg() {
        /* This will run in different thread */
        for(int i=0;i<=10000;i++){
//            System.out.println(Thread.currentThread().getName()+"hello");
        }

        System.out.println(Thread.currentThread().getName()+ " In omg functin"); // worker-future thread.
        try {
            Thread.sleep(200000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "omg";
    }


    public static String omg2() {
        /* This will run in different thread */
        for(int i=0;i<=10000;i++){
//            System.out.println(Thread.currentThread().getName()+"hello");
        }

        System.out.println(Thread.currentThread().getName()+ " In omg functin"); // worker-future thread.
        return "omg";
    }


   static class InfiniteBlockThreadCountExperiment implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+ " In InfiniteBlockThreadCountExperiment"); // worker-future thread.
//
//            try {
//                Thread.sleep(200000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }

            int count=0;
            for(long i=0;i<=1000000000000L;i++){
                count++;
            }
        }
    }
    public static void main(String args[]) throws InterruptedException, ExecutionException {

        /*
        *  Then Apply
        * */
//        CompletableFuture<String> completableFuture
//                = CompletableFuture.supplyAsync(() -> omg());
//
//        // do 1 after another.
//        CompletableFuture<String> future = completableFuture
//                .thenApply(s -> s + omg());
//
//        System.out.println(Thread.currentThread().getName()+ " Main function"); // worker-future thread.
//
//        System.out.println("Last"+ future.get());




        /*
         *  Then Accept
         * */
//        CompletableFuture<String> completableFuture
//                = CompletableFuture.supplyAsync(() -> "Hello");
//
//        CompletableFuture<Void> future = completableFuture
//                .thenAccept(s -> System.out.println("Computation returned: " + s)); // no return
//
//        future.get();




        /*
         *  thenRun
         * */

//        CompletableFuture<String> completableFuture
//                = CompletableFuture.supplyAsync(() -> "Hello");
//
//        CompletableFuture<Void> future = completableFuture
//                .thenRun(() -> System.out.println("Computation finished."));
//
//        future.get();







        /*
         *  Combining - both will run sequentially one after another.
//         * */
//        CompletableFuture<String> completableFuture
//                = CompletableFuture.supplyAsync(() -> "Hello")
//                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
//
//
//        System.out.println(completableFuture.get()+ " lst");







        /*
         * thenCombine : Both omg()'s will run independently.
         * always take 2 arguments.
         */

//        CompletableFuture<String> completableFuture
//                = CompletableFuture.supplyAsync(() -> omg())
//                .thenCombine(CompletableFuture.supplyAsync(
//                        () -> omg()), (s1, s2) -> s1 + s2);








        /*
         * experiment how many threads CompletableFuture executes if I block all.
         * Answer is 9 threads: may be thread-pool default size is 9
            ForkJoinPool.commonPool-worker-4 In omg functin
            ForkJoinPool.commonPool-worker-5 In omg functin
            ForkJoinPool.commonPool-worker-1 In omg functin
            ForkJoinPool.commonPool-worker-3 In omg functin
            ForkJoinPool.commonPool-worker-2 In omg functin
            ForkJoinPool.commonPool-worker-6 In omg functin
            ForkJoinPool.commonPool-worker-7 In omg functin
            ForkJoinPool.commonPool-worker-8 In omg functin
            ForkJoinPool.commonPool-worker-9 In omg functin
         */

//        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> omg());
//        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> omg());
//        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> omg());
//        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> omg());
//        CompletableFuture<String> future5 = CompletableFuture.supplyAsync(() -> omg());
//        CompletableFuture<String> future6 = CompletableFuture.supplyAsync(() -> omg());
//        CompletableFuture<String> future7 = CompletableFuture.supplyAsync(() -> omg());
//        CompletableFuture<String> future8 = CompletableFuture.supplyAsync(() -> omg());
//        CompletableFuture<String> future9 = CompletableFuture.supplyAsync(() -> omg());
//        CompletableFuture<String> future10 = CompletableFuture.supplyAsync(() -> omg());
//        CompletableFuture<String> future11 = CompletableFuture.supplyAsync(() -> omg());
//        CompletableFuture<String> future12= CompletableFuture.supplyAsync(() -> omg());
//        CompletableFuture<String> future13 = CompletableFuture.supplyAsync(() -> omg());
//        CompletableFuture<String> future14 = CompletableFuture.supplyAsync(() -> omg());
//        CompletableFuture<String> future15 = CompletableFuture.supplyAsync(() -> omg());
//        CompletableFuture<String> future16 = CompletableFuture.supplyAsync(() -> omg());
//
//
//        CompletableFuture<String> combinedResult = future1.thenCombine(future2, (s1, s2) -> s1 + s2)
//                .thenCombine(future3, (s12, s3) -> s12 + s3);

//        System.out.println(combinedResult.get()+ " last");



        /*
         * Async thread : ThenApplyAsync in different thread.
         */

        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> omg2());

        CompletableFuture<String> future = completableFuture
                .thenApplyAsync(s -> s + omg2());

        System.out.println(future.get());





    }
}

