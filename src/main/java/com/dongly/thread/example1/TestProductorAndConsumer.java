package com.dongly.thread.example1;

import java.util.concurrent.TimeUnit;

/**
 * Created by tiger on 2017/6/28.
 */
public class TestProductorAndConsumer {

    public static void main(String[] args) {

        Shop shop = new Shop();

        new Thread(new Productor(shop), " 进货员A ").start();
        new Thread(new Productor(shop), " 进货员B ").start();
        new Thread(new Consumer(shop), " 消费员A ").start();
        new Thread(new Consumer(shop), " 消费员B ").start();

    }


    /**
     * 商店
     */
    static class Shop {

        Integer product = 0;

        /**
         * 进货
         */
        public synchronized void stock() throws InterruptedException {

            // 避免虚假唤醒, 必须使用while循环判断，线程大于2
            while (product > 0) {

                System.out.println(" 商品已上架, 请售卖 !!! ");

                this.wait();
            }
            System.out.println(Thread.currentThread().getName() + " 开始生产 " + ++product);
            this.notifyAll();
        }

        /**
         * 售卖
         */
        public synchronized void sale() throws InterruptedException {

            while (product.equals(0)) {
                System.out.println("商品已经售完, 请进货 !!!");
                this.wait();
            }

            System.out.println(Thread.currentThread().getName() + " 开始售卖 " + --product);
            this.notifyAll();
        }
    }

    /**
     * 生产者
     */
    public static class Productor implements Runnable {

        Shop shop;

        Productor(Shop shop) {
            this.shop = shop;
        }

        @Override
        public void run() {

            for (int i = 0; i < 10; i++) {
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
    public  static class Consumer implements Runnable {

        Shop shop;

        Consumer(Shop shop) {
            this.shop = shop;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
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
