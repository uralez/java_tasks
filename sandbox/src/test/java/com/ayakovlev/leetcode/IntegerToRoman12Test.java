package com.ayakovlev.leetcode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegerToRoman12Test {

    @ParameterizedTest
    @CsvSource({
            "1,     I",
            "10,    X",
            "3749,  MMMDCCXLIX",
            "58,    LVIII",
            "1994,  MCMXCIV",
    })
    void testInt2Roman(int in, String expected){
        IntegerToRoman12 obj = new IntegerToRoman12();
        String out = obj.intToRoman(in);
        System.out.println("---");
        System.out.println("in: " + in);
        System.out.println("expected: " + expected);
        System.out.println("out: " + out);
        assertEquals(expected, out);
    }
}
