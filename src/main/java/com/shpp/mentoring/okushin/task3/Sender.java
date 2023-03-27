package com.shpp.mentoring.okushin.task3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
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
        manager.createNewConnectionForProducer(queueName);
      //  manager.createNewConnectionForConsumer("IN-queue");
        LocalDateTime startGeneratingTime = LocalDateTime.now();
        sendMessagesToQueue(manager, amount, startGeneratingTime, timeLimit);
        manager.closeProducerConnection();
        interrupt();
    }

    public void sendMessagesToQueue(ActiveMqManager manager, int amount,
                                    LocalDateTime startGeneratingTime, long timeLimit) {
        AtomicInteger total = new AtomicInteger(0);
        long startTime = System.currentTimeMillis();
        LocalDateTime finishTime = startGeneratingTime.plusSeconds(timeLimit);
        Stream.generate(() -> new POJOMessage(StringGenerator.randomString(), total.get(), LocalDateTime.now()))
                .takeWhile(b -> LocalDateTime.now().isBefore(finishTime)).limit(amount).
                forEach(m -> {
                    total.incrementAndGet();
                    manager.pushNewMessageToQueue(m);
                });

        manager.pushNewMessageToQueue(poisonPillPojo);

        long endTime = System.currentTimeMillis();
        double elapsedSeconds = (endTime - startTime) / 1000.0;
        double messagesPerSecond = total.get() / elapsedSeconds;

        logger.info("Sending speed: {} messages per second, total = {}, elapseSeconds = {}",
                messagesPerSecond, total.get(), elapsedSeconds);
    }
}