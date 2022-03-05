package edu.emory.cs.sort.hybrid;

import java.io.IOException;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class HybridSortHW<T extends Comparable<T>> implements HybridSort<T> {

    private int array[];
    private int length;

    HybridSortHW(int size)
    {
        array = new int[size];
        this.length = 0;
    }

    private void swapData(int m, int n)
    {
        int temp_data = array[m];
        array[m] = array[n];
        array[n] = temp_data;
    }

    private void addData(int temporal_data)
    {
        array[length] = temporal_data;
        length++;
    }

    private void maxH(int k, int heapData, int start)
    {
        int temp = array[start + k - 1];
        int ch;
        while (k <= heapData / 2) {
            ch = 2 * k;
            if (ch < heapData
                    && array[start + ch - 1] < array[start + ch])
                ch++;
            if (temp >= array[start + ch - 1])
                break;
            array[start + k - 1] = array[start + ch - 1];
            k = ch;
        }
        array[start + k - 1] = temp;
    }

    private void addheap(int start, int endvalue, int heapValue)
    {
        for (int i = (heapValue) / 2; i >= 1; i--){
            maxH(i, heapValue, start);
        }
    }

    private void insertion_sort(int left_value, int right_value)
    {
        for (int m = left_value; m <= right_value; m++) {
            int value = array[m];
            int b = m;
            while (b > left_value && array[b - 1] > value) {
                array[b] = array[b - 1];
                b--;
            }
            array[b] = value;
        }
    }
    private void heap_sort(int start, int end)
    {
        int heapN = end - start;
        this.addheap(start, end, heapN);
        for (int i = heapN; i >= 1; i--) {
            swapData(start, start + i);
            maxH(1, i, start);
        }
    }

    private int divide(int down, int up)
    {
        int fulc = array[up];
        int h = (down - 1);
        for (int m = down; m <= up - 1; m++) {
            if (array[m] <= fulc) {
                h++;
                swapData(h, m);
            }
        }
        swapData(h + 1, up);
        return (h + 1);
    }

    private void display()
    {
        for (int k = 0; k < length; k++){
            System.out.print(array[k] + " ");
        }

    }

    private int pivot(int a, int b, int c)
    {
        int maximum = Math.max(Math.max(array[a], array[b]), array[c]);
        int min = Math.min(Math.min(array[a], array[b]), array[c]);
        int med = maximum ^ min ^ array[a] ^ array[b] ^ array[c];

        if (med == array[a])
            return a;
        if (med == array[b])
            return b;
        return c;
    }

    @Override
    public void introSort()

    {
        int lim = (int)(2 * Math.floor(Math.log(length) /Math.log(2)));
        this.hybridSortBaseLine(0, length - 1, lim);
    }
    @Override
    public void hybridSortBaseLine(int start, int stop, int limit)
    {
        if (stop - start > 16) {
            if (limit == 0) {
                this.heap_sort(start, stop);
                return;
            }
            limit = limit - 1;
            int piv = pivot(start,start + ((stop - start) / 2) + 1,stop);
            swapData(piv, stop);
            int dat = divide(start, stop);
            hybridSortBaseLine(start, dat - 1, limit);
            hybridSortBaseLine(dat + 1, stop, limit);
        }
        else {
            insertion_sort(start, stop);
        }
    }
    public static void main(String args[]) throws IOException
    {
        int[] input = { 1,2,50,53,12,67,28,1,29,35 };
        int l = input.length;
        HybridSortHW intro = new HybridSortHW(l);
        for (int i = 0; i < l; i++) {
            intro.addData(input[i]);
        }
        intro.introSort();
        intro.display();
    }
}

