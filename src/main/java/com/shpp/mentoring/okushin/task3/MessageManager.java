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
import java.util.Set;
import java.util.stream.Stream;

public class MessageManager {
    private static final Logger logger = LoggerFactory.getLogger(MessageManager.class);


    public static void writeToCsvValidatedMessages(Stream<POJOMessage> messageStream, Validator validator) {
        ObjectMapper mapper = new CsvMapper().findAndRegisterModules();
        CsvMapper csvMapper = (CsvMapper) mapper;
        CsvSchema validSchema = csvMapper.schemaFor(POJOMessage.class).withColumnSeparator('\t').withHeader().withoutQuoteChar();
        CsvSchema invalidSchema = csvMapper.schemaFor(InvalidMessage.class).withColumnSeparator('\t').withHeader().withoutQuoteChar();
        ObjectMapper jsonMapper = new JsonMapper().findAndRegisterModules();
        try (SequenceWriter validWriter = csvMapper.writer(validSchema).writeValues(new File("newValidFile.csv"));
             SequenceWriter invalidWriter = csvMapper.writer(invalidSchema).writeValues(new File("newInvalidFile.csv"))) {
            // try (SequenceWriter validWriter = MessagesToCsvWriter.writeMessageToCsv(POJOMessage.class, "newValidFile.csv");
            //    SequenceWriter invalidWriter = MessagesToCsvWriter.writeMessageToCsv(InvalidMessage.class, "newInvalidFile.csv")) {
            messageStream.forEach(m -> {
                //  logger.info(m.getName());
                Set<ConstraintViolation<POJOMessage>> violations = validator.validate(m);
                ArrayList<String> errorsList = new ArrayList<>();
                violations.forEach(v -> errorsList.add(v.getMessage()));
                Error error = new Error(errorsList);
                try {
                    if (violations.isEmpty()) {
                        validWriter.write(m);
                    } else {
                        InvalidMessage invalidMessage = new InvalidMessage(m.getName(), m.getCount(),
                                jsonMapper.writeValueAsString(error));
                        invalidWriter.write(invalidMessage);
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            });
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

