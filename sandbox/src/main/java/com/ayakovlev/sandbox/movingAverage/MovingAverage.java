package com.ayakovlev.sandbox.movingAverage;

import java.util.List;

/**
 * Рассчёт скользящего среднего с помощью очереди или массива.
 *
 * Дан поток чисел. На каждом шаге мы должны выдавать среднее последних трёх чисел.
 */
public interface MovingAverage {
    List<Double> getMovingAverageList(int[] in);
}
