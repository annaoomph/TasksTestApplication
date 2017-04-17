package com.example.annakocheshkova.testapplication.mvc.controller;

import com.example.annakocheshkova.testapplication.database.DataStore;
import com.example.annakocheshkova.testapplication.database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.manager.FileManager;
import com.example.annakocheshkova.testapplication.manager.converter.Converter;
import com.example.annakocheshkova.testapplication.manager.converter.ConverterFactory;
import com.example.annakocheshkova.testapplication.mvc.view.ExportView;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.manager.exporter.Exporter;
import com.example.annakocheshkova.testapplication.manager.exporter.ExporterFactory;
import com.example.annakocheshkova.testapplication.utils.NotImplementedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * A controller to handle export view
 */
public class ExportController {

    /**
     * Controller's main view
     */
    private ExportView view;

    /**
     * DataStore to work with data
     */
    private DataStore dataStore;

    /**
     * Creates new instance of the export controller
     * @param view main view
     */
    public ExportController(ExportView view) {
        this.view = view;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * Called when the export is clicked
     */
    public void onExport() {
        boolean local = view.isLocal();
        String nameOrPath = view.getNameOrPath();
        List<Task> tasks = dataStore.getAllTasks();
        try {
            Exporter<Task> taskExporter = ExporterFactory.getTaskExporter(local?ExporterFactory.ExportType.LOCAL_TO_FILE : ExporterFactory.ExportType.REMOTE);
            Converter<Task> converter = ConverterFactory.getConverter(ConverterFactory.ConvertType.JSON);
            taskExporter.exportData(tasks, nameOrPath, converter);
        } catch (FileNotFoundException exc) {
            view.showWrongFilePathError();
        }
        catch (IOException exception) {
            view.showIOError();
        }
        catch (NotImplementedException exception) {
            view.showNotImplementedError(exception);
        }
        view.showSuccessMessage(view.getNameOrPath() + FileManager.defaultPath);
        view.close();
    }
}
