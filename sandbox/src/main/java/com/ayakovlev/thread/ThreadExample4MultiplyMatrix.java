package com.ayakovlev.thread;

import java.util.HashSet;
import java.util.Set;

public class ThreadExample4MultiplyMatrix {
    private static final int[][] matrix1 = {
            {1, 2, 3,},
            {4, 5, 6,},
            {7, 8, 9,},
    };
    private static final int[][] matrix2 = {
            {9, 8, 7, },
            {6, 5, 4, },
            {3, 2, 1, },
    };
    private static final int MATRIX_SIZE = matrix1.length;
    private static final int NUM_THREADS = 2;
    public static void main(String[] args ){
        Set<Integer> set = new HashSet<Integer>();
//        set.


        int[][] result = new int[MATRIX_SIZE][MATRIX_SIZE];

        Thread[] threads = new Thread[NUM_THREADS];
        int segmentSize = MATRIX_SIZE / NUM_THREADS;

        for (int i = 0; i < NUM_THREADS; i++){
            int startIndex = i * segmentSize;
//            int endThread = (i == NUM_THREADS - 1) ?
        }
    }
}




















