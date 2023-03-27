package com.shpp.mentoring.okushin.task3;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.jms.ConnectionFactory;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class SenderTest extends Sender {
    /*ConnectionFactory connectionFactory = spy(new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL));
    @Mock
    ActiveMqManager activeMqManager;

    Sender sender = mock(Sender.class);

    @Test
    void testRun() {
    }

   @Test
    void testSendMessagesToQueue() {
        ActiveMqManager activeMqManager = new ActiveMqManager(connectionFactory);
        activeMqManager.createNewConnectionForProducer("test");
        //sendMessagesToQueue(activeMqManager,1000, LocalDateTime.now(),3);

        verify(sender, times(1)).
                sendMessagesToQueue(activeMqManager, 1000, LocalDateTime.now(), 7);
        Sender sender1 = new Sender();


    }

     */



}