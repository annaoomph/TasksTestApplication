package com.example.annakocheshkova.testapplication.operation;

import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;
import com.example.annakocheshkova.testapplication.utils.NetworkUtil;

/**
 * A component to retry executing operations
 */
public class OperationRetryComponent {

    /**
     * Max count of tries for one operation
     */
    private int maxTry;

    /**
     * A time (in ms) to wait before another retry
     */
    private long retryWait;

    /**
     * Current amount of made retries
     */
    private int retryCount;

    /**
     * Creates an instance of retry component
     */
    public OperationRetryComponent() {
        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        maxTry = preferencesManager.getMaxOperationsTryCount();
        retryWait = preferencesManager.getIntervalBetweenRetries();
    }

    /**
     * Checks if retry is necessary
     * @return true if yes, false otherwise
     */
    public boolean needRetry() {
        return !NetworkUtil.isNetworkAvailable() && retryCount < maxTry;
    }

    /**
     * Tries to execute the operation and retry executing, if needed, after the given time and the given amount of times
     * @param baseOperation operation to execute
     * @return true if the operation has been executed, false otherwise
     */
    public boolean execute(BaseOperation baseOperation) {
        if (baseOperation.execute()) {
            return true;
        } else {
            retryCount = 1;
            while (needRetry()) {
                try {
                    Thread.sleep(retryWait);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (baseOperation.execute()) {
                    return true;
                }
                retryCount++;
            }
        }
        return false;
    }
}
