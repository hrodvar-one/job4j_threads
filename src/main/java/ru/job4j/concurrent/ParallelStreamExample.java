package ru.job4j.concurrent;

import java.util.Arrays;
import java.util.List;

public class ParallelStreamExample {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 3, 4, 5, 2);
        list.stream().parallel().forEachOrdered(System.out::println);
    }
}
