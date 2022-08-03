package com.ynz.demo.asynccomputing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AsyncComputing {
  private static List<Future<Integer>> futures = new ArrayList<>();

  public static void main(String[] args) {

    ExecutorService executorService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // imagine we may run all tasks in parallel processes by a pool of threads.
    for (int i = 0; i < 8; i++) {
      Future<Integer> future = executorService.submit(new PrintNumberTask());
      futures.add(future);
    }

    // for the main thread, we check if all futures have been done.
    while (true) {
      boolean allFuturesDone = true;

      for (Future<Integer> future : futures) {
        allFuturesDone = allFuturesDone && future.isDone();
      }

      if (allFuturesDone) break;
    }

    futures.forEach(future -> {
      try {
        System.out.println(future.get());
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    });

    // terminate executor
    if (!executorService.isTerminated()) {
      try {
        executorService.awaitTermination(200, TimeUnit.MILLISECONDS);
        executorService.shutdown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
