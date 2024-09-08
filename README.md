### Introduction to `CompletableFuture`

`CompletableFuture` is a versatile and powerful class introduced in Java 8 as part of the `java.util.concurrent` package. It represents a **future result** of an asynchronous computation and provides a rich API for working with tasks that are executed asynchronously.

Unlike the older `Future` interface, which only allows checking if a task is done or blocking to retrieve the result, `CompletableFuture` offers more advanced features for **non-blocking asynchronous programming**, such as the ability to chain tasks, handle exceptions, and compose multiple asynchronous operations seamlessly.

#### Key Features of `CompletableFuture`:

1. **Asynchronous Task Execution**: 
   - With methods like `supplyAsync()` and `runAsync()`, you can run tasks in a separate thread without blocking the current one.
   - Example:
     ```java
     CompletableFuture.supplyAsync(() -> "Result from async task")
                      .thenAccept(result -> System.out.println("Received: " + result));
     ```

2. **Chaining and Transformations**: 
   - `CompletableFuture` allows chaining multiple asynchronous operations together. You can transform results using `thenApply()` or perform actions once a task is completed with `thenAccept()`.
   - Example:
     ```java
     CompletableFuture.supplyAsync(() -> "Hello")
                      .thenApply(result -> result + " World")
                      .thenAccept(System.out::println);
     ```
   - Output: `Hello World`

3. **Combining Multiple Futures**: 
   - You can combine multiple `CompletableFuture`s to wait for all of them to complete or proceed when any of them completes. Methods like `allOf()` and `anyOf()` handle this:
     ```java
     CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Task 1");
     CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Task 2");

     CompletableFuture<Void> combined = CompletableFuture.allOf(future1, future2);
     combined.join(); // Waits for both tasks to complete
     ```

4. **Exception Handling**: 
   - `CompletableFuture` has built-in methods for handling exceptions in an asynchronous pipeline. `exceptionally()` and `handle()` can be used to recover from errors or apply alternative logic when a task fails.
   - Example:
     ```java
     CompletableFuture.supplyAsync(() -> {
         if (true) throw new RuntimeException("Error occurred");
         return "Success";
     }).exceptionally(ex -> "Recovered from error")
       .thenAccept(System.out::println);
     ```

5. **Non-blocking Waiting**: 
   - While `Future.get()` blocks the current thread until the result is available, `CompletableFuture` offers non-blocking methods like `thenApply()`, `thenAccept()`, and `whenComplete()` that allow you to process results without waiting.

6. **Composition with `thenCompose()` and `thenCombine()`**:
   - `thenCompose()` is used to flatten nested `CompletableFuture`s, allowing tasks to be dependent on the result of previous asynchronous operations.
   - `thenCombine()` allows you to combine two independent tasks into a single result.
   - Example:
     ```java
     CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 5);
     CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 10);

     future1.thenCombine(future2, Integer::sum)
            .thenAccept(result -> System.out.println("Sum: " + result));
     ```

### Benefits of Using `CompletableFuture`:
- **Non-blocking and scalable**: It avoids the inefficiencies of blocking threads by using callback-style programming, which helps scale applications better.
- **Improves code readability**: By chaining operations, it avoids deeply nested callback structures (also known as "callback hell").
- **Better exception handling**: It makes handling asynchronous errors easier with built-in exception-handling methods.
- **Flexible and composable**: Tasks can be easily combined, transformed, and managed using a clear and concise API.

### Example: Fetching and Processing Data Asynchronously

```java
CompletableFuture.supplyAsync(() -> fetchData())
    .thenApply(data -> processData(data))
    .thenAccept(result -> System.out.println("Processed result: " + result))
    .exceptionally(ex -> {
        System.err.println("Error: " + ex.getMessage());
        return null;
    });
```

In this example:
1. `fetchData()` runs asynchronously.
2. The result is processed with `processData()`.
3. The final result is printed, and errors are handled with `exceptionally()`.

### Conclusion:
`CompletableFuture` brings a modern approach to asynchronous programming in Java. It simplifies the execution of non-blocking tasks, enhances error handling, and allows for smooth composition of multiple asynchronous operations, making it an essential tool for building efficient and scalable concurrent applications.


# Java 8 CompletableFuture Explanation

`CompletableFuture` is a powerful feature introduced in Java 8 for working with asynchronous computations. It provides a rich set of methods for composing, combining, and handling asynchronous operations.

## Key Concepts and Features

### 1. Creating a CompletableFuture

You can create and start an asynchronous computation using `CompletableFuture.supplyAsync()`:

```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    // Simulating a long-running task
    try {
        TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
        throw new IllegalStateException(e);
    }
    return "Result of the asynchronous computation";
});
```

### 2. Attaching Callbacks

You can attach callbacks to be executed when the future completes:

```java
future.thenAccept(result -> System.out.println("Got the result: " + result));
```

### 3. Chaining Operations

Multiple operations can be chained using methods like `thenApply()`:

```java
CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Hello")
        .thenApply(s -> s + " World")
        .thenApply(String::toUpperCase);

System.out.println(future2.get()); // Prints: HELLO WORLD
```

### 4. Combining Multiple Futures

You can combine the results of two independent futures:

```java
CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> "World");
CompletableFuture<String> combinedFuture = future3.thenCombine(future4, (s1, s2) -> s1 + " " + s2);

System.out.println(combinedFuture.get()); // Prints: Hello World
```

### 5. Handling Errors

`CompletableFuture` provides methods for handling exceptions:

```java
CompletableFuture<String> futureWithException = CompletableFuture.supplyAsync(() -> {
    if (true) throw new RuntimeException("Oops!");
    return "This will not be returned";
}).exceptionally(ex -> "Error: " + ex.getMessage());

System.out.println(futureWithException.get()); // Prints: Error: java.lang.RuntimeException: Oops!
```

### 6. Timeouts

You can specify timeouts for your asynchronous operations:

```java
CompletableFuture<String> futureWithTimeout = CompletableFuture.supplyAsync(() -> {
    try {
        TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
        throw new IllegalStateException(e);
    }
    return "Result after 2 seconds";
}).completeOnTimeout("Timeout after 1 second", 1, TimeUnit.SECONDS);

System.out.println(futureWithTimeout.get()); // Prints: Timeout after 1 second
```

## Conclusion

`CompletableFuture` provides a powerful way to work with asynchronous computations in Java. It allows you to compose, combine, and handle errors in asynchronous operations in a more readable and maintainable way compared to traditional `Future` implementations.
