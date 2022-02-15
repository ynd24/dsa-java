package edu.emory.cs.queue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LazyPriorityQueue<T extends Comparable<T>> extends AbstractPriorityQueue<T>{
    private final List<T> keys;

    /** Initializes this as a maximum PQ. */
    public LazyPriorityQueue() {
        this(Comparator.naturalOrder());
    }

    /** @see AbstractPriorityQueue#AbstractPriorityQueue(Comparator). */
    public LazyPriorityQueue(Comparator<T> priority) { // constructor
        super(priority); // call super class constructor
        keys = new ArrayList<>();
    }

    @Override
    public int size() {
        return keys.size();
    }

    /**
     * Appends a key to {@link #keys}.
     * @param key the key to be added.
     */
    @Override
    public void add(T key) { // simply just add key, O(1)
        keys.add(key);
    }

    /**
     * Finds the key with the highest/lowest priority, and removes it from {@link #keys}.
     * @return the key with the highest/lowest priority if exists; otherwise, null.
     */
    @Override
    public T remove() { // O(n)
        if (isEmpty()) return null;
        T max = Collections.max(keys, priority); // have to traverse list to find max, O(n)
        // max has the value of the key to be removed
        keys.remove(max); // after removing you have shift arraylist hence O(n)
        return max;
    }
}

