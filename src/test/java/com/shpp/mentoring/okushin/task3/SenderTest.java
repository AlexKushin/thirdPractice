package com.shpp.mentoring.okushin.task3;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.jms.ConnectionFactory;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class SenderTest extends Sender {

    @Mock
    ActiveMqManager mockAmqManager = mock(ActiveMqManager.class);

    POJOMessage poisonPOJO = new POJOMessage("poison pill",-1,LocalDateTime.now());

    Sender testSender = new Sender(mockAmqManager,"name",100,5,poisonPOJO);

    @Mock
    Sender senderMock = mock(Sender.class);


    @Test
    void sendMessagesToQueueTimesTest() {
        LocalDateTime start = LocalDateTime.now();
        testSender.sendMessagesToQueue(mockAmqManager,100,start,5);
        Mockito.verify(mockAmqManager,times(100)).pushNewMessageToQueue(any());
    }
    @Test
    void sendMessagesToQueueDurationTest() {
        LocalDateTime start = LocalDateTime.now();
        senderMock.sendMessagesToQueue(mockAmqManager,100,start,5);
        Mockito.verify(senderMock,timeout(5)).sendMessagesToQueue(mockAmqManager,100,start,5);
    }


}