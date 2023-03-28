package com.shpp.mentoring.okushin.task3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageManagerTest extends MessageManager {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();


    @Test
    void validMessagesCountLessThan10Test() {

        POJOMessage message = new POJOMessage("asdfghjk", 1, LocalDateTime.now());

        Set<ConstraintViolation<POJOMessage>> violations = validator.validate(message);
        assertFalse(violations.isEmpty());

    }
    @Test
    void validMessagesCountMoreThan10Test() {

        POJOMessage message = new POJOMessage("asdfghjk", 10, LocalDateTime.now());

        Set<ConstraintViolation<POJOMessage>> violations = validator.validate(message);
        assertTrue(violations.isEmpty());

    }
    @Test
    void validMessagesLengthLessThan7Test() {

        POJOMessage message = new POJOMessage("asd", 10, LocalDateTime.now());

        Set<ConstraintViolation<POJOMessage>> violations = validator.validate(message);
        assertFalse(violations.isEmpty());

    }
    @Test
    void validMessagesLengthMoreThan7Test() {

        POJOMessage message = new POJOMessage("asdfghjk", 10, LocalDateTime.now());

        Set<ConstraintViolation<POJOMessage>> violations = validator.validate(message);
        assertTrue(violations.isEmpty());

    }

    @Test
    void validMessagesContainsATest() {

        POJOMessage message = new POJOMessage("asdfghjk", 10, LocalDateTime.now());

        Set<ConstraintViolation<POJOMessage>> violations = validator.validate(message);
        assertTrue(violations.isEmpty());

    }
    @Test
    void validMessagesDoesntContainATest() {

        POJOMessage message = new POJOMessage("sddfghj", 10, LocalDateTime.now());

        Set<ConstraintViolation<POJOMessage>> violations = validator.validate(message);
        assertFalse(violations.isEmpty());

    }

    @Test
    void testWriteToCsvValidatedMessages() {
    }



}