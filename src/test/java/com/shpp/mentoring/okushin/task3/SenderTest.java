package com.shpp.mentoring.okushin.task3;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.jms.JMSException;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class SenderTest extends Sender {

    @Mock
    ActiveMqManager amqManagerMock = mock(ActiveMqManager.class);

    POJOMessage poisonPOJO = new POJOMessage("poison pill", -1, LocalDateTime.now());

    Sender testSender = new Sender(amqManagerMock, 100, 5, poisonPOJO);

    @Mock
    Sender senderMock = mock(Sender.class);


    @Test
    void sendMessagesToQueueTimesTest() {
        LocalDateTime start = LocalDateTime.now();
        testSender.sendMessagesToQueue(amqManagerMock, 100, start, 5);
        Mockito.verify(amqManagerMock, times(100)).sendNewMessageToQueue(any());
    }

    @Test
    void sendMessagesToQueueDurationTest() {
        LocalDateTime start = LocalDateTime.now();
        senderMock.sendMessagesToQueue(amqManagerMock, 100, start, 5);
        Mockito.verify(senderMock, timeout(5)).sendMessagesToQueue(amqManagerMock, 100, start, 5);
    }

    @Test
    void closeProducerConnectionCallVerifying() throws JMSException {
        testSender.run();
        verify(amqManagerMock).closeProducerConnection();

    }


}