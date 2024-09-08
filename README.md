### Introduction to `CompletableFuture`

`CompletableFuture` is a powerful class in Java that allows for asynchronous programming by representing a future result of an operation. Unlike `Future`, `CompletableFuture` provides methods to handle asynchronous computation, allowing developers to write non-blocking code that can be chained, combined, and executed asynchronously.

#### Key Features:
- **Non-blocking**: You can run tasks asynchronously without waiting for them to complete.
- **Chaining**: `CompletableFuture` allows you to chain tasks together using methods like `.thenApply()`, `.thenAccept()`, and `.thenCompose()`.
- **Combining**: You can combine multiple `CompletableFuture`s to execute tasks concurrently using `.allOf()` or `.anyOf()`.
- **Exception Handling**: Provides methods like `.exceptionally()` to handle exceptions without blocking the main thread.

#### Example:

```java
CompletableFuture.supplyAsync(() -> "Hello")
    .thenApply(result -> result + " World!")
    .thenAccept(System.out::println);
```

In this example, the tasks are executed asynchronously and chained together. The result is printed once the entire chain completes.

`CompletableFuture` makes it easier to handle complex asynchronous workflows in a readable and non-blocking manner.
