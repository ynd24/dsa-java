package edu.emory.cs.sort.distribution;

import java.util.Arrays;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public abstract class RadixSort extends BucketSort<Integer> {
    public RadixSort() {
        super(10); // 0 - 9
        // call super constructor
    }

    /**
     * @param array      the input array to be sorted.
     * @param beginIndex the beginning index (inclusive).
     * @param endIndex   the ending index (exclusive).
     * @return the order of the most significant digit in the input array.
     * It returns `0` if the max number in the array is <= 0.
     */
    protected int getMaxBit(Integer[] array, int beginIndex, int endIndex) {
        Integer max = Arrays.stream(array, beginIndex, endIndex).reduce(Integer::max).orElse(null);
        return (max != null && max > 0) ? (int)Math.log10(max) + 1 : 0;
    }

}
