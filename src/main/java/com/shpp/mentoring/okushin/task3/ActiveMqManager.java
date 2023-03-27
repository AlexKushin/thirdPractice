package com.shpp.mentoring.okushin.task3;

import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class ActiveMqManager {
    private static final Logger loggerAMqM = LoggerFactory.getLogger(ActiveMqManager.class);
    private ConnectionFactory connectionFactory;
    private final PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
    private Connection producerConnection;
    private Connection consumerConnection;
    private MessageProducer producer;

    private MessageConsumer consumer;
    private Destination producerDestination;
    private Destination consumerDestination;
    private Session producerSession;
    private Session consumerSession;


    private boolean producerConnectionIsClosed = true;
    private boolean consumerConnectionIsClosed = true;
    private boolean allConnectionsAreClosed = true;

    public ActiveMqManager() {
    }

    public ActiveMqManager(ConnectionFactory activeMQConnectionFactory) {
        connectionFactory = activeMQConnectionFactory;

        pooledConnectionFactory.setConnectionFactory(activeMQConnectionFactory);
        pooledConnectionFactory.setMaxConnections(10);
    }


    public Session createActiveMQSessionForProducer() {
        try {
            producerConnection = pooledConnectionFactory.createConnection();
            producerConnection.start();

            return producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            loggerAMqM.error("Can't create session {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Session createActiveMQSessionForConsumer() {
        try {
            consumerConnection = connectionFactory.createConnection();
            consumerConnection.start();
            return consumerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            loggerAMqM.error("Can't create session {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void createNewConnectionForProducer(String queueName) {
        try {
            producerSession = createActiveMQSessionForProducer();
            producerDestination = producerSession.createQueue(queueName);
            producer = producerSession.createProducer(producerDestination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producerConnectionIsClosed = false;
        } catch (JMSException e) {
            throw new RuntimeException();
        }

    }

    public void closeProducerConnection() {
        try {
            producer.close();
            producerSession.close();
            producerConnection.close();
            pooledConnectionFactory.clear();
            producerConnectionIsClosed = true;
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }

    public void createNewConnectionForConsumer(String queueName) {
        try {
            consumerSession= createActiveMQSessionForConsumer();
            consumerDestination = consumerSession.createQueue(queueName);
            consumer = consumerSession.createConsumer(consumerDestination);
            consumerConnectionIsClosed = false;
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConsumerConnection() {
        try {
            consumer.close();
            consumerSession.close();
            consumerConnection.close();
            consumerConnectionIsClosed = true;
        } catch (JMSException e) {
            throw new RuntimeException();
        }
    }

    public void createNewConnectionForAll(String queueName) {
        createNewConnectionForProducer(queueName);

        createNewConnectionForConsumer(queueName);


        allConnectionsAreClosed = false;

    }

    public void closeAllConnections() {
        closeProducerConnection();
        closeConsumerConnection();
        allConnectionsAreClosed = true;
    }

    public void pushNewMessageToQueue(POJOMessage message) {
        ObjectMessage objectMessage;
        try {
            objectMessage = producerSession.createObjectMessage(message);
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
