package com.ayakovlev.sandbox;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExtractNumberFromStringTest {
    @ParameterizedTest
    @CsvSource({
            "Стоимость товара: 1234 руб., 1234",
            "123asd5678, 1235678",
            "abc1й2$3.45def, 123.45",
            "abc1й2$3.4_5def, 123.45",
    })
    void testExtractIntegerFromString(String in, double expected){
        double out = ExtractNumberFromString.extractDoubleFromString(in);
        System.out.println("---");
        System.out.println("in      :" + in);
        System.out.println("expected:" + expected);
        System.out.println("out     :" + out);
        assertEquals(expected, out);
    }
}
