package com.ayakovlev.sandbox.movingAverage;

import java.util.ArrayList;
import java.util.List;

public class MovingAverageWithArray implements MovingAverage{
    private static final int N = 3;
    @Override
    public List<Double> getMovingAverageList(int[] in) {
        System.out.println("MovingAverageWithArray");
        List<Double> movingAverageList = new ArrayList<>();
        int[] workingArray = new int[N];
        int pointer = 0; // Указатель, куда мы будем вставлять следующий элемент
        int counter = 0; // Счётчик, сколько настоящих элементов сейчас в рабочем массиве
        int step = 0;
        for(int n : in){
            workingArray[pointer] = n;
            pointer = (pointer + 1) % N;
            counter = Math.min(counter+1, workingArray.length);
            double average = getAverage(workingArray, counter);
            System.out.println("Step " + ++step + ": average: " + average);
            movingAverageList.add(average);
        }
        return movingAverageList;
    }

    private double getAverage(int[] workingArray, int counter) {
        if(workingArray == null || workingArray.length <= 0 || counter <= 0){
            return 0;
        }
        double sum = 0.0;
        counter = Math.min(counter, workingArray.length);
        for(int i = 0; i < counter; i++){
            sum += workingArray[i];
        }
        return sum / counter;
    }
}