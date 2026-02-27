package com.ayakovlev.thread;

import java.util.*;

/**
 * chatGPT
 */
public class MaxSum {
    public static long maxSum(int[][] grid, int[] limits, int k) {
        List<Integer> topElements = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            Arrays.sort(grid[i]);
            for (int j = 0; j < limits[i]; j++) {
                topElements.add(grid[i][grid[i].length - 1 - j]);
            }
        }

        Collections.sort(topElements, Collections.reverseOrder());

        long sum = 0;
        for (int i = 0; i < Math.min(k, topElements.size()); i++) {
            sum += topElements.get(i);
        }

        return sum;
    }

}
