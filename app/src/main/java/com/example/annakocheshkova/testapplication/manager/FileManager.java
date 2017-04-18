package com.example.annakocheshkova.testapplication.manager;

import android.os.Environment;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that manages all the files operations
 */
public class FileManager {

    /**
     * A path to the default folder the application stores items in
     */
    public static final String DEFAULT_PATH = Environment.getExternalStorageDirectory() + "/" + MyApplication.getAppContext().getString(R.string.folder_name) + "/";

    /**
     * Gets the list of files in some folder
     * @param folderPath path of the folder to look into
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

    /**
     * Creates folder for a file if it doesn't exist
     * @param folderPath path to the folder we want to create
     * @throws FileNotFoundException exception if fodler was not created
     */
    public static void createFolder(String folderPath) throws FileNotFoundException{
        File folder = new File(folderPath);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (!success) {
            throw new FileNotFoundException();
        }
    }
}
