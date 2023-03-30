package com.shpp.mentoring.okushin.task3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Class Sender performs generating ad sending Pojo messages to ActiveMq broker queue
 */
public class Sender extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);
    private POJOMessage poisonPillPojo;
    private ActiveMqManager manager;

    private int amount;
    private long timeLimit;


    public Sender() {
    }

    public Sender(ActiveMqManager manager, int amount, long timeLimit, POJOMessage poisonPillPojo) {
        this.manager = manager;
        this.amount = amount;
        this.timeLimit = timeLimit;
        this.poisonPillPojo = poisonPillPojo;
    }

    @Override
    public void run() {
        logger.info("Sender thread started");
        LocalDateTime startGeneratingTime = LocalDateTime.now();
        sendMessagesToQueue(manager, amount, startGeneratingTime, timeLimit);
        manager.sendNewMessageToQueue(poisonPillPojo);
        manager.closeProducerConnection();
        logger.info("Sender thread finished");
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

                    manager.sendNewMessageToQueue(m);

                    logger.info("Sent POJO message name: {} count: {} createdAtDataTime: {}",
                            m.getName(), m.getCount(), m.getCreatedAtTime());
                });

        long endTime = System.currentTimeMillis();
        double elapsedSeconds = (endTime - startTime) / 1000.0;
        double messagesPerSecond = total.get() / elapsedSeconds;

        logger.info("SENDING SPEED: {} messages per second, total = {} messages, elapseSeconds = {}",
                messagesPerSecond, total.get(), elapsedSeconds);
    }
}
