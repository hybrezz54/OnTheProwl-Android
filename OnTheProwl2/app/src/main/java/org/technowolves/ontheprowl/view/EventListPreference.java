package org.technowolves.ontheprowl.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.technowolves.ontheprowl.Constants;
import org.technowolves.ontheprowl.R;
import org.technowolves.ontheprowl.TheBlueAllianceService;
import org.technowolves.ontheprowl.controller.ConfigManager;
import org.technowolves.ontheprowl.model.Event;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventListPreference extends Preference implements Callback<List<Event>> {

    /** Default event key */
    private static final String DEFAULT_VALUE = "2017ncral";

    /** Selected event key */
    private String mEventKey;

    /** Team number set by user */
    private String mNumber;

    /** FRC Season set by user */
    private String mSeason;

    /** List of events */
    private List<Event> mEvents;

    /**
     * Construct a new EventListPreference object
     *
     * @param context The application context
     * @param attrs The preference attributes
     */
    public EventListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWidgetLayoutResource(R.layout.preference_events);

        // retrieve team number set by user
        mNumber = ConfigManager.getTeamNumber(getContext());
        mSeason = ConfigManager.getSeason(getContext());

        // check if user has set other settings
        if (mNumber.length() < 1 || mSeason.length() != 4)
            setSummary(getContext().getString(R.string.pref_number_season_not_set));

        // get events from cache or TBA
        getEvents();
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
        final AppCompatSpinner spinner = (AppCompatSpinner) view.findViewById(R.id.spinner_event);
        spinner.setAdapter(new EventAdapter(getContext(), android.R.layout.simple_spinner_item,
                mEvents));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get event and save event key
                Event event = mEvents.get(position);
                ConfigManager.setEvent(getContext(), event.getKey());
                setSummary(event.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
        List<Event> events = (List) response.body();
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event event1, Event event2) {
                return event1.getName().compareTo(event2.getName());
            }
        });
    }

    @Override
    public void onFailure(Call<List<Event>> call, Throwable t) {
//        Snackbar.make(, getContext().getString(R.string.download_failure),
//                Snackbar.LENGTH_LONG).show();
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
        boolean fileExists = IOUtils.isFileExisting(this, SharedMap.TBA_DATA_DIR, getFileName());

        if (fileExists && eventInfo != null) {
            Gson gson = new Gson();
            String json = IOUtils.readStringFromFile(this, SharedMap.TBA_DATA_DIR, getFileName());
            List<Event> events = gson.fromJson(json, new TypeToken<List<Event>>() {}.getType());
            EventAdapter adapter = new EventAdapter(this,
                    android.R.layout.simple_dropdown_item_1line, events);
            eventInfo.setAdapter(adapter);
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
         * @param events
         */
        public EventAdapter(Context context, int resource, List<Event> events) {
            super(context,resource, events);
            this.context = context;
            this.events = events;
        }

        /**
         * Get the spinner view from the adapter
         *
         * @param position The position of element in the spinner dropdown
         * @param convertView The view to be used for each element
         * @param parent The ViewGroup the ConvertView belongs to
         * @return
         */
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            // get the event object and init variables
            Event event = getItem(position);
            TextView name;

            // find text view or create one
            if (convertView == null)
                name = new TextView(context);
            else
                name = (TextView) convertView.findViewById(android.R.id.text1);

            // set text view value
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
