package com.dongly.thread.example1;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by tiger on 2017/6/29.
 */
public class TestReadWriteLock {

    public static void main(String[] args) {
        ReadWriteLockDemo demo = new ReadWriteLockDemo();

        new Thread(() -> {
            demo.setNumber(ThreadLocalRandom.current().nextInt(60,70));
        }, "写线程： ").start();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                demo.getNumber();
            }, "线程" + i + ": ").start();
        }

    }

    static class ReadWriteLockDemo {

        private Integer number = 0;

        private ReadWriteLock lock = new ReentrantReadWriteLock();

        public void setNumber(Integer number) {
            lock.writeLock().lock();

            try {
                System.out.println(Thread.currentThread().getName() + " 写入 " + number);
                this.number = number;
            } finally {
                lock.writeLock().unlock();
            }
        }

        public void getNumber() {

            lock.readLock().lock();

            try {
                System.out.println(Thread.currentThread().getName() + " 读取 " + number);
            } finally {
                lock.readLock().unlock();
            }

        }
    }

}
