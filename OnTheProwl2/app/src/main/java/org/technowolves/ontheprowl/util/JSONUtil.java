package org.technowolves.ontheprowl.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
    public static List<? extends Data> onRead(File file) {
        List<? extends Data> data = null;
        Gson gson = new Gson();
        BufferedReader reader = null;

        try {
            // init variables
            reader = new BufferedReader(new FileReader(file));
            Type type = new TypeToken<List<? extends Data>>() {}.getType();

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
    public static List<? extends Data> onRead(String file) {
        onRead(new File(file));
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
    public static boolean onWrite(String file, List<? extends Data> data) {
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
    public static boolean onWrite(String file, String[][] data) {
    }

    public static String toCSV(ArrayList data) {

    }

    public static String toCSV(ArrayList data, String file) {

    }

}
