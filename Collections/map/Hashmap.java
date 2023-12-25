package Collections.map;
/*
1. In interface we can't declare static methods, because static methods are at class level.
2. interface means providing blueprint for the class instances. so it doesn't adhere to java principles.
 */

/*
hashmap (JAVA) = unordered_map ( C++)

(array + linked List on each index)
0  -> 1-> 2-> 3
1 -> 1-> 2-> 3
2 -> 1-> 2-> 3
3,-> 1-> 2-> 3
4 -> 1-> 2-> 3

*/


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class HashmapRunner {
    public static void main(String args[]) {
//        Hashmap map = new Hashmap(40);
//        map.put(1, 1);
//        map.put(2, 2);
//        map.put(11, 11);
//        map.put(12, 12);
//        map.printMapList();
//        System.out.println(map.get(1));


        /* Collections framework hashmap */

        System.out.println("----- Collections Map ------");
        Map<Integer, Integer> collectionsMap = new HashMap<Integer, Integer>();
        collectionsMap.put(12, 12);
        collectionsMap.put(1901, 1901);
        collectionsMap.put(1, 1);
        collectionsMap.put(2, 2);
        collectionsMap.put(11, 11);
        /* Print in no insertion order */

//        for (Map.Entry<Integer, Integer> entry : collectionsMap.entrySet()) {
//            if(entry.getKey().equals(12)){
//                collectionsMap.put(1,15);
//            }
//            System.out.println(entry.getValue());
//        }
        for (Integer key : collectionsMap.keySet()) {
            if (key.equals(2)) collectionsMap.put(121, 121);
        }

    }
}

public class Hashmap {

    private LinkedList[] hashmapEntry;
    private int size;

    public Hashmap(int size) {
        this.size = size;
        hashmapEntry = new LinkedListImpl[size];
        for (int i = 0; i < 10; i++) {
            hashmapEntry[i] = new LinkedListImpl();
        }
    }

    public int get(int key) {
        int hash = key % 10;
        return hashmapEntry[hash].findKey(key);
    }

    public void put(int key, int value) {
        int hash = key % 10;
        hashmapEntry[hash].insert(key, value);
    }

    public int generateHash(int key) {
        return Integer.hashCode(key);
    }

    public void resize() {
        // when keys will get more collionss.......
        // copy array into another.
        // make size double.
    }

    public void printMapList() {
        for (int i = 0; i < 10; i++) {
            System.out.println("_-----------------------");
            if (hashmapEntry[i] != null) {
                hashmapEntry[i].printList();
            }

            System.out.println("_-----------------------");
        }
    }

}

interface LinkedList {
    void insert(int key, int value);

    void printList();

    int findKey(int key);
}

class LinkedListImpl implements LinkedList {

    Node head;

    class Node {
        int key;
        int value;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            next = null;
        }
    }

    @Override
    public void insert(int key, int value) {
        Node new_node = new Node(key, value);
        if (head == null) {
            head = new_node;
        } else {
            Node last = head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = new_node;
        }
    }

    @Override
    public void printList() {
        printList(head);
    }

    @Override
    public int findKey(int key) {
        return findKey(key, head);
    }


    protected void printList(Node head) {
        Node currNode = head;
        while (currNode != null) {
            System.out.print(currNode.value + "\n");
            currNode = currNode.next;
        }
    }

    protected int findKey(int key, Node head) {
        Node currNode = head;
        while (currNode != null && currNode.key != key) {
            currNode = currNode.next;
        }
        assert currNode != null;
        return currNode.value;
    }

}