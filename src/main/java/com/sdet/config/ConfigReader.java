package com.sdet.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties;

    static {
        try {
            properties = new Properties();

            //using classpath to load config.properties
            InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("config/config.properties");
            if (is == null) throw new RuntimeException("config.properties not found in classpath");
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("failed to load config.properties" + e);
        }
    }

    public static String get(String key){
        String value = properties.getProperty(key);
        if (value == null) throw new RuntimeException("Key not found: "+ key);
        return value;
    }

    public static int getInt(String key){
        return Integer.parseInt(get(key));
    }
}
