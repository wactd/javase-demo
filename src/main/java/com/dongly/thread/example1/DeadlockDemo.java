package com.dongly.thread.example1;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tiger on 2017/7/3.
 */
public class DeadlockDemo {

   static   Integer count1 = 0;
    static AtomicInteger count2 = new AtomicInteger(0);

   synchronized void c1() {
        for (int i = 0; i < 100000; i++) {
            ++count1;
        }
    }

    void c2() {
        for (int i = 0; i < 100000; i++) {
            count2.incrementAndGet();
        }
    }

   //  static void t1() {
   //      Instant start = Instant.now();
   //      DeadlockDemo demo = new DeadlockDemo();
   //      List<Thread> threads1 = Arrays.asList(new Thread(demo::c1, "t11"),
   //              new Thread(demo::c1, "t12"), new Thread(demo::c1, "t13"),
   //              new Thread(demo::c1, "t14"), new Thread(demo::c1, "t15"));
   //
   //      threads1.forEach(thread -> thread.start());
   //
   //      threads1.forEach(thread -> {
   //          try {
   //              thread.join();
   //          } catch (InterruptedException e) {
   //              e.printStackTrace();
   //          }
   //      });
   //      System.out.println("count1 = " + demo.count1);
   //      Instant end = Instant.now();
   //      System.out.println(Duration.between(start, end).toMillis());
   //  }
   //
   //
   // static void t2() {
   //      Instant start = Instant.now();
   //      DeadlockDemo demo = new DeadlockDemo();
   //      List<Thread> threads = Arrays.asList(new Thread(demo::c2), new Thread(demo::c2), new Thread(demo::c2),
   //              new Thread(demo::c2), new Thread(demo::c2));
   //
   //      threads.forEach(thread -> thread.start());
   //
   //      threads.forEach(thread -> {
   //          try {
   //              thread.join();
   //          } catch (InterruptedException e) {
   //              e.printStackTrace();
   //          }
   //      });
   //      System.out.println("count2 = " + count2.get());
   //      Instant end = Instant.now();
   //      System.out.println(Duration.between(start, end).toMillis());
   //  }

    public static void main(String[] args) {
        Instant start = Instant.now();
        DeadlockDemo demo = new DeadlockDemo();
        // List<Thread> threads1 = Arrays.asList(new Thread(demo::c1, "t11"),
        //         new Thread(demo::c1, "t12"), new Thread(demo::c1, "t13"),
        //         new Thread(demo::c1, "t14"), new Thread(demo::c1, "t15"));

        List<Thread> threads1 = Arrays.asList(new Thread(demo::c2, "t11"),
                new Thread(demo::c2, "t12"), new Thread(demo::c2, "t13"),
                new Thread(demo::c2, "t14"), new Thread(demo::c2, "t15"));

        threads1.forEach(thread -> thread.start());

        threads1.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("count2 = " + count2.get());
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
   }



}
