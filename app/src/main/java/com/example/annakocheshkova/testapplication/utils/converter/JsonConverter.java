package com.example.annakocheshkova.testapplication.utils.converter;

import com.google.gson.Gson;
import java.util.List;
import java.util.Map;

/**
 * A converter to format all data to json
 * @param <T> data type
 */
class JsonConverter<T> implements Converter<T>{

    @Override
    public String convert(List<T> data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    @Override
    public String convert(Map<T, T> data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    @Override
    public T[] deconvert(String formattedData, Class<T[]> type) {
        Gson gson = new Gson();
        return gson.fromJson(formattedData, type);
    }

}
