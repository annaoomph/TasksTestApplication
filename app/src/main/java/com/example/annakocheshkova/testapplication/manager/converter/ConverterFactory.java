package com.example.annakocheshkova.testapplication.manager.converter;

/**
 * A factory to return an instance of the converter
 */
public class ConverterFactory {

    public static Converter getConverter() {
        return new JsonConverter();
    }
}
