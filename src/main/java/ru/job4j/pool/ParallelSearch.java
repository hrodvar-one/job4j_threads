package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 10;
    private final T[] array;
    private final T target;
    private final int start;
    private final int end;

    public ParallelSearch(T[] array, T target, int start, int end) {
        if (array == null) {
            throw new IllegalArgumentException("Array must not be null");
        }
        this.array = array;
        this.target = target;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            return linearSearch();
        }

        int mid = (start + end) / 2;
        ParallelSearch<T> leftTask = new ParallelSearch<>(array, target, start, mid);
        ParallelSearch<T> rightTask = new ParallelSearch<>(array, target, mid, end);
        leftTask.fork();
        int rightResult = rightTask.compute();
        int leftResult = leftTask.join();

        return (leftResult != -1) ? leftResult : rightResult;
    }

    private int linearSearch() {
        for (int i = start; i < end; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    public static <T> int parallelSearch(T[] array, T target) {
        if (array == null) {
            throw new IllegalArgumentException("Массив не должен быть null");
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch<>(array, target, 0, array.length));
    }

    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, null, 5, 6, 7, 8, 9, 10};
        Integer target = null;
        int index = parallelSearch(array, target);
        System.out.println("Index of " + target + ": " + index);

        Integer[] anotherArray = null;
        try {
            parallelSearch(anotherArray, 1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
