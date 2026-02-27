package com.ayakovlev.sandbox;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class BracketValidatorTest {

    private BracketValidator validator;
    @BeforeEach
    void setUp(){
        validator = new BracketValidator();
    }

    @ParameterizedTest
    @NullSource
    void nullReturnsFalse(String input){
        System.out.println("input: " + input);
        assertFalse(validator.hasValidBrackets(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void blankReturnsTrue(String input){
        assertTrue(validator.hasValidBrackets(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"()", "[]", "{}", "([])", "{[()]}", "a+b*(c-d)", "(())", "([{}])", "([{}(){}])"})
    void validBracketsReturnTrue(String input){
        assertTrue(validator.hasValidBrackets(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"(", "{", "([)]", "{[}", "(()", "){", ")(()", "([{]})"})
    void invalidBracketsReturnFalse(String input){
        assertFalse(validator.hasValidBrackets(input));
    }

}
