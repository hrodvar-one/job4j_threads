package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] sums = new Sums[size];

        for (int i = 0; i < size; i++) {
            sums[i] = new Sums();
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < size; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i].setRowSum(rowSum);
            sums[i].setColSum(colSum);
        }

        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int size = matrix.length;
        CompletableFuture<Sums>[] futures = new CompletableFuture[size];

        for (int i = 0; i < size; i++) {
            final int index = i;
            futures[i] = CompletableFuture.supplyAsync(() -> {
                int rowSum = 0;
                int colSum = 0;
                for (int j = 0; j < size; j++) {
                    rowSum += matrix[index][j];
                    colSum += matrix[j][index];
                }
                Sums sums = new Sums();
                sums.setRowSum(rowSum);
                sums.setColSum(colSum);
                return sums;
            });
        }

        Sums[] sums = new Sums[size];
        for (int i = 0; i < size; i++) {
            sums[i] = futures[i].get();
        }

        return sums;
    }
}
