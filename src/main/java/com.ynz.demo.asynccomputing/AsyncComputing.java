package com.ynz.demo.asynccomputing;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The AsyncComputing class demonstrates the use of CompletableFuture to run tasks asynchronously.
 */
@Slf4j
public class AsyncComputing {

    /**
     * The main method is the entry point of the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        final List<CompletableFuture<String>> futures = new ArrayList<>();

        // Create a thread pool with a number of threads equal to the number of available processors
        var threadNumber = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of available processors: " + threadNumber);
        log.info("Number of available processors: {}", threadNumber);
        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);

        // Submit 18 tasks to the executor service, and run each task asynchronously and return completable future
        for (int i = 0; i < 18; i++) {
            final int taskNumber = i;

            // Create a completable future that will return the result of the future
            var completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    return new RandomNumberGeneratorTask(taskNumber).call();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, executorService);

            futures.add(completableFuture);
        }

        // Wait for Task Completion Before Shutdown:
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allFutures.join();

        // For each future, get the result and print it
        for (var future : futures) {
            future.thenAccept(System.out::println)
                    .exceptionally(AsyncComputing::handleException);
        }

        // Shutdown the executor service gracefully
        executorService.shutdown();
        try {
            // Wait for the executor service to finish for 60 seconds
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                // If the executor service is not finished after 60 seconds, shut it down forcefully
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    /**
     * Handles exceptions by printing the stack trace.
     *
     * @param throwable The throwable to handle
     * @return null
     */
    private static Void handleException(Throwable throwable) {
        log.error("Exception occurred: {}", throwable.getMessage());
        return null;
    }
}