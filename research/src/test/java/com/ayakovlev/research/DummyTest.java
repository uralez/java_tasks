package com.ayakovlev.research;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DummyTest {

    @Test
    void simpleTest() {
        System.out.println("call simpleTest()");
        assertEquals(2, 1 + 1);
    }
    @Test
    void secondTest() {
        System.out.println("call secondTest()");
        assertEquals(5, 1 + 4);
    }
    @Test
    void testMyMethod(){
        int result = Qqq.myMethod();
        assertEquals(5, result);
    }
}