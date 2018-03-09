package com.hybrez.ontheprowl.config.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.hybrez.ontheprowl.Constants;
import com.hybrez.ontheprowl.R;
import com.hybrez.ontheprowl.config.EventAdapter;
import com.hybrez.ontheprowl.manager.ConfigManager;
import com.hybrez.ontheprowl.manager.SharedMap;
import com.hybrez.ontheprowl.manager.TheBlueAllianceService;
import com.hybrez.ontheprowl.model.Event;
import com.hybrez.ontheprowl.util.SpinnerPreference;
import com.hybrez.ontheprowl.util.io.IOUtils;
import com.hybrez.ontheprowl.util.io.JSONUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class ConfigActivity extends AppCompatPreferenceActivity {

    /**
     * Construct a new ConfigActivity class
     */
    public ConfigActivity() {
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);
            } else if (preference instanceof SpinnerPreference) {
                // Don't update anything
            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }

            return true;
        }
    };

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment implements Callback<List<Event>>,
            SharedPreferences.OnSharedPreferenceChangeListener {

        /** Team number set by user */
        private String mNumber;

        /** FRC Season set by user */
        private String mSeason;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("team_number"));
            bindPreferenceSummaryToValue(findPreference("frc_season"));
            bindPreferenceSummaryToValue(findPreference("frc_event"));
            bindPreferenceSummaryToValue(findPreference("server_url"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                getActivity().onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            // retrieve team number set by user
            mNumber = ConfigManager.getTeamNumber(getActivity());
            mSeason = ConfigManager.getSeason(getActivity());

            // check if user has set other settings
            if (mNumber.length() > 1 && mSeason.length() == 4) {
                getEvents();
            }
        }

        @Override
        public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
            // get the events
            List<Event> events = response.body();
            Collections.sort(events, new Comparator<Event>() {
                @Override
                public int compare(Event event1, Event event2) {
                    return event1.getName().compareTo(event2.getName());
                }
            });

            // TODO: set the events

            // save events
            String file = SharedMap.getInstance(getActivity()).getEventCachePath(mNumber, mSeason);
            JSONUtil.write(file, events);
        }

        @Override
        public void onFailure(Call<List<Event>> call, Throwable t) {
            Snackbar.make(getView(), getString(R.string.download_failure),
                    Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void onResume() {
            super.onResume();
//            getPreferenceManager().getSharedPreferences()
//                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
//            getPreferenceManager().getSharedPreferences()
//                    .unregisterOnSharedPreferenceChangeListener(this);
        }

        /**
         * Check if events are stored in cache. If not,
         * get the events from The Blue Alliance.
         */
        private void getEvents() {
            if (!addTeamsFromStorage()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.TBA_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TheBlueAllianceService service = retrofit.create(TheBlueAllianceService.class);
                Call<List<Event>> call = service.listEventsByTeam("frc" + mNumber,
                        mSeason, Constants.TBA_AUTH_KEY);
                call.enqueue(this);
            }
        }

        /**
         * Add events from a file if
         * stored on previous use of app
         *
         * @return Whether or not file exists
         */
        private boolean addTeamsFromStorage() {
            String file = SharedMap.getInstance(getActivity())
                    .getEventCachePath(mNumber, mSeason);
            boolean fileExists = IOUtils.doesFileExist(file);

            if (fileExists) {
                List<Event> events = JSONUtil.read(file);
                // TODO: add to spinner adapter
            }

            return fileExists;
        }
    }

    /**
     * Clear the data cached from THe Blue Alliance and
     * other online sources
     *
     * @param v The clicked button
     */
    public void clearCache(View v) {
        // Show alert dialog to warn user
        createDialog(getString(R.string.dlg_clear_cache),
                getString(R.string.msg_clear_cache), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: Implement clearing cache
                    }
                });
    }

    /**
     * Clear the data stored by the user
     *
     * @param v The clicked button
     */
    public void clearData(View v) {
        // Show alert dialog to warn user
        createDialog(getString(R.string.dlg_clear_data),
                getString(R.string.msg_clear_data), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: Implement clearing data
                    }
                });
    }

    /**
     * Create a dialog to show to the user
     *
     * @param title The title of the dialog box
     * @param message The message to show to user
     * @param listener Event for clicking positive button
     */
    private void createDialog(String title, String message,
                              DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        else
            builder = new AlertDialog.Builder(this);

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, listener)
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
