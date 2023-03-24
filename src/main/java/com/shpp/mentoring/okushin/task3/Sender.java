package com.shpp.mentoring.okushin.task3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class Sender extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private final POJOMessage poisonPillPojo;
    private final ActiveMqManager manager;
    private final String queueName;
    private final int amount;
    private final long timeLimit;
    boolean interruptedFlag=false;


    public Sender(ActiveMqManager manager, String queueName, int amount, long timeLimit, POJOMessage poisonPillPojo) {

        this.manager = manager;
        this.queueName = queueName;
        this.amount = amount;
        this.timeLimit = timeLimit;
        this.poisonPillPojo = poisonPillPojo;
    }

    @Override
    public void run() {
        //manager.createNewConnectionForProducer(queueName);
        manager.createNewConnectionForAll(queueName);
        LocalDateTime startGeneratingTime = LocalDateTime.now();
        sendMessagesToQueue(manager, amount, startGeneratingTime, timeLimit);
       // manager.closeProducerConnection();
        interrupt();


    }

    public void sendMessagesToQueue(ActiveMqManager manager, int amount, LocalDateTime startGeneratingTime, long timeLimit) {
        long startTime = System.currentTimeMillis();

        Stream.generate(() -> new POJOMessage(StringGenerator.randomString(), LocalDateTime.now()))
                .takeWhile(b -> LocalDateTime.now().isBefore(startGeneratingTime.plusSeconds(timeLimit))).limit(amount).
                forEach(manager::pushNewMessageToQueue);

        manager.pushNewMessageToQueue(poisonPillPojo);
        long endTime = System.currentTimeMillis();
        double elapsedSeconds = (endTime - startTime) / 1000.0;
        double messagesPerSecond = POJOMessage.getTotal() / elapsedSeconds;
        logger.info("Sending speed: " + messagesPerSecond + " messages per second");
    }
}
