package com.ayakovlev.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Write a Java program to convert a list of strings to uppercase or lowercase using streams.
 */
public class StreamExample2Uppercase {
    public static void main(String[] args){
        List<String> colors = Arrays.asList("RED", "grEEn", "white", "Orange", "pink");
        System.out.println("List of strings: " + colors);
        // Convert strings to uppercase using stream
        List<String> uppercaseStrings = colors.stream()
                .map(strng -> strng.toUpperCase())
                .collect(Collectors.toList());
        System.out.println("\nUppercase Strings: " + uppercaseStrings);

        // Convert strings to lowercase using strreams
        List<String> lowercaseStrings = colors.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        System.out.println("Lowercase Strings: " + lowercaseStrings);

        int [] a = new int[]{1, 3, 2};
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));

    }
}
