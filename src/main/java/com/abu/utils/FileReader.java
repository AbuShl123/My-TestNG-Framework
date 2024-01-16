package com.abu.utils;

import org.apache.logging.log4j.Level;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileReader {

    private FileReader() {
    }

    public static Properties readProperties(String path) {
        try (var input = new FileInputStream(path)) {

            Properties properties = new Properties();
            properties.load(input);

            return properties;
        } catch (IOException e) {
            Logger.log(Level.FATAL, "Couldn't read data from properties file: " + path);
            throw new RuntimeException(e.getMessage());
        }
    }
}
