package com.example.annakocheshkova.testapplication.operation;

import com.example.annakocheshkova.testapplication.utils.converter.Converter;

import java.util.List;

/**
 * Class for handling export http operations
 * @param <T> type of data to be exported
 */
public class ExportOperation<T> extends BaseOperation<T> {

    /**
     * Items to be exported
     */
    private List<T> items;

    /**
     * Basic constructor. Creates an instance of the operation
     * @param url url to export items to
     * @param converter converter for items
     */
    public ExportOperation(String url, Converter<T> converter) {
        super(url, converter);
    }

    /**
     * Creates an instance of operation
     * @param url to export items to
     * @param converter converter for items
     * @param items list of data to be exported
     */
    public ExportOperation(String url, Converter<T> converter, List<T> items) {
        super(url, converter);
        this.items = items;
    }

    @Override
    String prepareContent() {
        return converter.convert(items);
    }

}
