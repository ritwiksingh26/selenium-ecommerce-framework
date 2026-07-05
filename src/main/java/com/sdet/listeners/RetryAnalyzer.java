package com.sdet.listeners;

import com.sdet.utils.LogUtil;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger log = LogUtil.getLogger(RetryAnalyzer.class);
    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 2;

    @Override
    public boolean retry(ITestResult result){
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            log.warn("Retrying test '{}' - attempt {} of {}", result.getName(), retryCount, MAX_RETRY_COUNT);
            return true;
        }
        return false;
    }
}
