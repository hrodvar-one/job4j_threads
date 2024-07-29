package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        while (true) {
            int expected = count.get();
            boolean success = count.compareAndSet(expected, expected + 1);
            if (success) {
                break;
            }
        }
        throw new UnsupportedOperationException("Count is not impl.");
    }

    public int get() {
        return count.get();
    }
}
