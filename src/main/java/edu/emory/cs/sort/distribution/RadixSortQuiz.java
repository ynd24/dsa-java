package edu.emory.cs.sort.distribution;

import java.util.Deque;
import java.util.function.Function;
import java.util.List;


/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class RadixSortQuiz extends RadixSort {
    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        int maxBit = getMaxBit(array, beginIndex, endIndex);
        sortAux(array, beginIndex, endIndex, maxBit);

    } // List<Deque<Integer>> bucketIndex

    public void sortAux(Integer[] array, int beginIndex, int endIndex, int bit ) {

        int div = (int)Math.pow(10, bit);
        Function<Integer, Integer> bucketIndex = key -> ((key / div) % 10);
        for (int i = beginIndex; i < endIndex; i++)
            buckets.get(bucketIndex.apply(array[i])).add(array[i]); // gives you the index of the bucket
            // buckets.get((array[i] / div) % 10).add(array[i]); // gives you the index of the bucket

        // count the size of each bucket
        int[] counts = new int[10];
        for (Deque<Integer> bucket : buckets) { // iterating from 1st to last bucket
            while (!bucket.isEmpty())
                array[beginIndex++] = bucket.remove(); // take the last item in the bucket
        }

        for (int count : counts)
            if(buckets.size() <= 1) {
                break;
            } else {
                sortAux(array, beginIndex, beginIndex+count, bit-1);
            }

    }
}
