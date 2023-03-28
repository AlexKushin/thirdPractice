package com.shpp.mentoring.okushin.task3;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.jms.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActiveMqManagerTest extends ActiveMqManager {

    @Mock
    ConnectionFactory connectionFactoryTest = mock(ConnectionFactory.class);
    //  @Mock
    PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
    //PooledConnectionFactory pooledConnectionFactory = mock(PooledConnectionFactory.class);
    @Mock
    ActiveMqManager activeMqManager = mock(ActiveMqManager.class);


    @BeforeEach
    public void setUp() {

        //pooledConnectionFactory.setConnectionFactory(connectionFactoryTest);
        //pooledConnectionFactory.setMaxConnections(10);
    }

    @AfterEach
    void shutDown() {

        // pooledConnectionFactory.clear();
    }

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
        // activeMqManager.createActiveMQSessionForConsumer();
        activeMqManager.createNewConnectionForConsumer("name");
        // Mockito.verify(activeMqManager,times(1)).createActiveMQSessionForConsumer();
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
        Mockito.when(activeMqManager.pullNewMessageFromQueue()).thenReturn(new POJOMessage("Sasha",7,LocalDateTime.now()));
                //thenReturn(new POJOMessage("Sasha",7,LocalDateTime.now()));
    }
}