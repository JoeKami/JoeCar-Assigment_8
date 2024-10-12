package com.coderscampus.assignment;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Assignment8 assignment = new Assignment8();
        ExecutorService executor = Executors.newCachedThreadPool();
        List<CompletableFuture<List<Integer>>> futures = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            CompletableFuture<List<Integer>> future = CompletableFuture.supplyAsync(assignment::getNumbers, executor);
            futures.add(future);
        }

        List<Integer> allNumbers = futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .toList();

        Map<Integer, Long> frequencyMap = allNumbers.stream()
                .collect(Collectors.groupingBy(num -> num, Collectors.counting()));

        for (int i = 0; i <= 14; i++) {
            System.out.print(i + "=" + frequencyMap.getOrDefault(i, 0L) + ", ");
        }

        executor.shutdown();
    }
}
