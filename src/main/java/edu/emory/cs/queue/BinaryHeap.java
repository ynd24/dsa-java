package edu.emory.cs.queue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BinaryHeap<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    private final List<T> keys;

    public BinaryHeap() {
        this(Comparator.naturalOrder());
    }

    public BinaryHeap(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
        keys.add(null);
    }

    @Override
    public int size() {
        return keys.size() - 1;
    }

    /**
     * @param i1 the index of the first key in {@link #keys}.
     * @param i2 the index of the second key in {@link #keys}.
     * @return a negative integer, zero, or a positive integer as the first key is
     * less than, equal to, or greater than the second key.
     */
    private int compare(int i1, int i2) {
        return priority.compare(keys.get(i1), keys.get(i2));
    }



    @Override
    public void add(T key) {
        keys.add(key); // add the key
        swim(size()); // order it
    }

    public void swim(int k) {
        for(; 1 < k && compare(k/2, k) < 0; k /= 2) { // k must be greater than 1 and if parent is less than child
            // k = k/2 so that you swim up
            Collections.swap(keys, k/2, k); // swap parent and child
        }
    }

    @Override
    public T remove() {
        if (isEmpty()) return null; // avoid null point error
        Collections.swap(keys, 1, size()); // swap root and last node
        T max = keys.remove(size()); // remove new last node
        sink();
        return max;
    }

    private void sink() {
        for (int k = 1, i  = 2; i <= size(); k = i, i *= 2) { // k points at root, i at left child
            // k updates to left child, i updates to it's left child
            if (i < size() && compare(i, i + 1) < 0) i++;
            // compare left(i) and right(i+1) children to see who has higher priority
            // if right child is greater, increment i to point to right child
            if (compare(k, i) >= 0) break;
            // compare parent(k) and child(i), if parent bigger then break
            Collections.swap(keys, k, i); // else swap parent and child
        }
    }


}
