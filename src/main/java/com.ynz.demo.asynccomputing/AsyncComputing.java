package com.ynz.demo.asynccomputing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The AsyncComputing class demonstrates the use of CompletableFuture to run tasks asynchronously.
 */
public class AsyncComputing {
    private static final List<CompletableFuture<String>> futures = new ArrayList<>();

    /**
     * The main method is the entry point of the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Create a thread pool with a number of threads equal to the number of available processors
        var threadNumber = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of available processors: " + threadNumber);

        // Create a thread pool with a number of threads equal to the number of available processors
        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);

        // Submit 18 tasks to the executor service, and run each task asynchronously and return completable future
        for (int i = 0; i < 18; i++) {

            // Run the task asynchronously and return a completable future
            var future = CompletableFuture.supplyAsync(() -> {
                try {
                    return new RandomNumberGeneratorTask().call();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, executorService);

            futures.add(future);
        }

        // For each future, get the result and print it
        for (var future : futures) {
            future.thenAccept(System.out::println)
                  .exceptionally(throwable -> {
                      System.out.println(throwable.getMessage());
                      return null;
                  });
        }

        // Shutdown the executor service
        executorService.shutdown();
    }
}