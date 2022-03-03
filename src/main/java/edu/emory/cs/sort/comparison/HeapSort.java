package edu.emory.cs.sort.comparison;
import edu.emory.cs.sort.AbstractSort;
import java.util.Comparator;
/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class HeapSort<T extends Comparable<T>> extends AbstractSort<T> {
    public HeapSort() {
        this(Comparator.naturalOrder());
    }

    public HeapSort(Comparator<T> comparator) {
        super(comparator);
    }

    @Override
    public void sort(T[] array, int beginIndex, int endIndex) {
        // heapify all elements
        for (int k = getParentIndex(beginIndex, endIndex - 1); k >= beginIndex; k--)
            sink(array, k, beginIndex, endIndex);

        // swap the endIndex element with the root element and sink it
        while (endIndex > beginIndex + 1) {
            swap(array, beginIndex, --endIndex);
            sink(array, beginIndex, beginIndex, endIndex);
        }
    }

    private void sink(T[] array, int k, int beginIndex, int endIndex) {
        for (int i = getLeftChildIndex(beginIndex, k); i < endIndex; k = i, i = getLeftChildIndex(beginIndex, k)) {
            if (i + 1 < endIndex && compareTo(array, i, i + 1) < 0) i++;
            if (compareTo(array, k, i) >= 0) break;
            swap(array, k, i);
        }
    }

    private int getParentIndex(int beginIndex, int k) {
        return beginIndex + (k - beginIndex - 1) / 2;
    }

    private int getLeftChildIndex(int beginIndex, int k) {
        return beginIndex + 2 * (k - beginIndex) + 1;
    }
}
