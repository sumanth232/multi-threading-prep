package com.interview;

import java.util.ArrayList;
import java.util.List;

public class ParallelStreamExample {

    public ParallelStreamExample() {
    }

    public void doPrallelProcessing() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i+1);
        }

        list.stream().forEach(k -> System.out.println(k + " - " + Thread.currentThread().getName()));

        System.out.println("\nStarting Parallel Processing...");
        list.parallelStream().forEach(k -> System.out.println(k + " - " + Thread.currentThread().getName()));

        list.parallelStream().map(k -> 2*k).forEach(k -> System.out.println(k));

        System.out.println("Sum = " + list.parallelStream().map(k -> 2*k).reduce(0, (a,b) -> a+b));
        System.out.println("Max = " + list.parallelStream().max((a,b) -> a-b).get());
        System.out.println("Min = " + list.parallelStream().min((a,b) -> a-b).get());
    }
}
