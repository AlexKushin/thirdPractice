package com.shpp.mentoring.okushin.task3;

import com.shpp.mentoring.okushin.task2.PropertyManager;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.ConnectionFactory;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.stream.Stream;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        int messagesAmount;
        if (args.length != 0) {
            try {
                messagesAmount = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                messagesAmount = 1000;
            }
        } else {
            messagesAmount = 1000;
        }


        PropertyManager pm = new PropertyManager();
        Properties prop = new Properties();
        pm.readPropertyFile("config.properties", prop);
        //??
        long timeLimit = Long.parseLong(PropertyManager.getStringPropertiesValue("poisonPillSec", prop));
        String queueName = PropertyManager.getStringPropertiesValue("queueName", prop);

        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
        ActiveMqManager amqManager = new ActiveMqManager(connectionFactory);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        POJOMessage poisonPillPojo = new POJOMessage("poison pill", LocalDateTime.now());

        /*Sender sender = new Sender(amqManager,queueName,messagesAmount,timeLimit,poisonPillPojo);
        Receiver receiver = new Receiver(poisonPillPojo, amqManager, queueName, validator);
        sender.run();

        receiver.run();

         */
        Sender sendThread = new Sender(amqManager,queueName,messagesAmount,timeLimit,poisonPillPojo);
        Receiver receiveThread = new Receiver(poisonPillPojo, amqManager, queueName, validator);
        sendThread.start();

        receiveThread.start();
        System.out.println(sendThread.isInterrupted()+" "+receiveThread.isInterrupted());





        /*amqManager.createNewConnectionForProducer(queueName);
        logger.debug("connection for PRODUCER is created");

        MessageManager messageManager = new MessageManager();
        LocalDateTime startGeneratingTime = LocalDateTime.now();
        messageManager.sendMessagesToQueue(amqManager, messagesAmount, startGeneratingTime, timeLimit, poisonPillPojo);

        amqManager.closeProducerConnection();
        logger.debug("connection for PRODUCER is closed");

        amqManager.createNewConnectionForConsumer(queueName);
        logger.debug("connection for CONSUMER is created");


        Stream<POJOMessage> messageStream =
                messageManager.receiveMessagesFromQueue(amqManager, poisonPillPojo);

        messageManager.writeToCsvValidatedMessages(messageStream, validator);
        amqManager.closeConsumerConnection();
        logger.debug("connection for CONSUMER is closed");

         */

    }
}

