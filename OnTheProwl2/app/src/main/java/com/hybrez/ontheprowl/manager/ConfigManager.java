package com.hybrez.ontheprowl.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Class to manage preferences stored in
 * ConfigActivity
 */
public class ConfigManager {

    /** App initial launch preference key */
    private static final String INITIAL_LAUNCH = "initial_launch";

    /** Team Number preference key */
    private static final String TEAM_NUMBER = "team_number";

    /** FRC Season preference key */
    private static final String FRC_SEASON = "frc_season";

    /** FRC event preference key */
    private static final String FRC_EVENT = "frc_event";

    /**
     * Get whether this launch of the app is the initial launch
     *
     * @param context The application context
     * @return True if the application is launched for the first
     *         time and false otherwise
     */
    public static boolean isInitialLaunch(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(INITIAL_LAUNCH, true);
    }

    /**
     * Set not
     * @param context
     */
    public static void setNotInitialLaunch(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean(INITIAL_LAUNCH, false).apply();
    }

    /**
     * Get the team number from settings
     *
     * @param context The application context
     * @return The team number
     */
    public static String getTeamNumber(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(TEAM_NUMBER, "");
    }

    /**
     * Get the FRC season year from settings
     *
     * @param context The application context
     * @return The season year
     */
    public static String getSeason(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(FRC_SEASON, "");
    }

    /**
     * Get the FRC event key from settings
     *
     * @param context The application context
     * @return The event key
     */
    public static String getEvent(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(FRC_EVENT, "");
    }

    /**
     * Store the FRC event key in the preferences
     *
     * @param context The application context
     * @param eventKey The event key
     */
    public static void setEvent(Context context, String eventKey) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(FRC_EVENT, eventKey).apply();
    }

}
