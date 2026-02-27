package com.ayakovlev.stream;

import java.util.Arrays;
import java.util.List;

/**
 * https://www.w3resource.com/java-exercises/stream/index.php
 *
 * Write a Java program to calculate the sum of all even, odd numbers in a list using streams.
 */
public class StreamExample3OddAndEven {
    public static void main (String[] args){
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14);

// Sum of even numbers
int sumOfEvens = numbers.stream()
        .filter(num -> num % 2 == 0) // method creates from the first stream the second stream applying filter
        .mapToInt(k -> k.intValue())  // converts Stream<Integer> to IntStream, that has method sum()
        .sum();
System.out.println("Sum of even numbers: " + sumOfEvens);

        // Sum of odd numbers
        int sumOfOdds = numbers.stream()
                .filter(num -> num % 2 == 1)
                .mapToInt(k -> k.intValue())
                .sum();
        System.out.println("Sum of odd numbers: " + sumOfOdds);
    }
}
