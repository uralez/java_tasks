package com.ayakovlev.leetcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LetterCombinationsOfAPhoneNumber_17Test {
    private LetterCombinationsOfAPhoneNumber_17 obj;
    @BeforeEach
    public void setUp(){
        obj = new LetterCombinationsOfAPhoneNumber_17();
    }

    record TestCase(String digits, List<String> expected){}

    static Stream<TestCase> testCases(){
        return Stream.of(
                new TestCase("23", Arrays.asList("ad", "af", "ae", "bd", "be", "bf", "cd", "ce", "cf")),
                new TestCase("2", Arrays.asList("a", "b", "c")),
                new TestCase("3", Arrays.asList("d", "e", "f")),
                new TestCase("9", Arrays.asList("w", "x", "y", "z"))
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void letterCombinations(TestCase tc){
        assertEquals(
                new HashSet<>(tc.expected()),
                new HashSet<>(obj.letterCombinations(tc.digits())),
                "digits=" + tc.digits()
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    void emptyOrNullReturnsEmptyList(String input){
        System.out.println("input: " + input);
        assertTrue(obj.letterCombinations(input).isEmpty());
    }
}
