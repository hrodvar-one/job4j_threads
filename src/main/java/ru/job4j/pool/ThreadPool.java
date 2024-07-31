package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    public ThreadPool() throws InterruptedException {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Runnable task = tasks.poll();
                        if (task != null) {
                            task.run();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(String[] args) {
        try {
            ThreadPool threadPool = new ThreadPool();

            Runnable task1 = () -> {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Task 1 - Step " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };

            Runnable task2 = () -> {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Task 2 - Step " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };

            Runnable task3 = () -> {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Task 3 - Step " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };

            threadPool.work(task1);
            threadPool.work(task2);
            threadPool.work(task3);

            Thread.sleep(10000);

            threadPool.shutdown();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
