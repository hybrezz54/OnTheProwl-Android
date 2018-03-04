package com.hybrez.ontheprowl.util.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hybrez.ontheprowl.model.Data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Useful methods to store
 * and read data from JSON files
 *
 * @author Hamzah Aslam
 */
public class JSONUtil {

    /**
     * Read the contents of a JSON file
     * into a list of objects
     *
     * @param file The object of the file to read from
     * @return The JSON data stored in a list of objects. Returns
     *         null if the file was not read successfully.
     */
    public static <T> List<T> read(File file) {
        List<T> data = null;
        Gson gson = new Gson();
        BufferedReader reader = null;

        try {
            // init variables
            reader = new BufferedReader(new FileReader(file));
            Type type = new TypeToken<List<T>>() {}.getType();

            // read into list
            data = gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return data;
    }

    /**
     * Read the contents of a JSON file
     * into a list of objects
     *
     * @param file The path of the file to read from
     * @return The JSON data stored in a list of objects. Returns
     *         null if the file was not read successfully.
     */
    public static <T> List<T> read(String file) {
        return read(new File(file));
    }

    /**
     * Write the contents of a list of objects to
     * a JSON file
     *
     * @param file The file object of the file to write to
     * @param data The list of objects to write to the file
     * @return True if the data was written successfully and
     * false otherwise
     */
    public static <T> boolean write(File file, List<T> data) {
        // init variables
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(data);
        BufferedWriter writer = null;

        try {
            // write data
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(json);

            // close writer
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    /**
     * Write the contents of a list of objects to
     * a JSON file
     *
     * @param file The path of the file to write to
     * @param data The list of objects to write to the file
     * @return True if the data was written successfully and
     * false otherwise
     */
    public static <T> boolean write(String file, List<T> data) {
        return write(new File(file), data);
    }

    /**
     * Write the contents of an array of String arrays to
     * a JSON file
     *
     * @param file The path of the file to write to
     * @param data The data to write to the file
     * @return True if the data was written successfully and
     *         false otherwise
     */
    public static boolean write(String file, String[][] data) {
        // init variables
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(data);
        BufferedWriter writer = null;

        try {
            // write data
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(json);

            // close writer
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    /**
     *
     * @param data
     * @return
     */
    public static String toJSON(String data) {
        return null;
    }

    /**
     *
     * @param data
     * @return
     */
    public static String toJSON(List<? extends Data> data) {
        return null;
    }

}
