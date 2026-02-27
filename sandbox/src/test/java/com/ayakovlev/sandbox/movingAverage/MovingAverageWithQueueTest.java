package com.ayakovlev.sandbox.movingAverage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MovingAverageWithQueueTest {
    private MovingAverageWithArray maa;
    private MovingAverageWithQueue maq;
    @BeforeEach
    void setUp(){
        maa = new MovingAverageWithArray();
        maq = new MovingAverageWithQueue();
    }

    record TestCase(int[] input, List<Double> expected) {}

    static Stream<TestCase> testCases(){
        return Stream.of(
                new TestCase(
                        new int[]{2, 4, 6, 8, 10, 15, 10},
                        Arrays.asList(2.0, 3.0, 4.0, 6.0, 8.0, 11.0, 11.666666666666666)
                ),
                new TestCase(
                        new int[]{5},
                        Arrays.asList(5.0)
                ),
                new TestCase(
                        new int[]{3, 6},
                        Arrays.asList(3.0, 4.5)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void getMovingAverageList(TestCase tc){
        List<Double> actual_a = maa.getMovingAverageList(tc.input);
        List<Double> actual_q = maq.getMovingAverageList(tc.input);
        assertEquals(tc.expected().size(), actual_a.size());
        assertEquals(tc.expected().size(), actual_q.size());
        for (int i = 0; i < tc.expected().size(); i++){
            assertEquals(tc.expected().get(i), actual_a.get(i), 0.0001, "Mismatch at index " + i);
            assertEquals(tc.expected().get(i), actual_q.get(i), 0.0001, "Mismatch at index " + i);
        }
    }
}