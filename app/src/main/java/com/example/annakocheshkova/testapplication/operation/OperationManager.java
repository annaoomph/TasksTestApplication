package com.example.annakocheshkova.testapplication.operation;
import com.example.annakocheshkova.testapplication.error.BaseError;
import com.example.annakocheshkova.testapplication.manager.LoginManager;
import com.example.annakocheshkova.testapplication.error.ConnectionError;
import com.example.annakocheshkova.testapplication.utils.NetworkUtil;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * A class that manages all operations
 */
public class OperationManager {

    /**
     * A monitor for the threads to communicate
     */
    private static final Object monitor = new Object();

    /**
     * An operation manager one and only instance
     */
    private static final OperationManager operationManager = new OperationManager();

    /**
     * A thread-safe queue with all operations to be executed
     */
    private ConcurrentLinkedDeque<BaseOperation> operationQueue;

    /**
     * The thread that executes all operations from the queue
     */
    private final OperationThread operationThread;

    /**
     * Max count of tries for one operation
     */
    private static final int maxTry = 3;

    /**
     * A time (in ms) to wait before another retry
     */
    private static final long retryWait = 5000;

    /**
     * Current amount of made retries
     */
    private int retryCount;

    /**
     * A boolean, set to true if last attempt to relogin (for the operation first in queue) has failed.
     * Shows that another relogin is not necessary.
     */
    private boolean lastReloginFailed;

    /**
     * Creates an instance of the Operation Manager
     */
    private OperationManager() {
        operationThread = new OperationThread();
        operationThread.start();
        operationQueue = new ConcurrentLinkedDeque<>();
    }

    /**
     * Gets the only instance of operation manager
     * @return Operation Manager
     */
    public static OperationManager getInstance() {
        return operationManager;
    }

    /**
     * Pushes a new operation to the queue
     * @param baseOperation operation to be executed
     */
    public void enqueue(BaseOperation baseOperation) {
        if (baseOperation instanceof LoginOperation) {
            operationQueue.offerFirst(baseOperation);
        } else {
            operationQueue.offerLast(baseOperation);
        }
        if (!operationThread.isExecuting) {
            synchronized (monitor) {
                monitor.notifyAll();
            }
        }
    }

    /**
     * Executes the operation and checks if the token is valid, performs relogin if necessary
     * @param baseOperation operation to be executed
     * @return true if the operation has been executed properly and therefore should be removed from the queue, false otherwise
     */
    private boolean executeOperation(BaseOperation baseOperation) {
        if (LoginManager.getInstance().needRelogin() && !(baseOperation instanceof LoginOperation)) {
            if (lastReloginFailed) {
                lastReloginFailed = false;
            } else {
                LoginManager.getInstance().reLogin();
                return false;
            }
        }
        if (!baseOperation.execute()) {
            if (!NetworkUtil.isNetworkAvailable()) {
                retryCount = 1;
                retry(baseOperation);
            } else {
                if (baseOperation instanceof LoginOperation) {
                    lastReloginFailed = true;
                }
                BaseError error = new ConnectionError(baseOperation.getException().getMessage());
                baseOperation.getListener().onFailure(error);
            }
        }
        return true;
    }

    /**
     * Retries to execute the operation after the given time and the given amount of times
     * @param baseOperation operation to retry executing
     */
    private void retry(BaseOperation baseOperation) {
        if (retryCount >= maxTry) {
            if (baseOperation instanceof LoginOperation) {
                lastReloginFailed = true;
            }
            baseOperation.getListener().onFailure(new ConnectionError(ConnectionError.ConnectionErrorType.CONNECTION_ERROR));
        } else {
            retryCount++;
            try {
                Thread.sleep(retryWait);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!baseOperation.execute()) {
                retry(baseOperation);
            }
        }
    }

    /**
     * A background thread that executes operations
     */
    private class OperationThread extends Thread {

        /**
         * True if the thread is currently working
         */
        boolean isExecuting;

        /**
         * Executes operations from the queue
         */
        public void run() {
            while (true) {
                isExecuting = true;
                while (!operationQueue.isEmpty()) {
                    BaseOperation baseOperation = operationQueue.peek();
                    if (executeOperation(baseOperation)) {
                        operationQueue.remove(baseOperation);
                    }
                }
                isExecuting = false;
                try {
                    synchronized (monitor) {
                        monitor.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
