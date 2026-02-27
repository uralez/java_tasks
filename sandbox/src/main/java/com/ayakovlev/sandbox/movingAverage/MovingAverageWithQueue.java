package com.ayakovlev.sandbox.movingAverage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class MovingAverageWithQueue implements MovingAverage {
    @Override
    public List<Double> getMovingAverageList(int[] in) {
        System.out.println("MovingAverageWithQueue");
        List<Double> movingAverageList = new ArrayList<>();
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(3);
        int step = 0;
        for(int n : in){
            addOrReplace(q, n);
            double average = getMovingAverage(q);
            System.out.println("step: " + ++step + ", moving average: " + average);
            movingAverageList.add(average);
        }
        return movingAverageList;
    }

    private double getMovingAverage(ArrayBlockingQueue<Integer> q) {
        if(q == null || q.size() <= 0){
            return 0;
        }
        double sum = 0.0;
        for(Integer n : q){
            sum += n;
        }
        return sum / q.size();
    }

    private <E> void addOrReplace(ArrayBlockingQueue<E> q, E e) {
        // queue.offer() добавляет элемент в конец очереди. Если элемент удачно добавлен, возвращается true, иначе - false.
        if(!q.offer(e)){
            q.poll(); // Возвращает с удалением элемент из начала очереди. Если очередь пуста, возвращается null.
            q.offer(e);
        }
    }
}
