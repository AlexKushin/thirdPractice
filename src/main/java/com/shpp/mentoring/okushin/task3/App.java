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
                messagesAmount = 100000;
            }
        } else {
            messagesAmount = 100000;
        }

        PropertyManager pm = new PropertyManager();
        Properties prop = new Properties();
        pm.readPropertyFile("config.properties", prop);

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

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        POJOMessage poisonPillPojo = new POJOMessage("poison pill", -1, LocalDateTime.now());
        logger.info("poison pill POJO message - name: {}, count: {}, createdAtDataTime: {}",
                poisonPillPojo.getName(),poisonPillPojo.getCount(),poisonPillPojo.getCreatedAtTime());

        Sender sendThread = new Sender(amqManager, queueName, messagesAmount, timeLimit, poisonPillPojo);
        Receiver receiveThread1 = new Receiver( amqManager, queueName, validator,poisonPillPojo);


        sendThread.start();
        if(sendThread.isAlive()) {
            receiveThread1.start();
        }




    }
}

