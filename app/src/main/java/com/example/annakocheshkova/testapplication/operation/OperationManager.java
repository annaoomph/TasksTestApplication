package com.example.annakocheshkova.testapplication.operation;
import android.util.ArrayMap;

import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

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
    private ConcurrentLinkedQueue<BaseOperation> operationQueue;

    /**
     * The thread that executes all operations from the queue
     */
    private final OperationThread operationThread;

    /**
     * A map of listeners for operations
     */
    private Map<BaseOperation, OperationListener> listeners;

    /**
     * Creates an instance of the Operation Manager
     */
    private OperationManager() {
        operationThread = new OperationThread();
        operationThread.start();
        operationQueue = new ConcurrentLinkedQueue<>();
        listeners = new ArrayMap<>();
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
    public void enqueue(BaseOperation baseOperation, OperationListener operationListener) {
        listeners.put(baseOperation, operationListener);
        operationQueue.offer(baseOperation);
        if (!operationThread.isExecuting) {
            synchronized (monitor) {
                monitor.notifyAll();
            }
        }
    }

    /**
     * Executes the operation and checks if the token is valid, performs relogin if necessary
     * @param baseOperation operation to be executed
     */
    private void executeOperation(BaseOperation baseOperation) {
        if (!(baseOperation instanceof LoginOperation)) {
            if (!LoginManager.getInstance().tryRelogin(listeners.get(baseOperation))) {
                return;
            }
        }
        if (baseOperation.execute()) {
            success(baseOperation);
        } else {
            OperationRetryComponent operationRetryComponent = new OperationRetryComponent();
            if (operationRetryComponent.send(baseOperation)) {
                success(baseOperation);
            } else {
                error(baseOperation);
            }
        }
    }

    /**
     * Performs some action on successful execution
     * @param operation executed operation
     */
    private void success(BaseOperation operation) {
        OperationListener listener = listeners.get(operation);
        listener.onSuccess(operation.getBaseResponse());
    }

    /**
     * Performs some action on failed execution
     * @param operation operation that has not been executed
     */
    private void error(BaseOperation operation) {
        OperationListener listener = listeners.get(operation);
        listener.onFailure(operation.getError());
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
                    BaseOperation baseOperation = operationQueue.poll();
                    executeOperation(baseOperation);
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
