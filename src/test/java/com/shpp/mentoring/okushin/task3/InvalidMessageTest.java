package com.shpp.mentoring.okushin.task3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvalidMessageTest extends InvalidMessage {


    InvalidMessage invalidTestMessage = new InvalidMessage("Sasha", 7, "some error");


    @Test
    void testGetName() {

        assertEquals("Sasha", invalidTestMessage.getName());
    }

    @Test
    void testGetCount() {
        assertEquals(7, invalidTestMessage.getCount());
    }

    @Test
    void testGetError() {
        assertEquals("some error", invalidTestMessage.getError());
    }
}