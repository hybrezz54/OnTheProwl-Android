package org.technowolves.ontheprowl.util;

import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * An abstract class to be extended by any model class that
 * wishes to store its data in a CSV or JSON format
 */
public abstract class Data {

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
