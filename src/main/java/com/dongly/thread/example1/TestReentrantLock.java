package com.dongly.thread.example1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by tiger on 2017/7/4.
 */
public class TestReentrantLock {


    public static void main(String[] args) {

        Lock lock = new ReentrantLock();


        new Thread(() -> {
            try {
                lock.lock();
                TimeUnit.SECONDS.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();


        Thread t2 = new Thread(() -> {
            try {
                lock.lockInterruptibly();
                System.out.println("+++++++++++++++++++");
            } catch (InterruptedException e) {
                System.out.println("----------------------");
            } finally {
                System.out.println(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().isInterrupted());
                if (!Thread.currentThread().isInterrupted()) {
                    System.out.println("执行中Ing");
                }
                lock.unlock();
                System.out.println("线程中断了");
            }
        }, "BBBB");
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // t2.interrupt();

        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(t2.isInterrupted() + " ++++++++++++= ");

    }

}
