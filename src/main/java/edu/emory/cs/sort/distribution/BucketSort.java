package edu.emory.cs.sort.distribution;

import edu.emory.cs.sort.AbstractSort;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public abstract class BucketSort<T extends Comparable<T>> extends AbstractSort<T> {
    protected List<Deque<T>> buckets; // deque as we want to remove and add from ontop
    // each bucket is a deque, and we have a list of deque

    /** @param numBuckets the total number of buckets. */
    public BucketSort(int numBuckets) {
        super(null); // no comparisons therefore null
        buckets = Stream.generate(ArrayDeque<T>::new).limit(numBuckets).collect(Collectors.toList());
        // creates a specified number of buckets
    }

    /**
     * @param array       the input array.
     * @param beginIndex  the index of the first key to be sorted (inclusive).
     * @param endIndex    the index of the last key to be sorted (exclusive).
     * @param bucketIndex takes a key and returns the index of the bucket that the key to be added.
     */
    protected void sort(T[] array, int beginIndex, int endIndex, Function<T, Integer> bucketIndex) {
        // add each element in the input array to the corresponding bucket
        for (int i = beginIndex; i < endIndex; i++)
            buckets.get(bucketIndex.apply(array[i])).add(array[i]); // gives you the index of the bucket
        //that it must be added to, and then adds to that bucket

        // merge elements in all buckets to the input array
        for (Deque<T> bucket : buckets) { // iterating from 1st to last bucket
            while (!bucket.isEmpty())
                array[beginIndex++] = bucket.remove(); // take the last item in the bucket
            // and put in original array
        }
    }
}
