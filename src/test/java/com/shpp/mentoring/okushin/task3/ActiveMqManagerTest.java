package com.shpp.mentoring.okushin.task3;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.jms.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

class ActiveMqManagerTest extends ActiveMqManager {

    /*ConnectionFactory connectionFactoryTest = spy(new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL));

    ConnectionFactory connectionFactory = spy(new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL));

    Connection connection;
    MessageProducer producer;
    MessageProducer pr;
    MessageConsumer consumer;
    Destination destination;
    Session session;
    POJOMessage message;
    ObjectMessage objectMessage;


    @BeforeEach
    public void setUp() throws JMSException {
        connection = connectionFactoryTest.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue("queueTest");
        producer = session.createProducer(destination);
        consumer = session.createConsumer(destination);

        message = new POJOMessage("Sasha",7, LocalDateTime.now());
        objectMessage = session.createObjectMessage(message);


    }


    @AfterEach
    public void shutDown() throws JMSException {
        connection.close();
        session.close();
        producer.close();
        consumer.close();

    }

    @Test
    void testCreateActiveMQSession() {

        ActiveMqManager activeMqManager = new ActiveMqManager(connectionFactory);
        assertNotEquals(null, activeMqManager.createActiveMQSessionForProducer());


    }

    @Test
    void testCreateNewConnectionForProducer() {
        ActiveMqManager activeMqManager = new ActiveMqManager(connectionFactory);
        activeMqManager.createNewConnectionForProducer("test");
        assertNotEquals(null, activeMqManager.getProducer());
        assertFalse(activeMqManager.isProducerConnectionIsClosed());

    }

    @Test
    void testCreateNewConnectionForConsumer() {
        ActiveMqManager activeMqManager = new ActiveMqManager(connectionFactory);
        activeMqManager.createNewConnectionForConsumer("test");
        assertNotEquals(null, activeMqManager.getConsumer());
        assertFalse(activeMqManager.isConsumerConnectionIsClosed());
    }

    @Test
    void testCreateNewConnectionForAll() {
        ActiveMqManager activeMqManager = new ActiveMqManager(connectionFactory);
        activeMqManager.createNewConnectionForAll("test");
        assertNotEquals(null, activeMqManager.getProducer());
        assertNotEquals(null, activeMqManager.getConsumer());
        assertFalse(activeMqManager.isAllConnectionsAreClosed());

    }

    @Test
    void testCloseProducerConnection() {
        ActiveMqManager activeMqManager = new ActiveMqManager(connectionFactory);
        activeMqManager.createNewConnectionForProducer("test");
        assertNotEquals(null, activeMqManager.getProducer());

        activeMqManager.closeProducerConnection();
        assertTrue(activeMqManager.isProducerConnectionIsClosed());

    }

    @Test
    void testCloseConsumerConnection() {
        ActiveMqManager activeMqManager = new ActiveMqManager(connectionFactory);
        activeMqManager.createNewConnectionForConsumer("test");
        assertNotEquals(null, activeMqManager.getConsumer());

        activeMqManager.closeConsumerConnection();

        assertTrue(activeMqManager.isConsumerConnectionIsClosed());
    }

    @Test
    void testCloseConnectionForAll() {
        ActiveMqManager activeMqManager = new ActiveMqManager(connectionFactory);
        activeMqManager.createNewConnectionForAll("test");

        assertNotEquals(null, activeMqManager.getProducer());
        assertNotEquals(null, activeMqManager.getConsumer());
        assertFalse(activeMqManager.isAllConnectionsAreClosed());

        activeMqManager.closeAllConnections();

        assertTrue(activeMqManager.isAllConnectionsAreClosed());
    }


    @Test
    void testPushAndPollMessage() throws JMSException {

        producer.send(objectMessage);
        Message m1 = consumer.receive(1000);
        ObjectMessage objM1 = (ObjectMessage) m1;
        POJOMessage pojoMessage1 = (POJOMessage) objM1.getObject();

        ActiveMqManager activeMqManager = new ActiveMqManager(connectionFactory);
        activeMqManager.createActiveMQSessionForProducer();
        activeMqManager.createNewConnectionForAll("queue");
        activeMqManager.pushNewMessageToQueue(message);
        Message m2 = activeMqManager.pullNewMessageQueue();
        activeMqManager.closeAllConnections();

        ObjectMessage objM2 = (ObjectMessage) m2;
        POJOMessage pojoMessage2 = (POJOMessage) objM2.getObject();

        assertEquals(pojoMessage1.getName(), pojoMessage2.getName());
        assertEquals(pojoMessage1.getCreatedAtTime(), pojoMessage2.getCreatedAtTime());
    }
<<<<<<< HEAD
    
     */


}