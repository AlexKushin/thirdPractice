package com.shpp.mentoring.okushin.task3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class Sender extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);
    private POJOMessage poisonPillPojo;
    private ActiveMqManager manager;
    private String queueName;
    private int amount;
    private long timeLimit;

    public Sender() {
    }

    public Sender(ActiveMqManager manager, String queueName, int amount, long timeLimit, POJOMessage poisonPillPojo) {
        this.manager = manager;
        this.queueName = queueName;
        this.amount = amount;
        this.timeLimit = timeLimit;
        this.poisonPillPojo = poisonPillPojo;
    }

    @Override
    public void run() {
        manager.createNewConnectionForAll(queueName);
        LocalDateTime startGeneratingTime = LocalDateTime.now();
        sendMessagesToQueue(manager, amount, startGeneratingTime, timeLimit);
        interrupt();
    }

    public void sendMessagesToQueue(ActiveMqManager manager, int amount,
                                    LocalDateTime startGeneratingTime, long timeLimit) {
        long startTime = System.currentTimeMillis();

        Stream.generate(() -> new POJOMessage(StringGenerator.randomString(), LocalDateTime.now()))
                .takeWhile(b -> LocalDateTime.now().isBefore(startGeneratingTime.plusSeconds(timeLimit))).limit(amount).
                forEach(manager::pushNewMessageToQueue);

        manager.pushNewMessageToQueue(poisonPillPojo);

        long endTime = System.currentTimeMillis();
        double elapsedSeconds = (endTime - startTime) / 1000.0;
        double messagesPerSecond = POJOMessage.getTotal() / elapsedSeconds;

        logger.info("Sending speed: {} messages per second", messagesPerSecond);
    }
}
