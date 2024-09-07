package com.ynz.demo.asynccomputing;

import java.util.Random;
import java.util.concurrent.Callable;

public class RandomNumberGeneratorTask implements Callable<String> {

    @Override
    public String call() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Thread.currentThread().getName() + " : " + "Result: " + generateRandomNumber();
    }

    private int generateRandomNumber() {
        return new Random().nextInt(100);
    }
}
