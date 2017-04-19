package com.example.annakocheshkova.testapplication.utils.converter;
import java.util.List;

/**
 * An interface to covert any data to some format
 */
public interface Converter<T> {
    /**
     * Converts the data (list) to some format
     * @param data the given data
     * @return formatted data
     */
    String convert(List<T> data);

    /**
     * Gets the data from formatted string
     * @param formattedData data to be parsed
     * @param type type of objects you want to import (note: pass this as SomeClass[].class)
     * @return List of objects of certain type
     */
    T[] deconvert(String formattedData, Class<T[]> type);

    /**
     * Gets the data from formatted string
     * @param formattedData data to be parsed
     * @param type type of objects you want to import (note: pass this as SomeClass.class)
     * @return object of certain type
     */
    T deconvertObject(String formattedData, Class<T> type);
}
