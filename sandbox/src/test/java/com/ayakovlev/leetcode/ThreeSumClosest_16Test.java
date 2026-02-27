package com.ayakovlev.leetcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreeSumClosest_16Test {
    ThreeSumClosest_16 summer;
    @BeforeEach
    void setUp(){
        summer = new ThreeSumClosest_16();
    }

    static Stream<Object[]> testData() {
        return Stream.of(
                new Object[]{new int[]{-1, 2, 1, -4}, 1, 2},
                new Object[]{new int[]{0, 0, 0}, 1, 0},
                new Object[]{new int[]{4, 0, 5, -5, 3, 3, 0, -4, -5}, -2, -2}
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void threeSumClosest(int[] nums, int target, int expected){
        assertEquals(expected, summer.threeSumClosest(nums, target));
    }
}
