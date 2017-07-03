package com.dongly.thread.example1;

import java.time.Clock;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created by tiger on 2017/6/28.
 */
public class TestCountDownLatch {


    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(5);

        TestLathch lathch = new TestLathch(latch);

        Long start = Clock.systemDefaultZone().millis();


        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 5; i++) {
            threadPool.submit(lathch);
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdownNow();
            System.out.println("shutdown finished " + latch.getCount());
        }

        Long end = Clock.systemDefaultZone().millis();
        System.out.println("总耗时： " + (end - start));

    }

    static class TestLathch implements Runnable {

        CountDownLatch latch;

        TestLathch(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                int i = ThreadLocalRandom.current().nextInt(10);
                System.out.println(Thread.currentThread().getName() + " slept " + i + " seconds!");
                TimeUnit.SECONDS.sleep(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();

            System.out.println(Thread.currentThread().getName() + " task finished !");
        }
    }

}
