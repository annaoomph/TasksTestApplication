package com.example.annakocheshkova.testapplication.response;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the response sent by server on Export
 */
public class ExportResponse extends BaseResponse {

    /**
     * Date of export (needed later to import data)
     */
    @SerializedName("exportDate") private String exportDate;

    /**
     * Gets the value of export date and returns it
     * @return export date
     */
    public String getExportDate() {
        return exportDate;
    }
}
