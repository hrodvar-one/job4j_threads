package ru.job4j.concurrent;

public class ConsoleProgress {
    public static void main(String[] args) throws InterruptedException {
        var process = new char[] {'-', '\\', '|', '/'};
        Thread progress = new Thread(
                () -> {
                    try {
                        while (!Thread.currentThread().isInterrupted()) {
                            for (char c : process) {
                                Thread.sleep(500);
                                System.out.print("\r load: " + c);
                            }
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}
