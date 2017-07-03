package com.dongly.thread.example1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tiger on 2017/6/27.
 */
public class CASDemo {

    public static void main(String[] args) {

        final CompareAndSwap swap = new CompareAndSwap();
        final AtomicInteger atomicInteger = new AtomicInteger();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (swap.compareAndSet(swap.getValue(), atomicInteger.incrementAndGet())) {
                        break;
                    }
                }
            }).start();
        }

    }

    static class CompareAndSwap {

        private Integer value = 0;

        public synchronized Integer getValue() {
            return value;
        }

        public synchronized Integer compareAndSwap(Integer expectedValue, Integer newValue) {
            Integer oldValue = value;
            if (oldValue.equals(expectedValue)) {
                value = newValue;
            }
            return oldValue;
        }

        public synchronized Boolean compareAndSet(Integer expectedValue, Integer newValue) {
            boolean b = expectedValue == compareAndSwap(expectedValue, newValue);
            System.out.println(b);
            if (b) {
                System.out.println(getValue());
            }
            return b;
        }

    }

}

