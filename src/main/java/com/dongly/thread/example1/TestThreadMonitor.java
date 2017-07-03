package com.dongly.thread.example1;

import java.util.concurrent.TimeUnit;

/**
 * Created by tiger on 2017/6/29.
 */
public class TestThreadMonitor {

    public static void main(String[] args) {
        Printer printer = new Printer();

        new Thread(() -> {
            printer.printA();
        }).start();

        new Thread(() -> {
            printer.printB();
        }).start();
    }

    static class Printer {


        public synchronized void printA() {
            System.out.println(" ------- A -------");
        }

        public synchronized void printB() {
            System.out.println(" ------- B -------");
        }
    }

}
