package com.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class CompletableFutureRunnableExample {

    int numberOfThreads;
    ForkJoinPool executorService = ForkJoinPool.commonPool();


    public CompletableFutureRunnableExample(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        System.out.println("parallelism = " + executorService.getParallelism());
        System.out.println("poolsize = " + executorService.getPoolSize());
    }

    public void startAllThreads() throws ExecutionException, InterruptedException {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            futures.add(CompletableFuture.runAsync(new RunnableForLoop(String.valueOf(i+1),(i+1)*2)));
            System.out.println("poolsize = " + executorService.getPoolSize());
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).get();
        System.out.println("All Done !");

    }


    class RunnableForLoop implements Runnable {
        int times;
        String name;
        public RunnableForLoop(String name, int times) {
            this.times = times;
            this.name = name;
        }


        @Override
        public void run() {

            try {
                System.out.println("Task - " + name + ": START");
                for (int i = 0; i < times; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Task - " + name + ": INTERRUPTED, EXITING");
                        return;
                    }
                    System.out.println("Task - " + name + ": " + i);
                    Thread.sleep(10000);
                }
                System.out.println("Task - " + name + ": END");
            } catch (Exception e) {
                System.out.println("Task - " + name + ": " + e.getClass().getName() + ", EXITING");
            }
        }
    }
}
