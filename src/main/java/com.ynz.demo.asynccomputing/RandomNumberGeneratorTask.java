package com.ynz.demo.asynccomputing;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * The RandomNumberGeneratorTask class generates a random number after a delay.
 */
public class RandomNumberGeneratorTask implements Callable<String> {
    private final int taskNumber;

    /**
     * Constructs a new RandomNumberGeneratorTask.
     *
     * @param taskNumber The task number
     */
    public RandomNumberGeneratorTask(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Generates a random number after a delay.
     *
     * @return A string containing the task number, thread name, and generated random number
     */
    @Override
    public String call() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return String.format("taskNumber: %d %s : Result: %d", taskNumber, Thread.currentThread().getName(), generateRandomNumber());
    }

    /**
     * Generates a random number between 0 and 99.
     *
     * @return A random number between 0 and 99
     */
    private int generateRandomNumber() {
        return new Random().nextInt(100);
    }
}