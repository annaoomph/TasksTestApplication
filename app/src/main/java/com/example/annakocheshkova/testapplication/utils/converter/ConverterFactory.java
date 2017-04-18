package com.example.annakocheshkova.testapplication.utils.converter;

import com.example.annakocheshkova.testapplication.utils.NotImplementedException;

/**
 * A factory to return an instance of the converter
 */
public class ConverterFactory {

    /**
     * Enum with conversion types
     */
    public enum ConvertType {
        JSON
    }

    /**
     * Gets an instance of the converter
     * @param convertType conversion format
     * @return instance of the converter
     */
    public static Converter getConverter(ConvertType convertType){
        if (convertType == ConvertType.JSON) {
            return new JsonConverter();
        } else {
            throw new RuntimeException(new NotImplementedException(convertType.toString()));
        }
    }
}
