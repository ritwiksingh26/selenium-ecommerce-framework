package com.sdet.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {

    public static Logger getLogger(Class<?> clazz){
        return LogManager.getLogger(clazz);
    }
}
