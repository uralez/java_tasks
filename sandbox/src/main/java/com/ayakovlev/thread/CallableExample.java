package com.ayakovlev.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {
    public static void main(String[] args){
        int start = 3;
        int finish = 15;

        // Создаём пул из одного потока:
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Создаём задачу Callable, которая возвращает сумму чисел:
        Callable<Integer> sumTask = () -> {
            int sum = 0;
            for(int i = start; i <= finish; i++){
                sum += i;
            }
            return sum;// возвращаем результат
        };

        // Передаём задачу в ExecutorService
        // Запускаем задачу и возвращаем объект Future<T>
        Future<Integer> future = executorService.submit(sumTask);

        // Получаем результат (блокируется до завершения задачи)
        try {
            Integer result = future.get();
            System.out.println("Сумма чисел от " + start + " до " + finish + " равна " + result);
        }catch(InterruptedException | ExecutionException ex){
            ex.printStackTrace();
        }
//        start = 5;
//        finish = 10;
//        System.out.println("start snd finish = " + start + ", " + finish );

        // Завершаем работу пула потоков
        executorService.shutdown();
        /////////////////////
        Callable<Integer> task = () -> {
            return 30+15;
        };
        try {
            Integer result = task.call(); // вызываем напрямую
            System.out.println("result 2 = " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
