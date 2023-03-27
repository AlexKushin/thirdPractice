package com.shpp.mentoring.okushin.task2;

import com.shpp.mentoring.okushin.exceptions.NotExistPropertyKeyException;
import com.shpp.mentoring.okushin.exceptions.NotInputtedFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * class PropertyManager was created for handy reading property files, getting values from property file by keys
 * it has methods
 */
public class PropertyManager {
    private static final Logger logger = LoggerFactory.getLogger(PropertyManager.class);


    /**
     * Reads data from file.properties by FILE_NAME in UTF-8 format and writes this data to instance of Properties
     *
     * @param fileName - name of property file to be read
     * @param prop     - reference to Property object for storing read property file
     */
    public void readPropertyFile(String fileName, Properties prop) {

        try (FileInputStream inputStream = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            prop.load(isr);
            logger.debug("Property was successfully read");
        } catch (IOException e) {
            logger.error("Can't read property file{}", e.getMessage(), e);

        }
    }

    /**
     * returns string value which is tied with key from property file
     *
     * @param propKey - key for getting tied value from the property file
     * @param prop    - object Properties in which property file was read
     * @return string value which is tied with key from property file
     */
    public static String getStringPropertiesValue(String propKey, Properties prop) {

        if (prop.getProperty(propKey) == null) {
            throw new NotExistPropertyKeyException("Value of PROPERTY_KEY: " + propKey + " doesn't exist");
        }
        return prop.getProperty(propKey);
    }

    /**
     * returns numeric value in accordance with assigned type, which is stored in property file and tied with key
     * by default it returns numeric value of Integer type
     * if format is not inputted, it will throw unchecked NotInputtedFormatException
     *
     * @param prop - reference to Property object which stores property file
     * @param key  - key for getting tied value from the property file
     * @param type - type of numeric value which will be returned
     * @return numeric value in accordance with assigned type, which is stored in property file and tied with key
     */
    public Number getNumberPropertiesValue(Properties prop, String key, String type) {

        if (type == null) {
            throw new NotInputtedFormatException("Type isn't inputted");
        }
        if (type.equalsIgnoreCase("byte")) {
            logger.info("Byte value of {} will be returned", key);
            return Byte.parseByte(PropertyManager.getStringPropertiesValue(key, prop));
        }
        if (type.equalsIgnoreCase("long")) {
            logger.info("Long value of {} will be returned", key);
            return Long.parseLong(PropertyManager.getStringPropertiesValue(key, prop));
        }
        if (type.equalsIgnoreCase("short")) {
            logger.info("Short value of {} will be returned", key);
            return Short.parseShort(PropertyManager.getStringPropertiesValue(key, prop));
        }
        if (type.equalsIgnoreCase("float")) {
            logger.info("Float value of {} will be returned", key);
            return Float.parseFloat(PropertyManager.getStringPropertiesValue(key, prop));
        }
        if (type.equalsIgnoreCase("double")) {
            logger.info("Double value of {} will be returned", key);
            return Double.parseDouble(PropertyManager.getStringPropertiesValue(key, prop));
        }
        if (type.equalsIgnoreCase("BigInteger")) {
            logger.info("BigInteger value of {} will be returned", key);
            return new BigInteger(PropertyManager.getStringPropertiesValue(key, prop));
        }
        if (type.equalsIgnoreCase("BigDecimal")) {
            logger.info("BigDecimal value of {} will be returned", key);
            return new BigDecimal(PropertyManager.getStringPropertiesValue(key, prop));
        }

        logger.info("Int value of {} will be returned by default", key);

        return Integer.parseInt(PropertyManager.getStringPropertiesValue(key, prop));

    }
}
