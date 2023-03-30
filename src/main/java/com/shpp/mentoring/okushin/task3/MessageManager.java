package com.shpp.mentoring.okushin.task3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Class MessageManager is developed for processing POJO messages: receiving from queue, validating and writing to CSV
 * file in according to result of validation. All actions are resolved in order: creating 2 csv files
 * (for valid and for invalid pojo messages), receiving POJO message from queue, validating it, writing to
 * corresponding CSV file
 */
public class MessageManager {
    private static final Logger logger = LoggerFactory.getLogger(MessageManager.class);
    private ObjectMapper jsonMapper;
    private Validator validator;
    private ActiveMqManager manager;
    private POJOMessage poisonPillPojo;

    public MessageManager() {
    }

    public MessageManager(ActiveMqManager manager, Validator validator, POJOMessage poisonPillPojo) {
        this.validator = validator;
        this.manager = manager;
        this.poisonPillPojo = poisonPillPojo;
    }

    /**
     * method which
     */
    public void writeValidatedMessagesFromAmqToCsvFiles() {
        ObjectMapper mapper = new CsvMapper().findAndRegisterModules();
        CsvMapper csvMapper = (CsvMapper) mapper;

        CsvSchema schemaForValidPojo =
                csvMapper.schemaFor(POJOMessage.class).withColumnSeparator('\t').withHeader().withoutQuoteChar();
        CsvSchema schemaForInvalidPojo =
                csvMapper.schemaFor(InvalidPojoMessage.class).withColumnSeparator('\t').withHeader().withoutQuoteChar();
        jsonMapper = new JsonMapper().findAndRegisterModules();
        try (SequenceWriter validWriter =
                     csvMapper.writer(schemaForValidPojo).writeValues(new File("newValidFile.csv"));
             SequenceWriter invalidWriter =
                     csvMapper.writer(schemaForInvalidPojo).writeValues(new File("newInvalidFile.csv"))) {
            receiveAndValidate(validWriter, invalidWriter);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Receives pojo messages from ActiveMq broker queue, validates it and calls method to writing to CSV file
     *
     * @param validWriter   writer which writes valid pojo messages to CSV file of valid messages
     * @param invalidWriter writer which writes invalid pojo messages to CSV file of invalid messages
     */
    private void receiveAndValidate(SequenceWriter validWriter, SequenceWriter invalidWriter) {
        AtomicInteger total = new AtomicInteger(0);
        long startTime = System.currentTimeMillis();
        Stream.generate(manager::receiveNewMessageFromQueue)
                .takeWhile(Objects::nonNull)
                .takeWhile(m -> !m.equals(poisonPillPojo))
                .forEach(m -> {
                    total.incrementAndGet();
                    logger.info("Received POJO message name: {} count: {} createdAtDataTime: {} ",
                            m.getName(), m.getCount(), m.getCreatedAtTime());
                    writePojoMessageToAccordanceCsvFile(validator.validate(m), m, validWriter, invalidWriter);
                });
        long endTime = System.currentTimeMillis();
        double elapsedSeconds = (endTime - startTime) / 1000.0;
        double messagesPerSecond = total.get() / elapsedSeconds;
        logger.info("RECEIVING SPEED: {} messages per second, total = {} messages, elapseSeconds = {}",
                messagesPerSecond, total.get(), elapsedSeconds);
    }

    /**
     * checks set of received pojo message violations ad writes this poo to corresponding CSV file
     *
     * @param violations    set of received pojo message violations after validation
     * @param receivedPojo  received pojo message from ActiveMq broker queue
     * @param validWriter   writer which writes valid pojo messages to CSV file of valid messages
     * @param invalidWriter writer which writes invalid pojo messages to CSV file of invalid messages
     */
    private void writePojoMessageToAccordanceCsvFile(Set<ConstraintViolation<POJOMessage>> violations,
                                                     POJOMessage receivedPojo, SequenceWriter validWriter,
                                                     SequenceWriter invalidWriter) {
        try {
            if (violations.isEmpty()) {
                validWriter.write(receivedPojo);
            } else {
                ArrayList<String> errorsList = new ArrayList<>();
                violations.forEach(v -> errorsList.add(v.getMessage()));
                Error error = new Error(errorsList);
                InvalidPojoMessage invalidPojoMessage = new InvalidPojoMessage(receivedPojo.getName(),
                        receivedPojo.getCount(), jsonMapper.writeValueAsString(error));
                invalidWriter.write(invalidPojoMessage);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

