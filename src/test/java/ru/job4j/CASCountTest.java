package ru.job4j;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CASCountTest {

    @Test
    void whenIncrementOnceThenCountIsOne() {
        CASCount casCount = new CASCount();
        casCount.increment();
        assertEquals(1, casCount.get());
    }

    @Test
    void whenIncrementTwiceThenCountIsTwo() {
        CASCount casCount = new CASCount();
        casCount.increment();
        casCount.increment();
        assertEquals(2, casCount.get());
    }

    @Test
    void whenIncrementInMultipleThreadsThenCountIsCorrect() throws InterruptedException {
        CASCount casCount = new CASCount();
        int numberOfThreads = 100;
        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(casCount::increment);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        assertEquals(numberOfThreads, casCount.get());
    }
}