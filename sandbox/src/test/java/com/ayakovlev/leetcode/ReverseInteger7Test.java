package com.ayakovlev.leetcode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReverseInteger7Test {
    @ParameterizedTest
    @MethodSource("reverseTestCases")
    void testReverseInteger(int in, int expected){
        ReverseInteger7 obj = new ReverseInteger7();
        int out = obj.reverse(in);
        System.out.println("---");
        System.out.println("in      :" + in);
        System.out.println("expected:" + expected);
        System.out.println("out     :" + out);
        assertEquals(expected, out);
    }

    static Stream<Arguments> reverseTestCases(){
        return Stream.of(
                Arguments.of(123, 321),
                Arguments.of(-123, -321),
                Arguments.of(120, 21),
                Arguments.of((int)Math.pow(2, 31)-1, 0),
                Arguments.of(1534236469, 0)
        );
    }
}
