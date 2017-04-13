package com.example.annakocheshkova.testapplication.manager.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
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

    @Override
    public T[] deConvert(String formattedData, Class<T[]> type) {
        Gson gson = new Gson();
        return gson.fromJson(formattedData, type);
    }

}
