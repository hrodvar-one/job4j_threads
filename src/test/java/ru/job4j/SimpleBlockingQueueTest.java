package ru.job4j;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

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
}