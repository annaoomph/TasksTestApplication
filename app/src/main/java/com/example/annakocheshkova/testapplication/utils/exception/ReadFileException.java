package com.example.annakocheshkova.testapplication.utils.exception;

import java.io.File;

/**
 * An exception thrown during reading the file
 */
public class ReadFileException extends Exception {

    /**
     * A file that has caused the exception
     */
    private File file;

    /**
     * Creates an instance of read file exception
     * @param file file that has caused the exception
     * @param e exception
     */
    public ReadFileException(File file, Exception e) {
        super(e.getMessage(), e.getCause());
        this.file = file;
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
