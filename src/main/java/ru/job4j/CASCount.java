package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int expected;
        boolean success;
        do {
            expected = count.get();
            success = count.compareAndSet(expected, expected + 1);
        } while (!success);
    }

    public int get() {
        return count.get();
    }
}
