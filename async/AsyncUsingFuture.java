import java.util.concurrent.*;

public class AsyncUsingFuture {

    public static Callable getLoop(){
        Callable callable = ()->{
            for(int i=0;i<1000;i++){
                System.out.println(Thread.currentThread().getName()+" executing : "+ i);
            }
            return "For thread: "+ Thread.currentThread().getName()+" loop done";
        };
        return callable;
    }

    public static void main(String args[]) throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future future = service.submit(getLoop());
        int times =0;
        while(!future.isDone()){
            times++;
//            Thread.sleep(1000);
            System.out.println("Not done :( "+ Thread.currentThread().getName() + "Times-> "+ times);
        }
        System.out.println(future.get().toString());// blocking call.

    }
}
