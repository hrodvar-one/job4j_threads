package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class SimpleBlockingQueueTest {

    @Test
    public void testProducerConsumer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        List<Integer> consumed = new ArrayList<>();
        int totalElements = 10;

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < totalElements; i++) {
                    queue.offer(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < totalElements; i++) {
                    consumed.add(queue.poll());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        assertEquals(totalElements, consumed.size(), "Все элементы должны были быть израсходованы.");
        for (int i = 0; i < totalElements; i++) {
            assertEquals(Integer.valueOf(i), consumed.get(i), "Расходуемые элементы должны быть в порядке производства.");
        }
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        int totalElements = 5;
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < totalElements; i++) {
                    queue.offer(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        Thread consumer = new Thread(() -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4);
    }
}