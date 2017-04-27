package com.example.annakocheshkova.testapplication.utils.exception;

import java.io.File;

/**
 * An exception raised during creating the file
 */
public class CreateFileException extends Exception {

    /**
     * A file that has not been created
     */
    private File file;

    /**
     * Creates an instance of the exception
     * @param file file that has not been created
     */
    public CreateFileException(File file) {
        this.file = file;
    }

    /**
     * Creates an instance of exception by another exception
     * @param e exception
     */
    public CreateFileException(Exception e) {
        super(e.getMessage(), e.getCause());
    }

    /**
     * Gets the message
     * @return message
     */
    @Override
    public String getMessage() {
        return file.getAbsolutePath() + "\n" + super.getMessage();
    }
}
