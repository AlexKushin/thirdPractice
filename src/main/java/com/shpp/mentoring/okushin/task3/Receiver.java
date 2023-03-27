package com.shpp.mentoring.okushin.task3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.validation.Validator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Receiver extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
    private final POJOMessage poisonPillPojo;
    private final ActiveMqManager manager;

    private final Validator validator;

    public Receiver(POJOMessage poisonPillPojo, ActiveMqManager manager, Validator validator) {
        this.poisonPillPojo = poisonPillPojo;
        this.manager = manager;
        this.validator = validator;
    }

    @Override
    public void run() {
        manager.createNewConnectionForConsumer("IN-queue");

        Stream<POJOMessage> messageStream = receiveMessagesFromQueue(manager, poisonPillPojo);

        MessageManager.writeToCsvValidatedMessages(messageStream, validator);

        manager.closeConsumerConnection();

        interrupt();

    }

    public Stream<POJOMessage> receiveMessagesFromQueue(ActiveMqManager manager, POJOMessage poisonPillPojo) {
        return Stream.generate(() -> {
                    Message message = manager.pullNewMessageQueue();
                    if (message == null) {
                        return null;
                    }
                    ObjectMessage objectMessage = (ObjectMessage) message;
                    try {
                        return (POJOMessage) objectMessage.getObject();
                    } catch (JMSException e) {
                        throw new RuntimeException(e);
                    }
                })
                .takeWhile(Objects::nonNull).takeWhile(m -> !m.equals(poisonPillPojo));

    }
}
