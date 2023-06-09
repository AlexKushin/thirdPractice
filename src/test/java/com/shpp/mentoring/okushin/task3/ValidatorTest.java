package com.shpp.mentoring.okushin.task3;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();


    @Test
    void validMessagesCountLessThan10Test() {

        POJOMessage message = new POJOMessage("asdfghjk", 1, LocalDateTime.now());

        Set<ConstraintViolation<POJOMessage>> violations = validator.validate(message);
        assertFalse(violations.isEmpty(),"hadn't passed validation by value of count");

    }
    @Test
    void validMessagesCountMoreThan10Test() {

        POJOMessage message = new POJOMessage("asdfghjk", 10, LocalDateTime.now());

        Set<ConstraintViolation<POJOMessage>> violations = validator.validate(message);
        assertTrue(violations.isEmpty(), "hadn't passed validation by value of count");

    }
    @Test
    void validMessagesLengthLessThan7Test() {

        POJOMessage message = new POJOMessage("asd", 10, LocalDateTime.now());

        Set<ConstraintViolation<POJOMessage>> violations = validator.validate(message);
        assertFalse(violations.isEmpty(), "hadn't passed validation by length of name");

    }
    @Test
    void validMessagesLengthMoreThan7Test() {

        POJOMessage message = new POJOMessage("asdfghjk", 10, LocalDateTime.now());

        Set<ConstraintViolation<POJOMessage>> violations = validator.validate(message);
        assertTrue(violations.isEmpty(), "hadn't passed validation by length of name");

    }

    @Test
    void validMessagesContainsATest() {

        POJOMessage message = new POJOMessage("asdfghjk", 10, LocalDateTime.now());

        Set<ConstraintViolation<POJOMessage>> violations = validator.validate(message);
        assertTrue(violations.isEmpty(), "hadn't passed validation by containing \"a\"");

    }
    @Test
    void validMessagesDoNotContainALetterTest() {

        POJOMessage message = new POJOMessage("sddfghj", 10, LocalDateTime.now());

        Set<ConstraintViolation<POJOMessage>> violations = validator.validate(message);
        assertFalse(violations.isEmpty(), "hadn't passed validation by containing \"a\"");

    }

    @Test
    void validMessageTest() {

        POJOMessage message = new POJOMessage("asddfghj", 10, LocalDateTime.now());

        Set<ConstraintViolation<POJOMessage>> violations = validator.validate(message);
        assertTrue(violations.isEmpty(), "valid message hadn't passed validation");

    }
    @Test
    void invalidMessageTest() {

        POJOMessage message = new POJOMessage("ghj", 5, LocalDateTime.now());

        Set<ConstraintViolation<POJOMessage>> violations = validator.validate(message);
        assertEquals(3,violations.size(), "message has less violations then it has to have");

    }





}