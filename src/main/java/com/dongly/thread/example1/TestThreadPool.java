package com.dongly.thread.example1;

import java.time.Clock;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by tiger on 2017/6/28.
 */
public class TestThreadPool {

    public static void main(String[] args) {
        final AtomicLong atom = new AtomicLong();

        ThreadDemo demo = new ThreadDemo(atom);

        Long start = Clock.systemDefaultZone().millis();

        ExecutorService pool = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            pool.submit(demo);
        }

        pool.shutdown();

        try {

            while (!pool.awaitTermination(1, TimeUnit.SECONDS)) {
                System.out.println("in execute..." + atom.get());
            }

        } catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        } finally {
            if (!pool.isTerminated()) {
                System.err.println("cancel non-finished tasks");
                pool.shutdownNow();
            }
            System.out.println("shutdown finished " + atom.get());
        }

        Long end = Clock.systemDefaultZone().millis();
        System.out.println("total time ： " + (end - start));

    }


    static class ThreadDemo implements Runnable {

        AtomicLong atom;

        private ThreadDemo(AtomicLong atom) {
            this.atom = atom;
        }

        @Override
        public void run() {
            Long aLong = 0L;
            try {
                aLong = ThreadLocalRandom.current().nextLong(10);
                System.out.println(Thread.currentThread().getName() + " slept " + aLong + " seconds");
                TimeUnit.SECONDS.sleep(aLong);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atom.addAndGet(aLong);
        }
    }

}
