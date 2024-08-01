package ru.job4j.pool;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class ParallelSearchTest {

    @Test
    public void testIntegerArraySmall() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target = 7;
        int index = ParallelSearch.parallelSearch(array, target);
        assertEquals(6, index);
    }

    @Test
    public void testIntegerArrayLarge() {
        Integer[] array = new Integer[1000];
        Arrays.setAll(array, i -> i + 1);
        int target = 500;
        int index = ParallelSearch.parallelSearch(array, target);
        assertEquals(499, index);
    }

    @Test
    public void testStringArraySmall() {
        String[] array = {"apple", "banana", "cherry", "date", "elderberry"};
        String target = "cherry";
        int index = ParallelSearch.parallelSearch(array, target);
        assertEquals(2, index);
    }

    @Test
    public void testStringArrayLarge() {
        String[] array = new String[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = "string" + i;
        }
        String target = "string500";
        int index = ParallelSearch.parallelSearch(array, target);
        assertEquals(500, index);
    }

    @Test
    public void testElementNotFound() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target = 11;
        int index = ParallelSearch.parallelSearch(array, target);
        assertEquals(-1, index);
    }

    @Test
    public void testEmptyArray() {
        Integer[] array = {};
        int target = 1;
        int index = ParallelSearch.parallelSearch(array, target);
        assertEquals(-1, index);
    }

    @Test
    public void testNullArray() {
        Integer[] array = null;
        int target = 1;
        assertThrows(NullPointerException.class, () -> ParallelSearch.parallelSearch(array, target));
    }
}