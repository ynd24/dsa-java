package edu.emory.cs.sort.hybrid;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public interface HybridSort<T extends Comparable<T>> {

    public abstract void introSort();
    public abstract void hybridSortBaseLine(int start, int stop, int limit);
}

