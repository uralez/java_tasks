package com.ayakovlev.designpatterns.singleton;

import com.ayakovlev.designpatterns.creational.singleton.Singleton;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdvansedSingletonTest {
    private static final int NUMBER_OF_THREADS = 10;

    @Test
    void testConcurentSingleton(){
        // Класс ExecutorService нужен для удобного, гибкого и безопасного управления потоками. Он позволяет запускать, планировать и завершать задачи в пуле потоков, не создавая вручную Thread каждый раз.
        // The ExecutorService class provides convinient, flexible and safe thread management. It allows you to launch, schedule and terminate tasks in a thread pool without manually creating a Thread each time.
        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        Callable<Integer> task = () -> { // call()
            Thread current = Thread.currentThread();
            Singleton instance = Singleton.getInstance();
            System.out.println("Thread name: " + current.getName() +
                    ", ID: " + current.getId() +
                    ", Instance hash: " + instance.hashCode());
            return instance.hashCode();
        };

        // Запуск всех задач и сбор результатов
        // Run all tasks and collect results
        List<Future<Integer>> results = new ArrayList<Future<Integer>>();
        for(int i = 0; i < NUMBER_OF_THREADS; i++){
            results.add(executor.submit(task));
        }

        // Проверка: все hashCode должны быть одинаковыми
        // Check: all hashcodes must be the same
        Set<Integer> uniqueHashes = new HashSet<>();
        for (Future<Integer> result : results){
            try {
                // Метод get() блокирует основной поток пока в данную Future<Integer> result не придёт результат.
                // The get() method blocks the main thread until a result is received in the given Future<Integer> result.
                uniqueHashes.add(result.get());
            } catch (InterruptedException ex) {
                ex.printStackTrace();;
            } catch (ExecutionException ex) {
                ex.printStackTrace();
            }
        }

        executor.shutdown();

        System.out.println("\nUnique instance hash code count: " + uniqueHashes.size());
        if(uniqueHashes.size() == 1){
            System.out.println("✅ Singleton verified: only one instance created.");
        }else{
            System.out.println("❌ Singleton violation: multiple instances detected!");
            int i = 0;
            for(Integer h : uniqueHashes){
                System.out.println(i++ + ": " + h);
            }
        }
        assertEquals(1, uniqueHashes.size());
    }
}
