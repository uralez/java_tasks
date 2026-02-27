package com.ayakovlev.sandbox;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BinarySearchTest {
    private BinarySearch obj;
    @BeforeEach
    public void setUp(){
        obj = new BinarySearch();
    }
    record TestCase(int[] nums, int a, Number expected){}
    private static Stream<TestCase> testCases(){
        return Stream.of(
                new TestCase(new int[]{1, 2, 3, 4, 5}, 1, 0),
                new TestCase(new int[]{1, 2, 3, 4, 5}, 0, -1),
                new TestCase(new int[]{1, 2, 3, 4, 5}, 3, 2),
                new TestCase(new int[]{1, 2, 3, 4, 5}, 10, 6),
                new TestCase(new int[]{1, 1, 1, 2, 3, 4, 7}, 1, 0),
                new TestCase(new int[]{1, 2, 3, 4, 4, 4, 4, 7}, 4, 3),
                new TestCase(new int[]{}, 1, null),        // пустой массив
                new TestCase(new int[]{5}, 5, 0),         // один элемент, найден
                new TestCase(new int[]{5}, 3, -1)         // один элемент, не найден
        );
    }
    private static Stream<TestCase> testCaseEx(){
        return Stream.of(
                new TestCase(new int[]{1, 2, 30, 4, 5}, 10, null)
        );
    }
    @ParameterizedTest
    @MethodSource("testCases")
    void testSearch2(TestCase tc) throws Exception {
        assertEquals(tc.expected(), obj.search(tc.nums(), tc.a()), "nums=" + Arrays.toString(tc.nums()) + " A=" + tc.a());
    }

    @ParameterizedTest
    @MethodSource("testCaseEx")
    void searchThrowsExceptionWhenArrayNotSorted(TestCase tc){
        assertThrows(Exception.class, () -> obj.search(tc.nums, tc.a()));
    }
}
