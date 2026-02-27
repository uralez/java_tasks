package com.ayakovlev.stream;

import java.util.Arrays;
import java.util.List;

/**
 * Write a Java program to find the maximum and minimum values in a list of integers using streams.
 */
public class StreamExample7MinMax {
    public static void main(String[] args){
        List<Integer> nums = Arrays.asList(1, 17, 54, 14, 14, 33, 45, -11);
        System.out.println("Original list of numbers: " + nums);
        // Find the maximum value
        Integer max_val = nums.stream()
                .max((x, y) -> Integer.compare(x, y))
//                .max(Integer::compare)
        .orElse(null);

        Integer min_val = nums.stream()
                .min(Integer::compare)
                .orElse(null);

        System.out.println("\nMaximum value of the said list: " + max_val);
        System.out.println("\nMinimum value of the said list: " + min_val);
    }

    public long maxSum(int[][] grid, int[] limits, int k) {
        int n = grid.length; // кол-во строк
        int m = grid[0].length;//кол-во столбцовв в первой строке
        int[] maxArray = new int[sumLimits(limits)];
        // List<Integer> list = new ArrayList<Integer>();
        int i_maxs = 0;
        for(int i = 0; i < n; i++){
            int[] row = grid[i];
            int[] maxs = getMaxs(row, limits[i]);
            for(int j = 0; j < maxs.length; j++){
                maxArray[i_maxs++] = maxs[j];
            }
        }
        Arrays.sort(maxArray);
        long sum = 0;
        for(int i = 1; i <= k; i++){
            sum += maxArray[maxArray.length - i];
        }
        return sum;
    }

    int[] getMaxs(int[] row, int limit){
        Arrays.sort(row);
//        Arrays.sort(row, Comparator.reverseorder());
        return Arrays.copyOfRange(row, row.length-limit, row.length);
    }

    int sumLimits(int[] limits){
        return Arrays.stream(limits)
                .sum();
    }

}
