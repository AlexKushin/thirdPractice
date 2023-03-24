package com.shpp.mentoring.okushin.task3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class ActiveMqManager {
    private static final Logger loggerAMqM = LoggerFactory.getLogger(ActiveMqManager.class);
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private MessageProducer producer;

    private MessageConsumer messageConsumer;
    private Destination destination;
    private Session session;

    public ActiveMqManager() {
    }

    public ActiveMqManager(ConnectionFactory activeMQConnectionFactory) {
         connectionFactory = activeMQConnectionFactory;
    }


    public Session createActiveMQSession() {
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public void createNewConnectionForProducer(String queueName) {
        try {
            session = createActiveMQSession();
            destination = session.createQueue(queueName);
            producer = session.createProducer(destination);
        } catch (JMSException e) {
            throw new RuntimeException();
        }

    }

    public void closeProducerConnection() {
        try {
            connection.close();
            session.close();
            producer.close();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }

    public void createNewConnectionForConsumer(String queueName) {
        try {
            session = createActiveMQSession();
            destination = session.createQueue(queueName);
            messageConsumer = session.createConsumer(destination);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConsumerConnection() {
        try {
            connection.close();
            session.close();
            messageConsumer.close();
        } catch (JMSException e) {
            throw new RuntimeException();
        }
    }
    public void createNewConnectionForAll(String queueName) {
        try {
            session = createActiveMQSession();
            destination = session.createQueue(queueName);
            producer = session.createProducer(destination);
            messageConsumer = session.createConsumer(destination);
        } catch (JMSException e) {
            throw new RuntimeException();
        }

    }
    public void closeAllConnections() {
        try {
            connection.close();
            session.close();
            producer.close();
            messageConsumer.close();
        } catch (JMSException e) {
            throw new RuntimeException();
        }
    }

    public void pushNewMessageToQueue(POJOMessage message) {
        ObjectMessage objectMessage;
        try {
            objectMessage = session.createObjectMessage(message);
            producer.send(objectMessage);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }

    public Message pullNewMessageQueue() {
        try {
            return messageConsumer.receive(1000);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }

    public MessageProducer getProducer() {
        return producer;
    }

    public MessageConsumer getMessageConsumer() {
        return messageConsumer;
    }
}
