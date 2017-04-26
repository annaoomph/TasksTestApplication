package com.example.annakocheshkova.testapplication.operation;

import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;
import com.example.annakocheshkova.testapplication.utils.NetworkUtil;

/**
 * A component to retry executing operations
 */
class OperationRetryComponent {

    /**
     * Max count of tries for one operation
     */
    private static int maxTry;

    /**
     * A time (in ms) to wait before another retry
     */
    private static long retryWait;

    /**
     * Current amount of made retries
     */
    private int retryCount;

    /**
     * Creates an instance of retry component
     */
    OperationRetryComponent() {
        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        maxTry = preferencesManager.getMaxTry();
        retryWait = preferencesManager.getRetryWait();
    }

    /**
     * Send an operation for the retry
     * @param baseOperation operation
     * @return true if after a series of retries operation has been executed successfully, false otherwise
     */
    boolean send(BaseOperation baseOperation) {
        if (!NetworkUtil.isNetworkAvailable()) {
            retryCount = 1;
            return retry(baseOperation);
        } else {
            return false;
        }
    }

    /**
     * Retries to execute the operation after the given time and the given amount of times
     * @param baseOperation operation to retry executing
     * @return true if the operation has been executed, false otherwise
     */
    private boolean retry(BaseOperation baseOperation) {
        if (retryCount >= maxTry) {
            return false;
        } else {
            retryCount++;
            try {
                Thread.sleep(retryWait);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return baseOperation.execute() || retry(baseOperation);
        }
    }
}
