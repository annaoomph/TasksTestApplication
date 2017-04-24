package com.example.annakocheshkova.testapplication.operation;
import com.example.annakocheshkova.testapplication.manager.LoginManager;
import com.example.annakocheshkova.testapplication.manager.configuration.ConfigurationManager;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;
import com.example.annakocheshkova.testapplication.error.ConnectionError;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * A class that manages all operations
 */
public class OperationManager {

    /**
     * An operation manager one and only instance
     */
    private static final OperationManager operationManager = new OperationManager();

    /**
     * A queue with all operations to be executed
     */
    private BlockingQueue<BaseOperation> operationQueue;

    /**
     * The thread that executes all operations from the queue
     */
    private final OperationThread operationThread;

    /**
     * Application preferences manager
     */
    private PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();

    /**
     * Creates an instance of the Operation Manager
     */
    private OperationManager() {
        operationThread = new OperationThread();
        operationThread.start();
        operationQueue = new ArrayBlockingQueue<>(1);
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
        operationQueue.offer(baseOperation);
        if (!operationThread.isExecuting) {
            synchronized (operationThread) {
                operationThread.notify();
            }
        }
    }

    /**
     * Executes the operation and checks if the token is valid
     */
    private void executeOperation(BaseOperation baseOperation) {
        String expirationDateString = preferencesManager.getString(PreferencesManager.EXPIRE);
        DateFormat dateFormat = DateFormat.getDateTimeInstance(3, 0); // 3 specifies the dd/mm/yy date format; 0 specifies the standard GMT time format
        Date expirationDate;
        try {
            expirationDate = dateFormat.parse(expirationDateString);
        } catch (ParseException e) {
            expirationDate = new Date();
        }
        if (expirationDate.compareTo(new Date()) < 0) {
            reLogin();
        }
        baseOperation.execute();
    }

    /**
     * Gets the new token when the previous one has expired
     */
    private void reLogin() {
        String url = ConfigurationManager.getConfigValue(ConfigurationManager.SERVER_URL);
        LoginOperation loginOperation = new LoginOperation(url, new OperationListener<LoginOperation>() {
            @Override
            public void onSuccess(LoginOperation operation) {
                String token = operation.getToken();
                String expirationDate = operation.getExpirationDate();
                preferencesManager.putString(PreferencesManager.EXPIRE, expirationDate);
                preferencesManager.putBoolean(PreferencesManager.LOGGED_IN, true);
                preferencesManager.putString(PreferencesManager.TOKEN, token);
            }

            @Override
            public void onFailure(ConnectionError connectionError) {
                LoginManager.logout();
            }
        });
        loginOperation.execute();
    }


    /**
     * The thread that executes operations
     */
    private class OperationThread extends Thread {

        /**
         * True if the thread is currently working
         */
        boolean isExecuting;

        /**
         * Executes operations in line
         */
        public synchronized  void run(){
            while (true) {
                isExecuting = true;
                while (!operationQueue.isEmpty()) {
                    BaseOperation baseOperation = operationQueue.poll();
                    executeOperation(baseOperation);
                }
                isExecuting = false;
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
