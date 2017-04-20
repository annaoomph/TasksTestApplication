package com.example.annakocheshkova.testapplication.response;

import com.google.gson.Gson;

/**
 * Represents the response sent by server on Export
 */
public class ExportResponse extends BaseResponse {

    /**
     * Id of the current user (needed later to import data)
     */
    private int userId;

    /**
     * Gets the value of id and returns it     *
     * @return id
     */
    public int getUserId() {
        return userId;
    }
}
