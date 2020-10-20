package com.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FuturesExample {

    int numberOfThreads;
    //ForkJoinPool executorService = ForkJoinPool.commonPool();
    ExecutorService executorService = Executors.newFixedThreadPool(11);


    public FuturesExample(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public void startAllThreadsCancelOthersIfOneCompletes() throws ExecutionException, InterruptedException {
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            Future<?> future = executorService.submit(new RunnableForLoop(String.valueOf(i + 1), (i + 1) * 2));
            futures.add(future);
        }

        //Thread.sleep(1000);
        for (Future<?> future : futures) {
            future.cancel(true);
        }
        //CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).get();
        System.out.println("All Done !");

    }

    public void invokeAll() throws ExecutionException, InterruptedException {
        List<Callable<Object>> tasks = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            tasks.add(Executors.callable(new RunnableForLoop(String.valueOf(i + 1), (i + 1) * 2)));
        }

        Object resOfACompletedFuture = executorService.invokeAny(tasks);// only waits for one future to complete and cancel the others
        //executorService.invokeAll(tasks); // waits for all futures to complete
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
                    //Thread.sleep(100);
                }
                System.out.println("Task - " + name + ": END");
            } catch (Exception e) {
                System.out.println("Task - " + name + ": " + e.getClass().getName() + ", EXITING");
            }
        }
    }


}
