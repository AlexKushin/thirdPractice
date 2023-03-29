package com.shpp.mentoring.okushin.task3;

import com.shpp.mentoring.okushin.exceptions.CreateConnectionException;
import com.shpp.mentoring.okushin.exceptions.ReceiveMessageException;
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
    private Session producerSession;
    private Session consumerSession;


    public ActiveMqManager() {
    }

    public ActiveMqManager(ConnectionFactory activeMQConnectionFactory) {
        connectionFactory = activeMQConnectionFactory;
        pooledConnectionFactory.setConnectionFactory(activeMQConnectionFactory);
        pooledConnectionFactory.setMaxConnections(10);
        loggerAMqM.info("ActiveMqManager object is created");
    }


    public Session createActiveMQSessionForProducer() {
        try {
            producerConnection = pooledConnectionFactory.createConnection();
            producerConnection.start();
            loggerAMqM.info("Producer connection started");
            return producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            loggerAMqM.error("Can't create producer session {}", e.getMessage(), e);
            throw new CreateConnectionException("Can't create session for producer");
        }
    }

    public Session createActiveMQSessionForConsumer() {
        try {
            consumerConnection = connectionFactory.createConnection();
            consumerConnection.start();
            loggerAMqM.info("Consumer connection started");
            return consumerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            loggerAMqM.error("Can't create consumer session {}", e.getMessage(), e);
            throw new CreateConnectionException("Can't create session for consumer");
        }
    }

    public void createNewConnectionForProducer(String queueName) {
        try {
            producerSession = createActiveMQSessionForProducer();
            Destination producerDestination = producerSession.createQueue(queueName);
            producer = producerSession.createProducer(producerDestination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            loggerAMqM.info("Producer connection is created");

        } catch (JMSException e) {
            loggerAMqM.error("Can't create producer connection {}", e.getMessage(), e);
            throw new CreateConnectionException("Can't create producer connection");
        }

    }

    public void closeProducerConnection() {
        try {
            producer.close();
            producerSession.close();
            producerConnection.close();
            pooledConnectionFactory.clear();
            loggerAMqM.info("Producer connection is closed");

        } catch (JMSException e) {
            loggerAMqM.error("Can't close producer connection {}", e.getMessage(), e);
            throw new CreateConnectionException("Can't close producer connection");
        }

    }

    public void createNewConnectionForConsumer(String queueName) {
        try {
            consumerSession = createActiveMQSessionForConsumer();
            Destination consumerDestination = consumerSession.createQueue(queueName);
            consumer = consumerSession.createConsumer(consumerDestination);
            loggerAMqM.info("Consumer connection is created");

        } catch (JMSException e) {
            loggerAMqM.error("Can't create producer connection {}", e.getMessage(), e);
            throw new CreateConnectionException("Can't create consumer connection");
        }
    }

    public void closeConsumerConnection() {
        try {
            consumer.close();
            consumerSession.close();
            consumerConnection.close();
            loggerAMqM.info("Consumer connection is closed");
        } catch (JMSException e) {
            loggerAMqM.error("Can't close producer connection {}", e.getMessage(), e);
            throw new CreateConnectionException("Can't close consumer connection");
        }
    }


    public void pushNewMessageToQueue(POJOMessage message) {
        ObjectMessage objectMessage;
        try {
            objectMessage = producerSession.createObjectMessage(message);
            producer.send(objectMessage);
        } catch (JMSException e) {
            loggerAMqM.error("Can't send message from queue  {}", e.getMessage(), e);
            throw new ReceiveMessageException("Can't send message from queue");
        }


    }


    public POJOMessage pullNewMessageFromQueue() {
        try {
            Message message = consumer.receive(1000);
            if (message == null) {
                return null;
            }
            ObjectMessage objectMessage = (ObjectMessage) message;
            return (POJOMessage) objectMessage.getObject();
        } catch (JMSException e) {
            loggerAMqM.error("Can't receive message from queue  {}", e.getMessage(), e);
            throw new ReceiveMessageException("Can't receive message from queue");
        }
    }


}



