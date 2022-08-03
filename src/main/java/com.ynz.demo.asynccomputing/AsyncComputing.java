package com.ynz.demo.asynccomputing;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AsyncComputing {

  public static void main(String[] args) {

    ExecutorService executorService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    for (int i = 0; i < 20; i++) {
      Future<Integer> future = executorService.submit(new PrintNumberTask(i));
      Integer result = null; // blocking
      try {
        result = future.get();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
      System.out.println("result: " + result);
    }

    // get task result from the future.

    if (!executorService.isTerminated()) {
      try {
        executorService.awaitTermination(10, TimeUnit.MILLISECONDS);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      executorService.shutdown();
    }
  }
}
