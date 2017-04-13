package com.example.annakocheshkova.testapplication.manager.converter;


import java.util.List;

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
}
