package com.ayakovlev.leetcode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegularExpressionMatching10Test {
    @ParameterizedTest
    @CsvSource({
            "a, a, true",
            "bc, ab, false",
            "aa, a, false",
            "aa, a*, true",
            "ab, .*, true",
            "aab, c*a*b, true",
            "mississippi, mis*is*ip*., true",
            "ab, .*c, false",
            "aaa, a*a, true",
            "aaa, ab*ac*a, true",
            "a, .*..a*, false",
            "aaaaaaaaaaaaaaaaaaab, a*a*a*a*a*a*a*a*a*a*, false",
    })
    void qqq(String s, String p, boolean expected){
        RegularExpressionMatching10 obj = new RegularExpressionMatching10();
        boolean out = obj.isMatch(s, p);
        System.out.println("---");
        System.out.println("s       :" + s);
        System.out.println("p       :" + p);
        System.out.println("expected:" + expected);
        System.out.println("out     :" + out);
        assertEquals(expected, out);
    }
}
