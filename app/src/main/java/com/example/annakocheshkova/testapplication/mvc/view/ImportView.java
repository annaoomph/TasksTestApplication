package com.example.annakocheshkova.testapplication.mvc.view;

import com.example.annakocheshkova.testapplication.utils.NotImplementedException;

import java.io.File;
import java.util.List;

/**
 * A view for import activity
 */
public interface ImportView {

    /**
     * Shows the available list of files
     * @param files list of files
     */
    void showFiles(List<File> files);

    /**
     * Shows the error if the file is corrupt
     */
    void showCorruptFileError();

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
    String getChosenFilePath();
}
