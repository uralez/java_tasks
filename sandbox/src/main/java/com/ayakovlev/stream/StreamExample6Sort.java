package com.ayakovlev.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamExample6Sort {
    public static void main(String [] args){
        Integer[] a = new Integer[]{1, 5, 2, 3, 4};
        System.out.println("before: " + Arrays.toString(a));
        Arrays.sort(a);
        System.out.println("ascendeing: " + Arrays.toString(a));
        Arrays.sort(a, Comparator.reverseOrder());
        System.out.println("descending: " + Arrays.toString(a));

        List<String> colors = Arrays.asList("Red", "Green", "Blue", "Pink", "Brown");
        System.out.println("Original list of strings (colors)" + colors);
        // Sort list in ascending order
        List<String> ascendingOrder = colors.stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("\nSorted in Ascending Order: " + ascendingOrder);

        // Sort strings in descending order
        List<String> descendingOrder = colors.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
//                .collect();
        System.out.println("\nSorted in Descending Order: " + descendingOrder);
    }
}
