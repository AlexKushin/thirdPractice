package com.shpp.mentoring.okushin.task3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class StringGeneratorTest extends StringGenerator {

    @ParameterizedTest
    @CsvSource({
            "alex",
            "brian",
            "charles",
            "12",
            "b",
            "asdfg"
    })
    //run test a few times and compare with came string every time
    void testRandomString(String str) {

        assertNotEquals(str, StringGenerator.randomString());
    }
}