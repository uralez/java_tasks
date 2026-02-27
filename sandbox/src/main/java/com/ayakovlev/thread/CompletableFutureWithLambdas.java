package com.ayakovlev.thread;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWithLambdas {
    public static void main(String[] args){
        System.out.println("main start: " + Thread.currentThread().getName());
        CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            System.out.println("supplyAsynch: " + Thread.currentThread().getName());
            return 10;
        }).thenApply(x -> {
            System.out.println("thenApply: " + Thread.currentThread().getName());
            return x * 2;
        }).thenAccept(result -> {
            System.out.println("thenAccept: " + Thread.currentThread().getName());
            System.out.println("result = " + result);
        });

        System.out.println("main continues...");

        // Ждём, чтобы программа не завершилась
        // Плохой стиль, это искусственный код, но нам нужен вызов
        // cf.join() , чтобы получить результат работы потоков CompletableFuture()
        cf.join();
        System.out.println("main end");
    }

    private static void sleep(long ms){
        try{
            Thread.sleep(ms);
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }
}
