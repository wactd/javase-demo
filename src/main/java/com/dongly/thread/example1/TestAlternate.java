package com.dongly.thread.example1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by tiger on 2017/6/28.
 */
public class TestAlternate {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(3);

        Printer printer = new Printer();

        Runnable a = new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        printer.printA(i);
                    }
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable b = new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        printer.printB(i);
                    }
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable c = new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        printer.printC(i);
                    }
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };


        Thread ta = new Thread(a, " A ");
        ta.start();

       Thread tb = new Thread(b, " B ");
       tb.start();

        Thread tc = new Thread(c, " C ");
        tc.start();

        // ta.join();
        // tb.join();
        // tc.join();
        latch.await();
        System.out.println("---------- end -----------");
    }

    static class Printer {

        Lock lock = new ReentrantLock();

        Integer flag = 1;

        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();


        public void printA(Integer index) throws InterruptedException {
            lock.lock();
            try {

                while (flag != 1) conditionA.await();

                System.out.println(Thread.currentThread().getName() + " index=" + index + " : A");
                flag = 2;
                conditionB.signal();
            } finally {
                lock.unlock();
            }
        }

        public void printB(Integer index) throws InterruptedException {
            lock.lock();
            try {

                while (flag != 2) conditionB.await();

                System.out.println(Thread.currentThread().getName() + " index=" + index + " : B");
                flag = 3;
                conditionC.signal();
            } finally {
                lock.unlock();
            }
        }

        public void printC(Integer index) throws InterruptedException {
            lock.lock();
            try {

                while (flag != 3) conditionC.await();

                System.out.println(Thread.currentThread().getName() + " index=" + index + " : C");
                flag = 1;
                conditionA.signal();
            } finally {
                System.out.println(" ---------------------------- ");
                lock.unlock();
            }
        }

    }

}
