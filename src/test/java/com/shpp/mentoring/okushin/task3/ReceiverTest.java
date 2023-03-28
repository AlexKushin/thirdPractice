package com.shpp.mentoring.okushin.task3;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.validation.Validator;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class ReceiverTest extends Receiver {

    @Mock
    ActiveMqManager amqManagerMock = mock(ActiveMqManager.class);

   @Test
    void testStopReceiveMessagesFromQueueByPoisonPill() {
        Receiver testReceiver = new Receiver();
        POJOMessage poisonPillPOJO = new POJOMessage("poison pill", -1, LocalDateTime.now());
        POJOMessage pojoMessage = new POJOMessage("Sasha",7,LocalDateTime.now());

        Mockito.when(amqManagerMock.pullNewMessageFromQueue())
                .thenReturn(pojoMessage)
                .thenReturn(pojoMessage)
                .thenReturn(pojoMessage)
                .thenReturn(pojoMessage)
                .thenReturn(poisonPillPOJO)
                .thenReturn(pojoMessage)
                .thenReturn(pojoMessage);

        Stream<POJOMessage> stream = testReceiver.receiveMessagesFromQueue(amqManagerMock,poisonPillPOJO);
        assertEquals(4,stream.count(),"Wrong amount of pullNewMessageFromQueue() method calls");

    }



  @Test
    void testStopReceiveMessagesFromQueueByNull() {
        Receiver testReceiver = new Receiver();
        POJOMessage poisonPillPOJO = new POJOMessage("poison pill", -1, LocalDateTime.now());
        POJOMessage pojoMessage = new POJOMessage("Sasha",7,LocalDateTime.now());

        Mockito.when(amqManagerMock.pullNewMessageFromQueue())
                .thenReturn(pojoMessage)
                .thenReturn(pojoMessage)
                .thenReturn(pojoMessage)
                .thenReturn(pojoMessage)
                .thenReturn(null)
                .thenReturn(pojoMessage)
                .thenReturn(pojoMessage);

        Stream<POJOMessage> stream = testReceiver.receiveMessagesFromQueue(amqManagerMock,poisonPillPOJO);
        assertEquals(4,stream.count(),"Wrong amount of pullNewMessageFromQueue() method calls");

    }


}