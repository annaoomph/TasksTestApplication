package com.example.annakocheshkova.testapplication.error;

import android.content.Context;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;

/**
 * A basic class describing an error that can happen during the file creation
 */
public class FileError implements BaseError {

    /**
     * Types of errors connected with files
     */
    public enum FileErrorType {
        IO_ERROR,
        CREATE_FILE_ERROR,
        PARSE_ERROR
    }

    /**
     * File error type
     */
    private FileErrorType fileErrorType;

    /**
     * Creates an instance of FileError
     * @param fileErrorType type of the error
     */
    public FileError(FileErrorType fileErrorType) {
        this.fileErrorType = fileErrorType;
    }

    @Override
    public String getErrorMessage() {
        Context context = MyApplication.getAppContext();
        switch (fileErrorType) {
            case IO_ERROR: return context.getString(R.string.io_error);
            case CREATE_FILE_ERROR: return context.getString(R.string.create_file_error);
            case PARSE_ERROR: return context.getString(R.string.parse_error);
            default: return context.getString(R.string.unexpected_error);
        }
    }
}
