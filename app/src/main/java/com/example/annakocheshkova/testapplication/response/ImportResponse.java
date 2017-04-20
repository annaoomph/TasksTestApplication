package com.example.annakocheshkova.testapplication.response;

import com.google.gson.Gson;

/**
 * Represents the response sent by server on Import
 */
public class ImportResponse extends BaseResponse{

    /**
     * Imported data
     */
    private String items;

    /**
     * Gets the value of items and returns it     *
     * @return items
     */
    public String getItems() {
        return items;
    }
}
