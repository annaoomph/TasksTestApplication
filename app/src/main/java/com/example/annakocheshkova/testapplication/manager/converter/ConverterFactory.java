package com.example.annakocheshkova.testapplication.manager.converter;

/**
 * A factory to return an instance of the converter
 */
public class ConverterFactory {

    public static Converter getConverter() {
        //TODO Check which format we need
        return new JsonConverter();
    }
}
