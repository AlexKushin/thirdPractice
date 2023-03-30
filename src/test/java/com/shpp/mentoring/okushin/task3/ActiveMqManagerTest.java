package com.shpp.mentoring.okushin.task3;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.jms.JMSException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ActiveMqManagerTest extends ActiveMqManager {

    @Mock
    ActiveMqManager activeMqManager = mock(ActiveMqManager.class);

    @Test
    void createActiveMQSessionForProducerTest() throws JMSException {
        activeMqManager.createActiveMQSessionForProducer();
        Mockito.verify(activeMqManager).createActiveMQSessionForProducer();

    }

    @Test
    void createNewConnectionForProducerTest() throws JMSException {

        activeMqManager.createNewConnectionForProducer("name");
        Mockito.verify(activeMqManager).createNewConnectionForProducer("name");
    }

    @Test
    void createActiveMQSessionForConsumerTest() throws JMSException {
        activeMqManager.createActiveMQSessionForConsumer();
        Mockito.verify(activeMqManager).createActiveMQSessionForConsumer();
    }

    @Test
    void createNewConnectionForConsumerTest() throws JMSException {

        activeMqManager.createNewConnectionForConsumer("name");
        Mockito.verify(activeMqManager).createNewConnectionForConsumer("name");

    }

    @Test
    void closeProducerConnectionTest() throws JMSException {
        activeMqManager.closeProducerConnection();
        Mockito.verify(activeMqManager).closeProducerConnection();
    }

    @Test
    void closeConsumerConnectionTest() throws JMSException {
        activeMqManager.closeConsumerConnection();
        Mockito.verify(activeMqManager).closeConsumerConnection();
    }

    @Test
    void pushNewMessageToQueueTest() throws JMSException {
        POJOMessage message = new POJOMessage("Sasha", 7, LocalDateTime.now());
        activeMqManager.sendNewMessageToQueue(message);
        Mockito.verify(activeMqManager).sendNewMessageToQueue(message);
    }

    @Test
    void pullNewMessageFromQueueTest() {
        Mockito.when(activeMqManager.receiveNewMessageFromQueue())
                .thenReturn(new POJOMessage("Sasha", 7, LocalDateTime.now()));
        assertEquals(POJOMessage.class, activeMqManager.receiveNewMessageFromQueue().getClass());
    }
}