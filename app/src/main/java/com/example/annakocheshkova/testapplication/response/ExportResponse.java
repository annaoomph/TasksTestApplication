package com.example.annakocheshkova.testapplication.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Represents the response sent by server on Export
 */
public class ExportResponse extends BaseResponse {

    /**
     * Id of the current user (needed later to import data)
     */
    @SerializedName("userId") private int userId;

    /**
     * Gets the value of id and returns it     *
     * @return id
     */
    public int getUserId() {
        return userId;
    }
}
