package org.technowolves.ontheprowl.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Useful methods to store
 * and read data from CSV files
 *
 * @author Hamzah Aslam
 */
public class CSVUtil {

    /**
     * Read the contents of a CSV file
     * into a list of objects
     *
     * @param file The object of the file to read from
     * @return The CSV data stored in a list of objects. Returns
     *         null if the file was not read successfully.
     */
    public static List<? extends Data> onRead(File file) {
        List<? extends Data> data = null;
        BufferedReader reader = null;

        try {
            // init reader and parser
            reader = new BufferedReader(new FileReader(file));
            CsvToBean csv = new CsvToBeanBuilder(reader)
                    .withType(Data.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            // read contents into list
            data = csv.parse();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    // close reader
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return data;
    }

    /**
     * Read the contents of a CSV file
     * into a list of objects
     *
     * @param file The path of the file to read from
     * @return The CSV data stored in a list of objects. Returns
     *         null if the file was not read successfully.
     */
    public static List<? extends Data> onRead(String file) {
        return onRead(new File(file));
    }

    /**
     * Read the contents of a CSV file into a list of
     * String arrays. The method assumes the first line of
     * the CSV file contains the header.
     *
     * @param file The path of the file to read from
     * @param withHeader Store the CSV header in the list if true
     * @return The CSV data stored in a list of String arrays. Returns
     *         null if the file was not read successfully.
     */
    public static List<String[]> onRead(String file, boolean withHeader) {
        List<String[]> data = null;
        BufferedReader reader = null;

        try {
            // init reader and parser
            reader = new BufferedReader(new FileReader(file));
            CSVReader csv = new CSVReader(reader);

            // read contents into list
            data = csv.readAll();

            // remove header if specified
            if (!withHeader) data.remove(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    // close reader
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return data;
    }

    /**
     * Write the contents of a list of objects to
     * a CSV file
     *
     * @param file The path of the file to write to
     * @param data The list of objects to write to the file
     * @return True if the data was written successfully and
     * false otherwise
     */
    public static boolean onWrite(String file, List<? extends Data> data) {
        BufferedWriter writer = null;

        try {
            // init writer and parser
            writer = new BufferedWriter(new FileWriter(file));
            StatefulBeanToCsv csv = new StatefulBeanToCsvBuilder<>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withEscapechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();

            // write data
            csv.write(data);

            // close writer
            writer.close();
            return true;
        } catch (Exception e) {
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
     * Write the contents of an array of String arrays to
     * a CSV file
     *
     * @param file The path of the file to write to
     * @param data The data to write to the file with the first element
     *             containing the CSV header
     * @return True if the data was written successfully and
     *         false otherwise
     */
    public static boolean onWrite(String file, String[][] data) {
        BufferedWriter writer = null;

        try {
            // init writer and parser
            writer = new BufferedWriter(new FileWriter(file));
            CSVWriter csv = new CSVWriter(writer,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            // write data
            for (String[] line : data) {
                csv.writeNext(line);
            }

            // close writer
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    // close writer
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    public static String toJSON(String file) {
        return null;
    }

    public static String toJSON(ArrayList data, String file) {
        return null;
    }

}
