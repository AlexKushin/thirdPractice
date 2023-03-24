package com.shpp.mentoring.okushin.task3;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.jms.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class ActiveMqManagerTest extends ActiveMqManager {

    ConnectionFactory connectionFactoryTest = spy(new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL));

    ConnectionFactory connectionFactory = spy(new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL));

    Connection connection;
    MessageProducer producer;
    MessageProducer pr;
    MessageConsumer messageConsumer;
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
        messageConsumer = session.createConsumer(destination);

        message = new POJOMessage("Sasha", LocalDateTime.now());
        objectMessage = session.createObjectMessage(message);
        //System.out.println(producer.getPriority());
      //  System.out.println(pr.getPriority());


    }


    @AfterEach
    public void shutDown() throws JMSException {
        connection.close();
        session.close();
        producer.close();
        messageConsumer.close();

    }

    @Test
    public void testCreateActiveMQSession() throws JMSException {

        ActiveMqManager activeMqManager = new ActiveMqManager(connectionFactory);

        assertNotEquals(null,activeMqManager.createActiveMQSession());


    }

    @Test
    public void testCreateNewConnectionForProducer() throws JMSException {
        ActiveMqManager activeMqManager = new ActiveMqManager(connectionFactory);
        activeMqManager.createNewConnectionForProducer("test");
        assertNotEquals(null,activeMqManager.getProducer());



    }

    @Test
    public void testCreateNewConnectionForConsumer() {
        ActiveMqManager activeMqManager = new ActiveMqManager(connectionFactory);
        activeMqManager.createNewConnectionForConsumer("test");
        assertNotEquals(null,activeMqManager.getMessageConsumer());
    }

   /* @Test
    public void testPushAndPollMessage() throws JMSException {

        producer.send(objectMessage);

        ActiveMqManager activeMqManager = new ActiveMqManager(connectionFactory);
        activeMqManager.createActiveMQSession();
        activeMqManager.createNewConnectionForProducer("queue");
        activeMqManager.pushNewMessageToQueue(message);
        activeMqManager.createNewConnectionForConsumer("queue");

        Message m1 = messageConsumer.receive(1000);
        ObjectMessage objM1 = (ObjectMessage) m1;
        POJOMessage pojoMessage1 = (POJOMessage) objM1.getObject();

        Message m2 = activeMqManager.pullNewMessageQueue();


        ObjectMessage objM2 = (ObjectMessage) m2;
        POJOMessage pojoMessage2 = (POJOMessage) objM2.getObject();

        assertEquals(pojoMessage1.getName(), pojoMessage2.getName());
        assertEquals(pojoMessage1.getCreatedAtTime(), pojoMessage2.getCreatedAtTime());
    }

    */


}