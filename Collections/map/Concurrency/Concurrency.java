package Collections.map.Concurrency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//https://amanagrawal9999.medium.com/hashmap-vs-concurrenthashmap-in-java-8-way-4df5435fe2dc
public class Concurrency {
}

class HashMapComputeIfAbsent
{
    public static void main(String[] args)
    {
        Map<String, Integer> map = new HashMap<>();
        int value = map.computeIfAbsent("abc", (k) -> 10);
        System.out.println("Value:"+value);
        System.out.println("Map:"+map);

        Map<String, Integer> map1 = new HashMap<>();
        map1.put("xyz", null);
        map1.computeIfAbsent("xyz", (k) -> 5);
        System.out.println("Map1:"+map1);

        Map<String, Integer> map2 = new HashMap<>();
        map2.computeIfAbsent("pqr", (k) -> null);
        System.out.println("Map2:"+map2);
    }
}



class HashMapComputeIfPresent
{
    public static void main(String[] args)
    {
        Map<String, List<Integer>> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(100);
        map.put("abc", list);
        map.computeIfPresent("abc", (key, value) ->
        {
            value.add(200);
            return value;
        });
        System.out.println("Map:"+map);

        Map<String, List<Integer>> map1 = new HashMap<>();
        map1.put("xyz", null);
        map1.computeIfPresent("xyz", (key, value) -> new ArrayList<>());
        System.out.println("Map1:"+map1);

        Map<String, List<Integer>> map2 = new HashMap<>();
        List<Integer> list1 = new ArrayList<>();
        list1.add(50);
        map2.put("pqr", list1);
        map2.computeIfPresent("pqr", (key, value) -> null);
        System.out.println("Map2:"+map2);
    }
}


class HashMapCompute
{
    public static void main(String[] args)
    {
        Map<String, String> map = new HashMap<>();
        map.compute("abc", (key, value) -> {
            if(value == null)
                return "empty";
            else
                return value.concat("present");
        });
        System.out.println("Map:"+map);

        Map<String, String> map1 = new HashMap<>();
        map1.put("xyz", "123");
        map1.compute("xyz", (key, value) -> {
            return null;
        });
        System.out.println("Map1:"+map1);
    }
}


class HashMapConcurrencyDemo
{

    static class Worker1 implements Runnable
    {
        private Map<String,Integer> map;
        public Worker1(Map<String,Integer> map)
        {
            this.map = map;
        }

        @Override
        public void run()
        {
            map.computeIfAbsent("pqr", (key) -> {
                try
                {
                    System.out.println("Worker 1");
                    //Thread.sleep(4000);
                }catch (Exception exception)
                {

                }
                return 10;
            });
        }
    }


    static class Worker2 implements Runnable
    {
        private Map<String,Integer> map;
        public Worker2(Map<String,Integer> map)
        {
            this.map = map;
        }

        @Override
        public void run()
        {
            map.computeIfAbsent("pqr", (key) -> {
                try
                {
                    System.out.println("Worker 2");
                    //Thread.sleep(1000);
                }catch (Exception exception)
                {

                }
                return 20;
            });;
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        Map<String,Integer> map = new HashMap<>();
        Map<String,Integer> map1 = new ConcurrentHashMap<>();
//        Thread t1 = new Thread(new Worker1(map));
//        Thread t2 = new Thread(new Worker2(map));

        Thread t1 = new Thread(new Worker1(map1));
        Thread t2 = new Thread(new Worker2(map1));
        t1.start();
        t2.start();
        Thread.sleep(1000);
        System.out.println("Result:"+map1.get("pqr"));
    }

    /*
    Hashmap:
        -  output - concurrentModificationException
        -  both threads will enter computeIfAbsent, so doesn't work

    ConcurrentHashmap:
        -  output - 1 thread will enter computeIfAbsent
        -  some kind of locking is used here, so thread safe.
     */
}

