package com.ayakovlev.thread;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Consumer;

public class CompletableFutureWithoutLambdas {
    public static void main(String[] args){
        System.out.println("main start: " + Thread.currentThread().getName());

        CompletableFuture<Void> cf = CompletableFuture
                .supplyAsync(new Supplier<Integer>(){
                    @Override
                    public Integer get(){
                        sleep(1000);
                        System.out.println("supplyAsynch: " + Thread.currentThread().getName());
                        return 11;
                    }
                }).thenApply(new Function<Integer, Integer>(){
                    @Override
                    public Integer apply(Integer x){
                        System.out.println("thenApply: " + Thread.currentThread().getName());
                        return x * 2;
                    }
                }).thenAccept(new Consumer<Integer>(){
                    @Override
                    public void accept(Integer result){
                        System.out.println("thenAccept: " + Thread.currentThread().getName());
                        System.out.println("result = " + result);
                    }
                });

        System.out.println("main continues...");

        cf.join();

        System.out.println("main end.");
    }

    private static void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
