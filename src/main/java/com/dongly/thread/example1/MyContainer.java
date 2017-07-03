package com.dongly.thread.example1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by tiger on 2017/7/3.
 */
public class MyContainer {

    List<Object> objects = new ArrayList<>();

    void add(Object o) {
        objects.add(o);
    }

    int size() {

        return objects.size();
    }

    public static void main(String[] args) {
        MyContainer container = new MyContainer();

        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i <= 10; i++) {
                    TimeUnit.MILLISECONDS.sleep(200L);
                    int size = container.size();
                    if (size % 5 == 0) {
                        condition1.await();
                        condition2.signal();
                    }
                    container.add(new Object());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            lock.lock();
            try {
                while (true) {
                    int size = container.size();
                    System.out.println("size: " + size);
                    if (size % 5 == 0) {
                        System.out.println("当前容器大小： " + size);
                        condition1.signal();
                        if (size == 10) break;
                    }
                    System.out.println("-------------------");
                    condition2.await();
                    // TimeUnit.MILLISECONDS.sleep(1L);
                    System.out.println("==================");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();


        // new Thread(() -> {
        //     lock.lock();
        //     try {
        //         while (true) {
        //             TimeUnit.MILLISECONDS.sleep(200L);
        //             int size = container.size();
        //             if (size % 5 == 0) {
        //                 condition1.await();
        //                 condition2.signal();
        //             }
        //             container.add(new Object());
        //         }
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     } finally {
        //         lock.unlock();
        //     }
        // }).start();
        //
        // new Thread(() -> {
        //     lock.lock();
        //     try {
        //         while (true) {
        //             int size = container.size();
        //             System.out.println("size: " + size);
        //             if (size % 5 == 0) {
        //                 System.out.println("当前容器大小： " + size);
        //                 condition1.signal();
        //             }
        //             System.out.println("-------------------");
        //             condition2.await();
        //             // TimeUnit.MILLISECONDS.sleep(1L);
        //             System.out.println("==================");
        //         }
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     } finally {
        //         lock.unlock();
        //     }
        // }).start();

    }

}
