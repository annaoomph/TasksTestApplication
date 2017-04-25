package com.example.annakocheshkova.testapplication.mvc.view;

import com.example.annakocheshkova.testapplication.error.BaseError;

import java.io.File;
import java.util.List;

/**
 * A view for import activity
 */
public interface ImportView {

    /**
     * Returns if the import should be local
     * @return true if local, false if remote
     */
    boolean isLocal();

    /**
     * Shows the available list of files
     * @param files list of files
     */
    void showFiles(List<File> files);

    /**
     * Shows the error
     * @param error error that happened during import
     */
    void showError(BaseError error);

    /**
     * Shows the error if the file was not chosen
     */
    void showFileNotChosenError();

    /**
     * Closes the view on success
     */
    void close();

    /**
     * Shows the number of items that were imported
     * @param numberItems number of items that were added
     */
    void showSuccessMessage(int numberItems);

    /**
     * Gets the path to chosen file
     * @return path
     */
    String getNameOrPath();

    /**
     * Shows or hides the content that is available only for logged in users
     * @param loggedIn true if user is logged in
     */
    void setLoggedIn(boolean loggedIn);

    /**
     * Gets the date of export, if it was chosen
     * @return date of export
     */
    String getExportDate();
}
