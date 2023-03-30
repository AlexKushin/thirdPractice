package com.shpp.mentoring.okushin.task3;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.validation.Validator;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class MessageManagerTest extends MessageManager {
    @Mock
    ActiveMqManager amqManagerMock = mock(ActiveMqManager.class);

    @Mock
    Validator validatorMock=mock(Validator.class);
    @Test
    void testStopReceiveMessagesFromQueueByPoisonPill() {

        POJOMessage poisonPillPOJO = new POJOMessage("poison pill", -1, LocalDateTime.now());
        POJOMessage pojoMessage = new POJOMessage("Sasha", 7, LocalDateTime.now());
        MessageManager messageManager = new MessageManager(amqManagerMock,validatorMock,poisonPillPOJO);

        Mockito.when(amqManagerMock.receiveNewMessageFromQueue())
                .thenReturn(pojoMessage)
                .thenReturn(pojoMessage)
                .thenReturn(pojoMessage)
                .thenReturn(pojoMessage)
                .thenReturn(poisonPillPOJO)
                .thenReturn(pojoMessage)
                .thenReturn(pojoMessage);

        messageManager.writeValidatedMessagesFromAmqToCsvFiles();
        verify(amqManagerMock,times(5)).receiveNewMessageFromQueue();

    }
    @Test
    void testWriteValidatedMessagesFromAmqToCsvFiles() {

        POJOMessage poisonPillPOJO = new POJOMessage("poison pill", -1, LocalDateTime.now());
        POJOMessage pojoMessage = new POJOMessage("Sasha",7,LocalDateTime.now());
        MessageManager messageManager = new MessageManager(amqManagerMock,validatorMock,poisonPillPOJO);

        Mockito.when(amqManagerMock.receiveNewMessageFromQueue())
                .thenReturn(pojoMessage)
                .thenReturn(pojoMessage)
                .thenReturn(pojoMessage)
                .thenReturn(pojoMessage)
                .thenReturn(null)
                .thenReturn(pojoMessage)
                .thenReturn(pojoMessage);

        messageManager.writeValidatedMessagesFromAmqToCsvFiles();
        verify(amqManagerMock,times(5)).receiveNewMessageFromQueue();

    }
}