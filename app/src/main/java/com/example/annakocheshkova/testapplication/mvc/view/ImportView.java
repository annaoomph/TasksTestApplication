package com.example.annakocheshkova.testapplication.mvc.view;

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
     * Redraws files when the item was chosen
     */
    void redrawFiles();

    /**
     * Gets path to current files folder
     * @return files folder
     */
    String getFolderPath();

    /**
     * Shows the error if the file is corrupt
     */
    void corruptFileError();

    /**
     * Shows the error if the file was not chosen
     */
    void showFileNotChosenError();

    /**
     * Closes the view on success
     * @param numberItems number of item that were added
     */
    void close(int numberItems);

    /**
     * Gets the path to chosen file
     * @return path
     */
    String getChosenFilePath();
}
