package edu.emory.cs.sort.distribution;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class IntegerBucketSort extends BucketSort<Integer> {
    private final int MIN;

    /**
     * @param min the minimum integer (inclusive).
     * @param max the maximum integer (exclusive).
     */
    public IntegerBucketSort(int min, int max) {
        super(max - min); // number of buckets using the range
        MIN = min;
    }

    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        sort(array, beginIndex, endIndex, key -> key - MIN); // sort in bucketsort class
        // key - Min helps find index of bucket
    }
}
