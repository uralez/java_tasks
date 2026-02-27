package com.ayakovlev.leetcode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringToIntegerAtoi8Test {
    @ParameterizedTest
    @MethodSource("atoiTestCases")
    void testAtoi(String in, int expected){
        StringToIntegerAtoi8 obj = new StringToIntegerAtoi8();
        int out = obj.myAtoi(in);
        System.out.println("---");
        System.out.println("in      : " + in);
        System.out.println("expected: " + expected);
        System.out.println("out     : " + out);
        assertEquals(expected, out);
    }

    static Stream<Arguments> atoiTestCases(){
        return Stream.of(
                Arguments.of("" + Integer.MIN_VALUE, Integer.MIN_VALUE),
                Arguments.of("-91283472332", -2147483648),
                Arguments.of("42", 42),
                Arguments.of("   42", 42),
                Arguments.of("   -42", -42),
                Arguments.of("   -00042", -42),
                Arguments.of("" + ((long)Integer.MAX_VALUE + 1), Integer.MAX_VALUE)
        );
    }

}
