package com.dongly;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(1);

        try {
            Future<Integer> future = pool.submit(() -> {
                TimeUnit.SECONDS.sleep(3);
                return 1;
            });

            // System.out.println(future.get());

            // 执行完毕 请求关闭线程池,并停止接受新的任务
            pool.shutdown();

            while (!pool.awaitTermination(1, TimeUnit.SECONDS)) System.out.println("执行中...");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (!pool.isTerminated()) {
                pool.shutdownNow();
                System.out.println("cancel non-finished tasks");
            }
            System.out.println("shutdown finished ");
        }
    }
}
