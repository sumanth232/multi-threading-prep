package com.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class CompletableFutureCallableExample {

    int numberOfThreads;


    public CompletableFutureCallableExample(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public void startAllThreads() throws ExecutionException, InterruptedException {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            String name = String.valueOf(i+1);
            CompletableFuture<?> future = CompletableFuture.supplyAsync(new MySuppplier(name)).whenComplete((res,ex) -> {
                System.out.println("Future complete with result: " + res);
            });
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).get();
        System.out.println("All Done !");

    }


    class MySuppplier implements Supplier<String> {
        String name;
        public MySuppplier(String name) {
            this.name = name;
        }

        @Override
        public String get() {
            return "DONE " + name;
        }
    }
}
