package com.shpp.mentoring.okushin.task3;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.shpp.mentoring.okushin.task3.InvalidMessage;

import java.io.File;
import java.io.IOException;

public class MessagesToCsvWriter {

    public static SequenceWriter writeMessageToCsv(Class className, String csvFileName) {
        ObjectMapper mapper = new CsvMapper().findAndRegisterModules();
        CsvMapper csvMapper = (CsvMapper) mapper;
        //csvMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN,true);
        CsvSchema schema = csvMapper.schemaFor(className).withColumnSeparator('\t').withHeader().withoutQuoteChar();
        try (SequenceWriter sequenceWriter = csvMapper.writer(schema).writeValues(new File(csvFileName))) {
            return sequenceWriter;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
