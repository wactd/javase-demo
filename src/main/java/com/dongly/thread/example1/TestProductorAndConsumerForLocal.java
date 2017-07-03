package com.dongly.thread.example1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by tiger on 2017/6/28.
 */
public class TestProductorAndConsumerForLocal {

    public static void main(String[] args) {

        Shop shop = new Shop();

        new Thread(new Productor(shop), " 进货员1 ").start();
        new Thread(new Productor(shop), " 进货员2 ").start();
        new Thread(new Consumer(shop), " 消费员1 ").start();
        new Thread(new Consumer(shop), " 消费员2 ").start();

    }


    /**
     * 商店
     */
    static class Shop {

        Integer product = 0;

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        /**
         * 进货
         */
        public void stock() throws InterruptedException {

            lock.lock();

            try {
                // 避免虚假唤醒, 必须使用while循环判断，线程大于2
                while (product > 0) {

                    System.out.println(" 商品已上架, 请售卖 !!! ");

                    condition.await();
                }
                System.out.println(Thread.currentThread().getName() + " 开始生产, 库存 " + ++product);
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        /**
         * 售卖
         */
        public void sale() throws InterruptedException {

            lock.lock();

            try {
                while (product.equals(0)) {
                    System.out.println("商品已经售完, 请进货 !!!");
                    condition.await();
                }

                System.out.println(Thread.currentThread().getName() + " 开始售卖, 剩余 " + --product);
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 生产者
     */
    static class Productor implements Runnable {

        Shop shop;

        Productor(Shop shop) {
            this.shop = shop;
        }

        @Override
        public void run() {

            for (int i = 0; i < 5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    // 开始进货
                    shop.stock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 消费者
     */
    static class Consumer implements Runnable {

        Shop shop;

        Consumer(Shop shop) {
            this.shop = shop;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    // 开始售卖
                    shop.sale();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
