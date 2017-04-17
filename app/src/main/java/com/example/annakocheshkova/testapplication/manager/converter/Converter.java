package com.example.annakocheshkova.testapplication.manager.converter;


import java.util.List;
import java.util.Map;

/**
 * An interface to covert any data to some format
 */
public interface Converter<T> {
    /**
     * Converts the data to some format
     * @param data the given data
     * @return formatted data
     */
    String convert(List<T> data);

    /**
     * Converts the data to some format
     * @param data the given data
     * @return formatted data
     */
    String convert(Map<T, T> data);

    /**
     * Gets the data from formatted string
     * @param formattedData data to be parsed
     * @param type type of objects you want to import (note: pass this as SomeClass[].class)
     * @return List of objects of certain type
     */
    T[] deconvert(String formattedData, Class<T[]> type);
}
