package com.shpp.mentoring.okushin.task3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validator;
import java.util.Objects;
import java.util.stream.Stream;

public class Receiver extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
    private POJOMessage poisonPillPojo;
    private ActiveMqManager manager;
    private String queueName;
    private Validator validator;


    public Receiver() {

    }

    public Receiver(ActiveMqManager manager, String queueName, Validator validator, POJOMessage poisonPillPojo) {
        this.poisonPillPojo = poisonPillPojo;
        this.manager = manager;
        this.queueName = queueName;
        this.validator = validator;
    }

    @Override
    public void run() {

        manager.createNewConnectionForConsumer(queueName);
        //Stream<POJOMessage> messageStream = receiveMessagesFromQueue(manager, poisonPillPojo);

        MessageManager.writeToCsvValidatedMessages(receiveMessagesFromQueue(manager, poisonPillPojo), validator);
        
        manager.closeConsumerConnection();

        interrupt();
    }

    public Stream<POJOMessage> receiveMessagesFromQueue(ActiveMqManager manager, POJOMessage poisonPillPojo) {
        return Stream.generate(manager::pullNewMessageFromQueue)
                .takeWhile(Objects::nonNull).takeWhile(m -> !m.equals(poisonPillPojo));
    }


}
