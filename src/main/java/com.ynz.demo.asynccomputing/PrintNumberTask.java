package com.ynz.demo.asynccomputing;

import java.util.Random;
import java.util.concurrent.Callable;

public class PrintNumberTask implements Callable<Integer> {
    private int taskSequence;

    public PrintNumberTask(int taskSequence) {
        this.taskSequence = taskSequence;
    }

    @Override
    public Integer call() throws Exception {
        return new Random().nextInt(100);
    }
}
