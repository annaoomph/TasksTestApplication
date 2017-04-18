package com.example.annakocheshkova.testapplication.manager.exporter;

import com.example.annakocheshkova.testapplication.manager.converter.Converter;
import com.example.annakocheshkova.testapplication.client.BaseHttpClient;
import com.example.annakocheshkova.testapplication.client.BaseHttpClientFactory;
import com.example.annakocheshkova.testapplication.utils.listener.ExportListener;
import com.example.annakocheshkova.testapplication.utils.listener.HttpListener;

import java.io.IOException;
import java.util.List;

/**
 * An exporter to server
 * @param <T> type of the data
 */
class RemoteExporter<T> implements Exporter<T>, HttpListener {

    /**
     * Responds to export events
     */
    private ExportListener exportListener;

    @Override
    public void exportData(List<T> items, String url, String path, Converter<T> converter, ExportListener exportListener) {
        try {
            this.exportListener = exportListener;
            BaseHttpClient httpClient = BaseHttpClientFactory.getHttpClient(this);
            String formattedData = converter.convert(items);
            httpClient.doPostRequest(url, formattedData);
        } catch (IOException exception) {
            exportListener.onIOError();
        }
    }

    @Override
    public void onFailure() {
        exportListener.onConnectionError();
    }

    @Override
    public void onSuccess(String response) {
        exportListener.onSuccess();
    }

    @Override
    public void onUnauthorized() {
        exportListener.onUnauthorized();
    }
}
