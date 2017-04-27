package com.example.annakocheshkova.testapplication.utils;

import android.content.Context;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.utils.exception.BadResponseException;
import com.example.annakocheshkova.testapplication.utils.exception.ConnectionException;
import com.example.annakocheshkova.testapplication.utils.exception.CreateFileException;
import com.example.annakocheshkova.testapplication.utils.exception.NoInternetException;
import com.example.annakocheshkova.testapplication.utils.exception.ReadFileException;
import com.google.gson.JsonParseException;

import java.io.IOException;

/**
 * Some tools for handling exceptions
 */
public class ExceptionUtils {

    /**
     * Gets the message for the user to explain what kind of exception has happened
     * @param e exception
     * @return readable message
     */
    public static String getReadableMessage(Exception e) {
        Context context = MyApplication.getAppContext();
        if (e instanceof CreateFileException) {
            return context.getString(R.string.create_file_error) + e.getMessage();
        }
        if (e instanceof ReadFileException) {
            return context.getString(R.string.read_file_error) + e.getMessage();
        }
        if (e instanceof NoInternetException) {
            return context.getString(R.string.connection_error);
        }
        if (e instanceof BadResponseException) {
            return context.getString(R.string.error_server) + e.getMessage();
        }
        if (e instanceof ConnectionException) {
            return context.getString(R.string.cant_connect_msg) + e.getMessage();
        }
        if (e instanceof JsonParseException) {
            return context.getString(R.string.parse_error) + e.getMessage();
        }
        return context.getString(R.string.unexpected_error) + e.getMessage();
    }
}
