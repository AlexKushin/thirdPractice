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

    @BeforeEach
    void setCountToZero() {
        POJOMessage.setTotalToZero();
    }

    @Test
    void validMessagesCountTest() {

        POJOMessage message;

        List<POJOMessage> messageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            message = new POJOMessage("asdfghjk", LocalDateTime.now());
            System.out.println(message.getCount() + "count");
            System.out.println(message.getName() + "name");
            messageList.add(message);
        }
        System.out.println(messageList.size() + "size");
        for (int i = 0; i < 9; i++) {
            System.out.println(messageList.get(i).getCount() + "count");
            System.out.println(messageList.get(i).getName() + "name");
            Set<ConstraintViolation<POJOMessage>> violations = validator.validate(messageList.get(i));
            assertFalse(violations.isEmpty());

        }
        Set<ConstraintViolation<POJOMessage>> violations = validator.validate(messageList.get(messageList.size() - 1));
        assertTrue(violations.isEmpty());

    }

    @Test
    void validMessagesLengthTest() {
        POJOMessage message;
        List<POJOMessage> messageList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i > 14) {
                message = new POJOMessage("asd", LocalDateTime.now());
            } else {
                message = new POJOMessage("asdfghjk", LocalDateTime.now());
            }
            messageList.add(message);
        }
        for (int i = 9; i < 15; i++) {
            Set<ConstraintViolation<POJOMessage>> violations = validator.validate(messageList.get(i));
            assertTrue(violations.isEmpty());

        }
        for (int i = 15; i < messageList.size(); i++) {
            Set<ConstraintViolation<POJOMessage>> violations = validator.validate(messageList.get(i));
            assertFalse(violations.isEmpty());

        }
    }

    @Test
    void validMessagesContainsATest() {
        POJOMessage message;
        List<POJOMessage> messageList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i > 14) {
                message = new POJOMessage("sdfghjkl", LocalDateTime.now());
            } else {
                message = new POJOMessage("asdfghjk", LocalDateTime.now());
            }
            messageList.add(message);
        }
        for (int i = 9; i < 15; i++) {
            Set<ConstraintViolation<POJOMessage>> violations = validator.validate(messageList.get(i));
            assertTrue(violations.isEmpty());
        }
        for (int i = 15; i < messageList.size(); i++) {
            Set<ConstraintViolation<POJOMessage>> violations = validator.validate(messageList.get(i));
            assertFalse(violations.isEmpty());
        }
    }

    @Test
    void testWriteToCsvValidatedMessages() {
    }

}