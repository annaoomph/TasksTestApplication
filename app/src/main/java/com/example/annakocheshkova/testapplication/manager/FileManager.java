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

    public static final String defaultPath = Environment.getExternalStorageDirectory() + "/" + MyApplication.getAppContext().getString(R.string.folder_name) + "/";
    /**
     * Gets the list of files in some folder
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
     */
    public static String createFolder(String folderPath) throws FileNotFoundException{
        File folder = new File(folderPath);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (!success) throw new FileNotFoundException();
        return folderPath;
    }


}
