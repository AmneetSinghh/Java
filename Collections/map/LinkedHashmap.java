package Collections.map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/*
1. Implementation same as Hashmap just maintains AFTER & BEFORE 2 extra pointers.
2. It maintains insertion order of keys.
*/


class LinkedHashmapRunner {
    public static void main(String args[]) {
        LinkedHashmap map = new LinkedHashmap(40);
        map.put(1213, 1213);
        map.put(1818, 1818);
        map.put(1, 1);
        map.put(2, 2);
        map.put(11, 11);
        map.put(12, 12);
        map.printMapList();


        System.out.println(map.get(11));
        /* Collections framework LinkedHashmap */

        System.out.println("----- Collections LinkedHashmap ------");
        Map<Integer,Integer> collectionsMap = new LinkedHashMap<>();
        collectionsMap.put(12,12);
        collectionsMap.put(1901,1901);
        collectionsMap.put(1,1);
        collectionsMap.put(2,2);
        collectionsMap.put(11,11);
        /* Print in no insertion order */

        for(Map.Entry<Integer,Integer> entry : collectionsMap.entrySet()){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }

    }
}

public class LinkedHashmap extends Hashmap {

    private DoubleLinkedList[] hashmapEntry;
    private int size;
    private DoubleLinkedListImpl.Node existingEntry;

    public LinkedHashmap(int size) {
        super(size);
        this.size = size;
        hashmapEntry = new DoubleLinkedListImpl[size];
        for (int i = 0; i < 10; i++) {
            hashmapEntry[i] = new DoubleLinkedListImpl();
            existingEntry = null;
        }
    }

    public int get(int key) {
        int hash = key % 10;
        return hashmapEntry[hash].findKey(key);
    }

    public void put(int key, int value) {
        int hash = key % 10;
        existingEntry = hashmapEntry[hash].insert(key, value, existingEntry);
    }

    @Override
    public void printMapList() {
        while (existingEntry != null) {
            System.out.println(existingEntry.value);
            existingEntry = existingEntry.before;
        }
    }

}

interface DoubleLinkedList extends LinkedList {
    void printListInOrder();

    DoubleLinkedListImpl.Node insert(int key, int value, DoubleLinkedListImpl.Node existingEntry);

    int findKey(int key);
}

class DoubleLinkedListImpl extends LinkedListImpl implements DoubleLinkedList {

    Node head;

    class Node extends LinkedListImpl.Node {
        Node after;
        Node before;

        Node(int key, int value) {
            super(key, value);
            after = null;
            before = null;
        }
    }

    @Override
    public DoubleLinkedListImpl.Node insert(int key, int value, DoubleLinkedListImpl.Node existingEntry) {
        Node new_node = new Node(key, value);
        if (head == null) {
            head = new_node;
            if (existingEntry != null) {
                new_node.before = existingEntry;
                existingEntry.after = new_node;
            }
            existingEntry = new_node;
        } else {
            Node last = head;
            while (last.next != null) {
                last = (Node) last.next;
            }
            last.next = new_node;
            /* For maintaining order */
            new_node.before = existingEntry;
            existingEntry.after = new_node;
            existingEntry = new_node;
        }
        return existingEntry;
    }

    @Override
    public void printListInOrder() {
        Node currNode = head;
        while (currNode != null) {
            System.out.println(currNode.value);
            currNode = currNode.after;
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

}
