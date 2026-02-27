package com.ayakovlev.stream;

import java.util.Arrays;
import java.util.List;

public class StreamExample5FilterCount {
    /**
     * Write a Java program to count the number of strings in a list that start with a specific letter using streams.
     * @param args
     */
    public static void main(String [] args){
        List<String> colors = Arrays.asList("Red", "Greenb", "Blue", "Pink", "Brown");
        System.out.println("Original list of strings (colors): " + colors);
        char startingLetter = 'B';
        String startingString = String.valueOf(startingLetter);
        // Count strings starting with a specific letter
        long ctr = colors.stream()
                .filter(s -> s.startsWith(startingString))
                .count();
        System.out.println("\nNumber of colors starting with '" + startingString + "': " + ctr);

        char startingLetter2 = 'Y';
        String startingString2 = String.valueOf(startingLetter2);
        // Count strings starting with a specific letter
        ctr = colors.stream()
                .filter(s -> s.startsWith(startingString2))
                .count();
        System.out.println("\nNumber of colors starting with '" + startingString2 + "': " + ctr);
    }
}
