package com.hybrez.ontheprowl.model;

import android.content.Context;

import com.hybrez.ontheprowl.util.io.IOUtils;
import com.opencsv.bean.ColumnPositionMappingStrategy;

import java.io.File;

/**
 * An abstract class to be extended by any model class that
 * wishes to store its data in a CSV or JSON format
 */
public abstract class Data {

    /** Data name */
    private String name;

    /** Path of the directory to store data */
    private String path;

    /**
     * Constructor for the Data class
     *
     * @param name The name of the data
     * @param path The path of the directory to store the data
     */
    public Data(String name, String path) {
        this.name = name;
        this.path = path;

        // create directories if not already
        new File(path).mkdirs();
    }

    /**
     * Constructor for the Data class
     *
     * @param context The context of the application
     * @param name The name of the data
     * @param dir The directory to store the data in
     */
    public Data(Context context, String name, IOUtils.Directory dir) {
        this.name = name;
        this.path = IOUtils.getStorageDirectory(context, dir).getPath();
    }

    /**
     * Get the name of the data
     *
     * @return The name of the data
     */
    public String getName() {
        return name;
    }

    /**
     * Get the path of the directory to store the data
     *
     * @return The path of the directory
     */
    public String getDirectoryPath() {
        return path;
    }

    /**
     * Get the file object of the directory to store the data
     *
     * @return The file object of the directory
     */
    public File getDirectoryFile() {
        return new File(path);
    }

    /**
     * Get the mapping strategy to help figure out what fields to
     * map the columns in the CSV file to
     *
     * @return Strategy to map CSV columns to fields or null if
     * the class does not wish to store its data in a CSV format
     */
    public abstract ColumnPositionMappingStrategy getMappingStrategy();

    /**
     * Get a string representation of the data
     *
     * @return The data as a string
     */
    public abstract String toString();

}
