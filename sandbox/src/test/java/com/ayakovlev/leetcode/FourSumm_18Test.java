package com.ayakovlev.leetcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FourSumm_18Test {
    /*
    * Rank 1 703 600
    * Rank 1 693 094 - 22 Sept 2025
    * */

    private FourSumm_18 fourSumm;

    record TestCase(int[] nums, int target, int[][] expected){
        List<List<Integer>> expectedAsList(){
            return Arrays.stream(expected).map(row -> Arrays.stream(row).boxed().collect(Collectors.toList())).collect(Collectors.toList());
        }
    }

    static Stream<TestCase> testCases(){
        return Stream.of(
                new TestCase(new int[]{1, 0, -1, 0, -2, 2},
                        0,
                        new int[][]{{-2, -1, 1, 2}, {-2, 0, 0, 2}, {-1, 0, 0, 1}}),
                new TestCase(new int[]{2, 2, 2, 2, 2},
                        8,
                        new int[][]{{2, 2, 2, 2}}),
                new TestCase(
                        new int[]{1000000000, 1000000000, 1000000000, 1000000000},
                        -294967296,
                        new int[][]{}
                )
        );
    }

    @BeforeEach
    public void setUp(){
        fourSumm = new FourSumm_18();
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void fourSum(TestCase tc){
        assertEquals(
                tc.expectedAsList(),
                fourSumm.fourSum(tc.nums(), tc.target()),
                "nums=" + Arrays.toString(tc.nums()) + " target=" + tc.target()
        );
    }
}
