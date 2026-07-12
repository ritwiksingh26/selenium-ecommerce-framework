package com.sdet.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties;

    static {

        String env = System.getProperty("env", "dev"); //default = dev
        String fileName = "config/" + env + ".properties";
        properties = new Properties();

            //using classpath to load config.properties
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) throw new RuntimeException(fileName + "not found");
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Config file not found for env='" + env + "' at path: " + fileName + ". " +
                    "Valid values are: dev, staging", e);
        }
    }

    public static String get(String key){
        String value = properties.getProperty(key);
        if (value == null) throw new RuntimeException("Key '" + key + "' not found in config");
        return value.trim();
    }

    public static int getInt(String key){
        try {
            return Integer.parseInt(get(key));
        } catch (NumberFormatException e){
            throw new RuntimeException("Key '" + key + "' is not a valid integer in config");
        }

    }
}
