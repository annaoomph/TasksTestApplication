package com.example.annakocheshkova.testapplication.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that manages all the files operations
 */
public class FileManager {

    /**
     * Gets the list of files in some folder
     * @param folderPath path to folder
     * @return List of files in the folder
     */
    public static List<File> getFilesInFolder(String folderPath) {
        List<File> files = new ArrayList<>();
        File folder = new File(folderPath);
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                files.add(file);
            }
        }
        return files;
    }
}
