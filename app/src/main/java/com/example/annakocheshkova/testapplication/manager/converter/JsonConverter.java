package com.example.annakocheshkova.testapplication.manager.converter;

import com.google.gson.Gson;

import java.util.List;

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
}
