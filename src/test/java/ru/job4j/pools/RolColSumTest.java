package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.concurrent.ExecutionException;

class RolColSumTest {

    @Test
    public void whenSyncSumThenCorrectResult() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        Sums[] expected = {
                new Sums(), new Sums(), new Sums()
        };
        expected[0].setRowSum(6);
        expected[0].setColSum(12);
        expected[1].setRowSum(15);
        expected[1].setColSum(15);
        expected[2].setRowSum(24);
        expected[2].setColSum(18);

        Sums[] result = RolColSum.sum(matrix);

        assertArrayEquals(expected, result);
    }

    @Test
    public void whenAsyncSumThenCorrectResult() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        Sums[] expected = {
                new Sums(), new Sums(), new Sums()
        };
        expected[0].setRowSum(6);
        expected[0].setColSum(12);
        expected[1].setRowSum(15);
        expected[1].setColSum(15);
        expected[2].setRowSum(24);
        expected[2].setColSum(18);

        Sums[] result = RolColSum.asyncSum(matrix);

        assertArrayEquals(expected, result);
    }

    @Test
    public void whenEmptyMatrixThenZeroSums() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };

        Sums[] expected = {
                new Sums(), new Sums(), new Sums()
        };

        Sums[] result = RolColSum.asyncSum(matrix);

        assertArrayEquals(expected, result);
    }

    @Test
    public void whenSingleElementMatrixThenCorrectResult() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {5}
        };

        Sums[] expected = {
                new Sums()
        };
        expected[0].setRowSum(5);
        expected[0].setColSum(5);

        Sums[] result = RolColSum.asyncSum(matrix);

        assertArrayEquals(expected, result);
    }
}