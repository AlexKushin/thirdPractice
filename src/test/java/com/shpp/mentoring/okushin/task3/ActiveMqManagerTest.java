package com.shpp.mentoring.okushin.task3;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ActiveMqManagerTest extends ActiveMqManager {

    @Mock
    ActiveMqManager activeMqManager = mock(ActiveMqManager.class);


    @Test
    void createActiveMQSessionForProducerTest() {

        activeMqManager.createActiveMQSessionForProducer();
        Mockito.verify(activeMqManager).createActiveMQSessionForProducer();

    }

    @Test
    void createNewConnectionForProducerTest() {

        activeMqManager.createNewConnectionForProducer("name");
        Mockito.verify(activeMqManager).createNewConnectionForProducer("name");
    }

    @Test
    void createActiveMQSessionForConsumerTest() {
        activeMqManager.createActiveMQSessionForConsumer();
        Mockito.verify(activeMqManager).createActiveMQSessionForConsumer();
    }

    @Test
    void createNewConnectionForConsumerTest() {

        activeMqManager.createNewConnectionForConsumer("name");
        Mockito.verify(activeMqManager).createNewConnectionForConsumer("name");

    }

    @Test
    void closeProducerConnectionTest() {
        activeMqManager.closeProducerConnection();
        Mockito.verify(activeMqManager).closeProducerConnection();
    }

    @Test
    void closeConsumerConnectionTest() {
        activeMqManager.closeConsumerConnection();
        Mockito.verify(activeMqManager).closeConsumerConnection();
    }

    @Test
    void pushNewMessageToQueueTest() {
        POJOMessage message = new POJOMessage("Sasha", 7, LocalDateTime.now());
        activeMqManager.pushNewMessageToQueue(message);
        Mockito.verify(activeMqManager).pushNewMessageToQueue(message);
    }

    @Test
    void pullNewMessageFromQueueTest() {
        Mockito.when(activeMqManager.pullNewMessageFromQueue())
                .thenReturn(new POJOMessage("Sasha", 7, LocalDateTime.now()));
        assertEquals(POJOMessage.class, activeMqManager.pullNewMessageFromQueue().getClass());
    }
}