package com.shpp.mentoring.okushin.task3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class ActiveMqManager {
    private static final Logger loggerAMqM = LoggerFactory.getLogger(ActiveMqManager.class);
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private MessageProducer producer;

    private MessageConsumer consumer;
    private Destination destination;
    private Session session;

    private boolean producerConnectionIsClosed = true;
    private boolean consumerConnectionIsClosed = true;
    private boolean allConnectionsAreClosed = true;

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
            loggerAMqM.error("Can't create session {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void createNewConnectionForProducer(String queueName) {
        try {
            session = createActiveMQSession();
            destination = session.createQueue(queueName);
            producer = session.createProducer(destination);
            producerConnectionIsClosed = false;
        } catch (JMSException e) {
            throw new RuntimeException();
        }

    }

    public void closeProducerConnection() {
        try {
            connection.close();
            session.close();
            producer.close();
            producerConnectionIsClosed = true;
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }

    public void createNewConnectionForConsumer(String queueName) {
        try {
            session = createActiveMQSession();
            destination = session.createQueue(queueName);
            consumer = session.createConsumer(destination);
            consumerConnectionIsClosed = false;
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConsumerConnection() {
        try {
            connection.close();
            session.close();
            consumer.close();
            consumerConnectionIsClosed = true;
        } catch (JMSException e) {
            throw new RuntimeException();
        }
    }

    public void createNewConnectionForAll(String queueName) {
        try {
            session = createActiveMQSession();
            destination = session.createQueue(queueName);
            producer = session.createProducer(destination);
            consumer = session.createConsumer(destination);
            allConnectionsAreClosed = false;
        } catch (JMSException e) {
            throw new RuntimeException();
        }

    }

    public void closeAllConnections() {
        try {
            connection.close();
            session.close();
            producer.close();
            consumer.close();
            allConnectionsAreClosed = true;
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
            return consumer.receive(1000);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }

    public MessageProducer getProducer() {
        return producer;
    }

    public MessageConsumer getConsumer() {
        return consumer;
    }

    public boolean isProducerConnectionIsClosed() {
        return producerConnectionIsClosed;
    }

    public boolean isConsumerConnectionIsClosed() {
        return consumerConnectionIsClosed;
    }

    public boolean isAllConnectionsAreClosed() {
        return allConnectionsAreClosed;
    }
}
