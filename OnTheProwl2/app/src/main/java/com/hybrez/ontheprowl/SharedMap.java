package com.hybrez.ontheprowl;

import android.content.Context;

import com.hybrez.ontheprowl.util.IOUtils;

/**
 * Class to map where all application data
 * is stored. Class implements a singleton pattern.
 */
public class SharedMap {

    public final static String TBA_DATA_DIR = "downloaded";
    public final static String USER_DATA_DIR = "data";

    /** Context of the application */
    private Context context;

    /** Single instance of this class */
    private static SharedMap mInstance;

    /**
     * Get an instance of the SharedMap class
     *
     * @param context The context of the application
     * @return An instance of SharedMap
     */
    public static SharedMap getInstance(Context context) {
        if (mInstance == null)
            mInstance = new SharedMap(context);

        mInstance.setContext(context);
        return mInstance;
    }

    /**
     * Construct a new SharedMap object
     *
     * @param context The application context
     */
    private SharedMap(Context context) {
        this.context = context;
    }

    /**
     * Update the application context
     *
     * @param context The context to use
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Get the file path to store the event list downloaded
     * from The Blue Alliance
     *
     * @param number The number of the team
     * @param season The year of the current season
     * @return The path to store the event cache
     */
    public String getEventCachePath(String number, String season) {
        return IOUtils.getStorageDirectory(context, IOUtils.Directory.kCache)
                + "_" + number + "_" + season + ".json";
    }

}
