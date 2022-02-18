package edu.emory.cs.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** @author Jinho D. Choi */
public class TernaryHeapQuiz<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    private final List<T> keys;

    public TernaryHeapQuiz() {
        this(Comparator.naturalOrder());
    }


    public TernaryHeapQuiz(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
        keys.add(null);
    }

    @Override
    public int size() {
        return keys.size() - 1;
    }

    @Override
    public void add(T key) {
        keys.add(key);
        swim(size());
    }

    public void swim(int k) {
      for (; 1 < k && compare((k+1)/3, k) < 0; k = (k+1)/3) {
          Collections.swap(keys, (k+1)/3, k);
      }
    }

    @Override
    public T remove() {
        if (isEmpty()) return null;
        Collections.swap(keys, 1, size());
        T max = keys.remove(size());
        sink();
        return max;
    }

    private void sink() {
       // int j = 2;
        for (int k = 1, i = 2; i <= size(); k = i, i = (3*i)-1) {
            int j = i;
            if (j+1 <= size() && compare(i, i+1) < 0) {
                i++;
            }
            if (j+2 <= size() && compare(i, j + 2) < 0) {
                i = j + 2;
            }
            if (compare(k, i) >= 0) break;
            Collections.swap(keys, k, i);
        }
    }

    private int compare(int i1, int i2) {
        return priority.compare(keys.get(i1), keys.get(i2));
    }
}
