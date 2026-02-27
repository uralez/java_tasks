package com.ayakovlev.stream;

import java.util.*;

/**
 * https://www.w3resource.com/java-exercises/stream/index.php
 *
 * 1. Write a Java program to calculate the average of a list of integers
 * using streams.
 */
public class StreamExample1Average {
    public static void main(String[] args){
        List<Integer> nums = Arrays.asList(1, 3, 6, 8, 10, 18, 36);
        System.out.println("List of numbers: " + nums);

        // Calculate the average using streams
        double average = nums.stream()
                .mapToDouble(intgr -> intgr.doubleValue())
                .average()
                .orElse(0.0);

        System.out.println("Average value of the said numbers: " + average);
        ///
        Map<Integer, Object> map = null;
        map = new HashMap<Integer, Object>();
/* TO BE CONTINUED
        map.put(new Integer(val));
        map.remove(new Integer(val));
        map.remove(7);
        map.containsKey(5);
        List list = new LinkedList();
        list.remove(7);
        list.set(3, new Integer(3));
        Integer a = Integer.valueOf(5);
        int b = Integer.valueOf(5).intValue();
*/
    }


}
