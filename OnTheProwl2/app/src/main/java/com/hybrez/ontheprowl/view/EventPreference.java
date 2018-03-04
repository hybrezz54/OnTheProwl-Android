package com.hybrez.ontheprowl.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hybrez.ontheprowl.Constants;
import com.hybrez.ontheprowl.R;
import com.hybrez.ontheprowl.SharedMap;
import com.hybrez.ontheprowl.TheBlueAllianceService;
import com.hybrez.ontheprowl.controller.ConfigManager;
import com.hybrez.ontheprowl.model.Event;
import com.hybrez.ontheprowl.util.IOUtils;
import com.hybrez.ontheprowl.util.JSONUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventPreference extends Preference implements Callback<List<Event>> {

    /** Default event key */
    private static final String DEFAULT_VALUE = "2017ncral";

    /** Selected event key */
    private String mEventKey;

    /** Team number set by user */
    private String mNumber;

    /** FRC Season set by user */
    private String mSeason;

    /** Spinner in the preference */
    private AppCompatSpinner mSpinner;

    /**
     * Construct a new EventPreference object
     *
     * @param context The application context
     * @param attrs The preference attributes
     */
    public EventPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWidgetLayoutResource(R.layout.preference_events);

        // retrieve team number set by user
        mNumber = ConfigManager.getTeamNumber(getContext());
        mSeason = ConfigManager.getSeason(getContext());

        // check if user has set other settings
        if (mNumber.length() < 1 || mSeason.length() != 4)
            setSummary(getContext().getString(R.string.pref_number_season_not_set));
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
        getEvents();
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
        ((EventAdapter) mSpinner.getAdapter()).notifyDataSetChanged();

        // save events
        String file = SharedMap.getInstance(getContext())
                .getEventCachePath(mNumber, mSeason);
        JSONUtil.write(file, events);
    }

    @Override
    public void onFailure(Call<List<Event>> call, Throwable t) {
        Snackbar.make(mSpinner, getContext().getString(R.string.download_failure),
                Snackbar.LENGTH_LONG).show();
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
    public boolean isPreferenceDisabled() {
        return (mNumber.length() < 1 || mSeason.length() != 4);
    }

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
        if (mSpinner != null) {
            EventAdapter adapter = (EventAdapter) mSpinner.getAdapter();
            return adapter.events;
        }

        return null;
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
        String file = SharedMap.getInstance(getContext())
                .getEventCachePath(mNumber, mSeason);
        boolean fileExists = IOUtils.doesFileExist(file);

        if (fileExists && mSpinner != null) {
            List<Event> events = JSONUtil.read(file);
            EventAdapter adapter = ((EventAdapter) mSpinner.getAdapter());
            adapter.clear();
            adapter.addAll(events);
            adapter.notifyDataSetChanged();
        }

        return fileExists;
    }

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

    /**
     * Create a new adapter to handle showing events in
     * the spinner
     */
    private class EventAdapter extends ArrayAdapter<Event> {

        /** the application context */
        private Context context;

        /** The list of events in the adapter */
        private List<Event> events;

        /**
         * Construct a new EventAdapter object
         *
         * @param context The application context
         * @param resource The view containing
         * @param events The list of events to add to the adapter
         */
        private EventAdapter(Context context, int resource, List<Event> events) {
            super(context, resource, events);
            this.context = context;
            this.events = events;
        }

        /**
         * Get the spinner view from the adapter
         *
         * @param position The position of element in the spinner dropdown
         * @param convertView The view to be used for each element
         * @param parent The ViewGroup the ConvertView belongs to
         * @return The view of each item in the adapter
         */
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            // get the event object and init variables
            Event event = getItem(position);

            // find text view or create one
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent,
                        false);
            }

            // set text view value
            TextView name = (TextView) convertView.findViewById(android.R.id.text1);
            name.setText(event.getName());

            return convertView;
        }

        /**
         * Get number of events in adapter
         *
         * @return The number of events
         */
        @Override
        public int getCount() {
            return events.size();
        }

        /**
         * Get the position of the event
         *
         * @param position The position of the event in the adapter
         * @return The position of the selected event
         */
        @Override
        public long getItemId(int position) {
            return position;
        }
    }

}
