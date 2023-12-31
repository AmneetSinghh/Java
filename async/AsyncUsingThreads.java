
/*
Asynchronous programming refers to a broader concept where tasks can be executed independently,
allowing the calling thread to continue its work without waiting for the completion of the asynchronous task.

Multithreading is one technique used to achieve asynchronous behavior.
*/
//https://medium.com/cognizantsoftvision-guildhall/async-in-java-80a7240fefa8


public class AsyncUsingThreads {

    static class Test implements Runnable{
        @Override
        public void run() {
            for(int i=0;i<100;i++){
                System.out.println(Thread.currentThread().getName()+" executing : "+ i);
            }
        }
    }

    public static void main(String args[]){
        Test test = new Test();
        Thread thead1 = new Thread(new Test());
        thead1.start();
        System.out.println(Thread.currentThread().getName()+" Main value : ");//
        test.run();

        // main thread and thread1 are independent. So main thread is free for taking tasks.
    }

}


