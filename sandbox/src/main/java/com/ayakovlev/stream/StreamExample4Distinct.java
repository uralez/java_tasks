package com.ayakovlev.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Write a Java program to remove all duplicate elements from a list using streams.
 */
public class StreamExample4Distinct {
    public static void main(String[] agrs){
        List<Integer> nums = Arrays.asList(10, 23, 22, 23, 24, 24, 33, 15, 26, 15);
        System.out.println("Original List of numbers: " + nums);
        // Remove duplicates
        List<Integer> distrinctNumbers = nums.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("After removing duplicates from the said list: " + distrinctNumbers);
    }
}
