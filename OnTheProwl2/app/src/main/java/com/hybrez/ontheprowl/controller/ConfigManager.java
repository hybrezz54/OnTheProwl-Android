package com.hybrez.ontheprowl.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Class to manage preferences stored in
 * ConfigActivity
 */
public class ConfigManager {

    /** Team Number preference key */
    public static final String TEAM_NUMBER = "team_number";

    /** FRC Season preference key */
    public static final String FRC_SEASON = "frc_season";

    /** FRC event preference key */
    public static final String FRC_EVENT = "frc_event";

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
