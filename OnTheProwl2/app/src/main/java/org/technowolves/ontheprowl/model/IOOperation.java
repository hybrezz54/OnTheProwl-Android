package org.technowolves.ontheprowl.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Class to model a polled IO Operation in the IOQueue.
 * Handles what kind of file to create and what data to read
 * and write to the file.
 *
 * @author Hamzah Aslam
 */

public class IOOperation {

    /**
     * Enum to represent the file type
     * to save the data as
     */
    public enum FileType {
        CSV,
        JSON
    }

    /** List of data objects */
    private ArrayList data;

    /** File path */
    private String path;

    /** File type */
    private FileType type;

    /**
     * Constructor for the IOOperation class to read the data
     *
     * @param type The file type to save the data as
     * @param fileName The name of the file to read the data from
     */
    public IOOperation(Context context, FileType type, String fileName) {
        this.type = type;
        this.path = context.getFilesDir() + fileName;
    }

    /**
     * Constructor for the IOOperation class to write the data
     *
     * @param type The file type to save the data as
     * @param fileName The path of the location to save the data
     * @param data The name of the file where data will be written
     */
    public IOOperation(Context context, FileType type, String fileName, ArrayList data) {
        this.type = type;
        this.path = context.getFilesDir() + fileName;
        this.data = data;
    }

}
