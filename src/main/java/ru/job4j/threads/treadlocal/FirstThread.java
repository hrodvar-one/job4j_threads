package ru.job4j.threads.treadlocal;

public class FirstThread extends Thread {
    @Override
    public void run() {
        ThreadLocalDemo.getThreadLocal().set("Это поток 1.");
        System.out.println(ThreadLocalDemo.getThreadLocal().get());
    }
}
