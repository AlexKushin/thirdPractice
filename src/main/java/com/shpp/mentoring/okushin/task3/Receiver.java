package com.shpp.mentoring.okushin.task3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validator;
/**
 * Class Receiver performs receiving ad processing received Pojo messages from ActiveMq broker queue
 */
public class Receiver extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
    private POJOMessage poisonPillPojo;
    private ActiveMqManager activeMqManager;

    private Validator validator;


    public Receiver() {
    }

    public Receiver(ActiveMqManager activeMqManager, Validator validator, POJOMessage poisonPillPojo) {
        this.poisonPillPojo = poisonPillPojo;
        this.activeMqManager = activeMqManager;
        this.validator = validator;

    }

    @Override
    public void run() {
        logger.info("Receiver thread started");
        MessageManager messageManager = new MessageManager(activeMqManager, validator, poisonPillPojo);
        messageManager.writeValidatedMessagesFromAmqToCsvFiles();
        activeMqManager.closeConsumerConnection();
        logger.info("Receiver thread finished");
        interrupt();
    }


}
