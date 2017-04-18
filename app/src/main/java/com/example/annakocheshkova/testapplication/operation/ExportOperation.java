package com.example.annakocheshkova.testapplication.operation;

import com.example.annakocheshkova.testapplication.utils.converter.Converter;

import java.util.List;

/**
 * Class for handling export http operations
 * @param <T> type of data to be exported
 */
public class ExportOperation<T> extends BaseOperation<T> {

    private List<T> items;

    public ExportOperation(String url, Converter<T> converter) {
        super(url, converter);
    }

    public ExportOperation(String url, Converter<T> converter, List<T> items) {
        super(url, converter);
        this.items = items;
    }

    @Override
    String prepareContent() {
        return converter.convert(items);
    }

}
