package edu.emory.cs.queue;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class PriorityQueueTest {
    @Test
    public void testRobustness() {
        List<Integer> keys = List.of(4, 1, 3, 2, 5, 6, 8, 3, 4, 7, 5, 9, 7);
        Comparator<Integer> natural = Comparator.naturalOrder();
        Comparator<Integer> reverse = Comparator.reverseOrder();

        testRobustness(new LazyPriorityQueue<>(), keys, reverse);
        testRobustness(new EagerPriorityQueue<>(), keys, reverse);
        testRobustness(new BinaryHeap<>(), keys, reverse);

        testRobustness(new LazyPriorityQueue<>(reverse), keys, natural);
        testRobustness(new EagerPriorityQueue<>(reverse), keys, natural);
        testRobustness(new BinaryHeap<>(reverse), keys, natural);
    }

    /**
     * @param pq   a priority queue.
     * @param keys a list of comparable keys.
     * @param comp a comparator used for sorting.
     */
    <T extends Comparable<T>> void testRobustness(AbstractPriorityQueue<T> pq, List<T> keys, Comparator<T> comp) {
        keys.forEach(pq::add);
        keys = keys.stream().sorted(comp).collect(Collectors.toList());
        keys.forEach(key -> assertEquals(key, pq.remove()));
    }

    @Test
    public void testRuntime() {
        testRuntime(new LazyPriorityQueue<>(), new EagerPriorityQueue<>(), new BinaryHeap<>());
    }

    @SafeVarargs
    final void testRuntime(AbstractPriorityQueue<Integer>... queues) {
        final int begin_size = 1000;
        final int end_size = 10000;
        final int inc = 1000;
        final Random rand = new Random();

        for (int size = begin_size; size <= end_size; size += inc) {
            // JVM warm-up
            benchmark(queues, size, 10, rand::nextInt);
            // benchmark all priority queues with the same keys
            Time[] times = benchmark(queues, size, 1000, rand::nextInt);

            StringJoiner joiner = new StringJoiner("\t");
            joiner.add(Integer.toString(size));
            joiner.add(Arrays.stream(times).map(t -> Long.toString(t.add)).collect(Collectors.joining("\t")));
            joiner.add(Arrays.stream(times).map(t -> Long.toString(t.remove)).collect(Collectors.joining("\t")));
            System.out.println(joiner.toString());
        }
    }

    <T extends Comparable<T>> Time[] benchmark(AbstractPriorityQueue<T>[] queues, int size, int iter, Supplier<T> sup) {
        Time[] times = Stream.generate(Time::new).limit(queues.length).toArray(Time[]::new);

        for (int i = 0; i < iter; i++) {
            List<T> keys = Stream.generate(sup).limit(size).collect(Collectors.toList());
            for (int j = 0; j < queues.length; j++)
                addRuntime(queues[j], times[j], keys);
        }

        return times;
    }

    static class Time {
        long add = 0;
        long remove = 0;
    }

    <T extends Comparable<T>> void addRuntime(AbstractPriorityQueue<T> queue, Time time, List<T> keys) {
        long st, et;

        // runtime for add()
        st = System.currentTimeMillis();
        keys.forEach(queue::add);
        et = System.currentTimeMillis();
        time.add += et - st;

        // runtime for remove()
        st = System.currentTimeMillis();
        while (!queue.isEmpty()) queue.remove();
        et = System.currentTimeMillis();
        time.remove += et - st;
    }
}
