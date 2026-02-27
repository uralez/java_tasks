package com.ayakovlev.leetcode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZigzagConversion6Test {
    @ParameterizedTest
    @CsvSource({
            "PAYPALISHIRING, 3, PAHNAPLSIIGYIR",
            "PAYPALISHIRING, 4, PINALSIGYAHRPI",
            "A, 1, A",
            "ABCD, 3, ABDC",
    })
    void testConversion(String in, int n, String expected){
        ZigzagConversion6 obj = new ZigzagConversion6();
        String out = obj.convert(in, n);
        System.out.println("---");
        System.out.println("in: " + in);
        System.out.println("n: " + n);
        System.out.println("expected: " + expected);
        System.out.println("out: " + out);
        assertEquals(expected, out);
    }
}
