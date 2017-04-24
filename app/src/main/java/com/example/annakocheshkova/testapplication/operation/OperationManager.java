package com.example.annakocheshkova.testapplication.operation;
import com.example.annakocheshkova.testapplication.error.BaseError;
import com.example.annakocheshkova.testapplication.manager.LoginManager;
import com.example.annakocheshkova.testapplication.manager.configuration.ConfigurationManager;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;
import com.example.annakocheshkova.testapplication.error.ConnectionError;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;
import java.util.Date;
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
     * Application preferences manager
     */
    private PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();

    /**
     * Creates an instance of the Operation Manager
     */
    private OperationManager() {
        operationThread = new OperationThread();
        operationThread.start();
        operationQueue = new ConcurrentLinkedQueue<>();
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
            synchronized (monitor) {
                monitor.notifyAll();
            }
        }
    }

    /**
     * Executes the operation and checks if the token is valid
     */
    private void executeOperation(BaseOperation baseOperation) {
        Date expirationDate = preferencesManager.getExpirationDate();
        Date currentDate = new Date();// TODO What was wrong here
        if ((expirationDate == null || expirationDate.compareTo(currentDate) < 0) && baseOperation.getClass() != LoginOperation.class) {
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
                LoginManager loginManager = LoginManager.getInstance();
                loginManager.saveLoginData(token, expirationDate);
            }

            @Override
            public void onFailure(BaseError baseError) {
                LoginManager loginManager = LoginManager.getInstance();
                loginManager.logout();
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
        public void run(){
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
