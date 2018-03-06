package com.hybrez.ontheprowl.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import com.hybrez.ontheprowl.Constants;
import com.hybrez.ontheprowl.R;
import com.hybrez.ontheprowl.config.EventAdapter;
import com.hybrez.ontheprowl.manager.SharedMap;
import com.hybrez.ontheprowl.manager.TheBlueAllianceService;
import com.hybrez.ontheprowl.manager.ConfigManager;
import com.hybrez.ontheprowl.model.Event;
import com.hybrez.ontheprowl.util.io.IOUtils;
import com.hybrez.ontheprowl.util.io.JSONUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpinnerPreference extends Preference {

    /** Spinner in the preference */
    private AppCompatSpinner mSpinner;

    /**
     * Construct a new SpinnerPreference object
     *
     * @param context The application context
     * @param attrs The preference attributes
     */
    public SpinnerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWidgetLayoutResource(R.layout.preference_events);
    }

    /**
     * Bind the view to the preference
     *
     * @param view The view to bind
     */
    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        // init spinner and set its values
        mSpinner = (AppCompatSpinner) view.findViewById(R.id.spinner_event);
        final EventAdapter adapter = new EventAdapter(getContext(), android.R.layout.simple_spinner_item,
                new ArrayList<Event>());
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get event and save event key
                Event event = adapter.getItem(position);
                ConfigManager.setEvent(getContext(), event.getKey());
                setSummary(event.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // get events from cache or TBA
        //getEvents();
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        super.onSetInitialValue(restorePersistedValue, defaultValue);
    }

    /**
     * Check if the team number and season have been set by the user
     *
     * @return True if other settings set and false otherwise
     */
//    public boolean isPreferenceDisabled() {
//        return (mNumber.length() < 1 || mSeason.length() != 4);
//    }

    /**
     * Returns the index of the given value in the list of events
     *
     * @param value The value whose index should be returned.
     * @param events The list of events to seach in
     * @return The index of the value, or -1 if not found.
     */
    public int findIndexOfValue(String value, List<Event> events) {
        if (value != null && events != null) {
            for (int i = events.size() - 1; i >= 0; i--) {
                if (events.get(i).getName().equals(value)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Get the events in the spinner
     *
     * @return The list of events
     */
    public List<Event> getEntries() {
//        if (mSpinner != null) {
//            EventAdapter adapter = (EventAdapter) mSpinner.getAdapter();
//            return adapter.events;
//        }

        return null;
    }

    /**
     * Check if events are stored in cache. If not,
     * get the events from The Blue Alliance.
     */
//    private void getEvents() {
//        if (!addTeamsFromStorage()) {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(Constants.TBA_BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            TheBlueAllianceService service = retrofit.create(TheBlueAllianceService.class);
//            Call<List<Event>> call = service.listEventsByTeam("frc" + mNumber,
//                    mSeason, Constants.TBA_AUTH_KEY);
//            call.enqueue(this);
//        }
//    }

    /**
     * Add events from a file if
     * stored on previous use of app
     *
     * @return Whether or not file exists
     */
//    private boolean addTeamsFromStorage() {
//        String file = SharedMap.getInstance(getContext())
//                .getEventCachePath(mNumber, mSeason);
//        boolean fileExists = IOUtils.doesFileExist(file);
//
//        if (fileExists && mSpinner != null) {
//            List<Event> events = JSONUtil.read(file);
//            EventAdapter adapter = ((EventAdapter) mSpinner.getAdapter());
//            adapter.clear();
//            adapter.addAll(events);
//            adapter.notifyDataSetChanged();
//        }
//
//        return fileExists;
//    }

    /**
     * Get the filename of the data file
     * based on the chosen event
     *
     * @return The filename
     */
    private String getFileName() {
        /*SharedPreferences manager = PreferenceManager.getDefaultSharedPreferences(this);

        // get index of season string array
        int idx = Integer.parseInt(manager.getString(FRC_SEASON, "0"));
        String season = getResources().getStringArray(R.array.season_names)[idx];

        // get filename
        String filename = season.substring(0, 4);
        filename += "_events.json";

        return filename;*/

        return "";
    }

}
