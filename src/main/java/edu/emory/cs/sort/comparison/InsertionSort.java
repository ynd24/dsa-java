package edu.emory.cs.sort.comparison;

import edu.emory.cs.sort.AbstractSort;

import java.util.Comparator;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class InsertionSort<T extends Comparable<T>> extends AbstractSort<T> {
    public InsertionSort() {
        this(Comparator.naturalOrder());
    }

    public InsertionSort(Comparator<T> comparator) {
        super(comparator);
    }

    @Override
    public void sort(T[] array, int beginIndex, int endIndex) {
        sort(array, beginIndex, endIndex, 1); // auxiliary method to set h = 1
    }

    protected void sort(T[] array, int beginIndex, int endIndex, final int h) {
        int begin_h = beginIndex + h; // so that each one is compared with h difference

        for (int i = begin_h; i < endIndex; i++) // iterate once over array
            for (int j = i; begin_h <= j && compareTo(array, j, j - h) < 0; j -= h) // j also points at i
                // if j is less than j-h, swap both, now that j has decremented and update compare once again
                // and keep swapping till one of the two conditions fail
                swap(array, j, j - h);
    }
}