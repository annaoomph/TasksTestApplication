package com.example.annakocheshkova.testapplication.manager.exporter;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.manager.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.ConfigurationManager;
import com.example.annakocheshkova.testapplication.utils.HttpClient;
import com.example.annakocheshkova.testapplication.utils.Listener.ExportListener;
import com.example.annakocheshkova.testapplication.utils.Listener.HttpListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    public void exportData(List<T> items, String url, Converter<T> converter, ExportListener exportListener) {
        try {
            this.exportListener = exportListener;
            HttpClient httpClient = new HttpClient(this);
            String formattedData = converter.convert(items);
            String fakeRequestString = ConfigurationManager.getConfigValue(MyApplication.getAppContext().getString(R.string.fake_request_config_name));
            boolean fakeRequest = fakeRequestString.equalsIgnoreCase("true");
            if (fakeRequest) {
                httpClient.doFakeRequest(url);
            } else {
                httpClient.doPostRequest(url, formattedData);
            }
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
