package edu.emory.cs.sort.distribution;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class LSDRadixSort extends RadixSort {
    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        int maxBit = getMaxBit(array, beginIndex, endIndex); // number of digits, units, tens, hunders, etc
        for (int bit = 0; bit < maxBit; bit++) { // iterate through
            int div = (int)Math.pow(10, bit); // helps go from 1st LSD to last
            sort(array, beginIndex, endIndex, key -> (key / div) % 10); // div to get the sig dig required.
            // mod 10 to get lsd
        }
    }


}
