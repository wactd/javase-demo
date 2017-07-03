package com.dongly.thread.example1;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by tiger on 2017/6/29.
 */
public class TestScheduledThreadPool {


    public static void main(String[] args) {

        ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);

        try {
            pool.schedule(() -> 1 / 0, 1, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!pool.isTerminated()) {
                System.err.println("cancel non-finished tasks!!!");
                pool.shutdownNow();
            }
            System.out.println("执行完毕");
        }

    }

}
