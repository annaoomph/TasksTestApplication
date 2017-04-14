package com.example.annakocheshkova.testapplication.manager.exporter;

import com.example.annakocheshkova.testapplication.manager.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.HttpClient;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * An exporter to some server
 * @param <T> type of the data
 */
class RemoteExporter<T> implements Exporter<T>, Callback {

    @Override
    public void exportData(List<T> items, String url, Converter<T> converter) throws FileNotFoundException, IOException {
        String formattedData = converter.convert(items);
        HttpClient httpClient = new HttpClient(this);
        httpClient.doPostRequest(url, formattedData);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        //TODO think what to do there
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (!response.isSuccessful())
            throw  new IOException();
    }
}
