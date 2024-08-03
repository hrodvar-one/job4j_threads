package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutionException;

class RolColSumTest {

    @Test
    public void whenSyncSumThenCorrectResult() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        RolColSum.Sums[] sums = RolColSum.sum(matrix);

        assertEquals(6, sums[0].getRowSum());
        assertEquals(15, sums[1].getRowSum());
        assertEquals(24, sums[2].getRowSum());

        assertEquals(12, sums[0].getColSum());
        assertEquals(15, sums[1].getColSum());
        assertEquals(18, sums[2].getColSum());
    }

    @Test
    public void whenAsyncSumThenCorrectResult() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        RolColSum.Sums[] sums = RolColSum.asyncSum(matrix);

        assertEquals(6, sums[0].getRowSum());
        assertEquals(15, sums[1].getRowSum());
        assertEquals(24, sums[2].getRowSum());

        assertEquals(12, sums[0].getColSum());
        assertEquals(15, sums[1].getColSum());
        assertEquals(18, sums[2].getColSum());
    }

    @Test
    public void whenEmptyMatrixThenZeroSums() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };

        RolColSum.Sums[] sums = RolColSum.asyncSum(matrix);

        for (RolColSum.Sums sum : sums) {
            assertEquals(0, sum.getRowSum());
            assertEquals(0, sum.getColSum());
        }
    }

    @Test
    public void whenSingleElementMatrixThenCorrectResult() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {5}
        };

        RolColSum.Sums[] sums = RolColSum.asyncSum(matrix);

        assertEquals(5, sums[0].getRowSum());
        assertEquals(5, sums[0].getColSum());
    }
}