package com.example.annakocheshkova.testapplication.manager.exporter;

import com.example.annakocheshkova.testapplication.manager.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.HttpClient;
import com.example.annakocheshkova.testapplication.utils.Listener.HttpListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * An exporter to some server
 * @param <T> type of the data
 */
class RemoteExporter<T> implements Exporter<T>, HttpListener {

    @Override
    public void exportData(List<T> items, String url, Converter<T> converter) throws FileNotFoundException, IOException {
        String formattedData = converter.convert(items);
        HttpClient httpClient = new HttpClient(this);
        httpClient.doPostRequest(url, formattedData);
    }

    @Override
    public void onFailure() throws IOException{
        throw new IOException();
    }

    @Override
    public void onSuccess(String response) {

    }
}
