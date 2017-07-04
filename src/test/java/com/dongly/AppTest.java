package com.dongly;

import com.dongly.thread.example1.MyContainer;
import com.dongly.thread.example1.TestAlternate;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class AppTest {


    private AppTest() {
        System.out.println("constructor init...");
    }

    // public static final AppTest INSTANCE = new AppTest();

    private static class Lazy {
        private static final AppTest INSTANCE = new AppTest();
    }

    public static AppTest getInstance() {
        return Lazy.INSTANCE;
    }

    public static void main(String[] args) {
        System.out.println(getInstance() == getInstance());
    }

}
