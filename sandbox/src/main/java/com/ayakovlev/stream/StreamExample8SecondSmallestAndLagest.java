package com.ayakovlev.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 8. Write a Java program to find the second smallest and largest elements in a list of integers using streams.
 */
public class StreamExample8SecondSmallestAndLagest {
    public static void main(String[] args){
//        String s = "123";
//        for(int i = 0; i < s.length(); i++){
//            char c = s.charAt(i); // '1'
//            System.out.println(c);
//            int c1 = s.charAt(i) - '0';// -48
//            System.out.println(c1);
//            System.out.println();
//        }
//        if(1<2)return;


        List<Integer> nums = Arrays.asList(1, 17, 54, 14, 14, 33, 45, -11);
        System.out.println("List of numbers: " + nums);
        // Find the second smallest element^
        Integer secondSmallest = nums.stream()
                .distinct()
                .sorted()
                .skip(1)
                .findFirst()
                .orElse(null);

        Integer secondLargest = nums.stream()
                .distinct()
                .sorted((x, y) -> Integer.compare(y, x))
                .skip(1)
                .findFirst()
                .orElse(null);

        System.out.println("\nSecond smallest element: " + secondSmallest);
        System.out.println("\nSecond largest element: " + secondLargest);

//        Comparator<Integer> c1 = new Comparator({(x, y) -> Integer.compare(y, x)});

        Comparator<Integer> c = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o2, o1);
            }

//            @Override
//            public boolean equals(Object obj) {
//                return false;
//            }
        };
    }
}
