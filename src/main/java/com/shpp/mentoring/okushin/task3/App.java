package com.shpp.mentoring.okushin.task3;

import com.shpp.mentoring.okushin.task2.PropertyManager;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Properties;

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
        logger.info("start reading all necessary data for access to ActiveMq broker");
        long timeLimit = Long.parseLong(PropertyManager.getStringPropertiesValue("poisonPillSec", prop));
        String queueName = PropertyManager.getStringPropertiesValue("queueName", prop);
        String url = PropertyManager.getStringPropertiesValue("brokerURL", prop);
        String userName = PropertyManager.getStringPropertiesValue("userName", prop);
        String password = PropertyManager.getStringPropertiesValue("password", prop);
        logger.info("all necessary data was successfully read from property file");


        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        connectionFactory.setUserName(userName);
        connectionFactory.setPassword(password);
        ActiveMqManager amqManager = new ActiveMqManager(connectionFactory);

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            POJOMessage poisonPillPojo = new POJOMessage("poison pill", -1, LocalDateTime.now());
            logger.info("poison pill POJO message - name: {}, count: {}, createdAtDataTime: {}",
                    poisonPillPojo.getName(), poisonPillPojo.getCount(), poisonPillPojo.getCreatedAtTime());
            amqManager.createNewConnectionForProducer(queueName);
            amqManager.createNewConnectionForConsumer(queueName);

            Sender sendThread = new Sender(amqManager, messagesAmount, timeLimit, poisonPillPojo);
            Receiver receiveThread = new Receiver(amqManager, validator, poisonPillPojo);

            sendThread.start();
            receiveThread.start();
        }


    }
}

