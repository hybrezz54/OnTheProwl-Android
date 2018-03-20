package com.hybrez.ontheprowl;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hybrez.ontheprowl.manager.ConfigManager;
import com.hybrez.ontheprowl.manager.SharedMap;
import com.hybrez.ontheprowl.manager.TheBlueAllianceService;
import com.hybrez.ontheprowl.model.Event;
import com.hybrez.ontheprowl.util.SpinnerPreference;
import com.hybrez.ontheprowl.util.io.IOUtils;
import com.hybrez.ontheprowl.util.io.JSONUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EventPreference extends SpinnerPreference implements Callback<List<Event>> {

    /** List of events */
    private List<Event> mEvents;

    /** The team number */
    private String mNumber;

    /** The season */
    private String mSeason;

    /**
     * Construct a new EventPreference
     *
     * @param context The preference context
     * @param attrs The preference attributes
     */
    public EventPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Construct a new EventPreference
     *
     * @param context The preference context
     * @param attrs The preference attributes
     * @param defStyleAttr The style attributes for the preference
     */
    public EventPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Create view for each spinner item
     *
     * @param position Position of item in spinner
     * @param parent Parent view or layout of the spinner item
     * @return Inflated view for the spinner item
     */
    @Override
    protected View createDropDownView(int position, ViewGroup parent) {
        return LayoutInflater.from(getContext())
                .inflate(android.R.layout.simple_spinner_item, parent, false);
    }

    /**
     * Bind the spinner item view to the preference
     *
     * @param position Position in the spinner
     * @param view View to bind
     */
    @Override
    protected void bindDropDownView(int position, View view) {
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(mEntries[position]);
    }

    @Override
    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
        // get the events
        mEvents = response.body();
        Collections.sort(mEvents, new Comparator<Event>() {
            @Override
            public int compare(Event event1, Event event2) {
                return event1.getName().compareTo(event2.getName());
            }
        });

        // update the spinner
        updateSpinner();

        // save events
        String file = SharedMap.getInstance(getContext()).getEventCachePath(mNumber, mSeason);
        JSONUtil.write(file, mEvents);
    }

    @Override
    public void onFailure(Call<List<Event>> call, Throwable t) {
        Snackbar.make(null, getContext().getString(R.string.download_failure),
                Snackbar.LENGTH_LONG).show();
    }

    public void updateEvents(String number, String season) {
        mNumber = number;
        mSeason = season;
        getEvents();
    }

    /**
     * Check if events are stored in cache. If not,
     * get the events from The Blue Alliance.
     */
    private void getEvents() {
        //if (!addTeamsFromStorage(mNumber, mSeason)) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.TBA_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TheBlueAllianceService service = retrofit.create(TheBlueAllianceService.class);
            Call<List<Event>> call = service.listEventsByTeam("frc" + mNumber,
                    mSeason, Constants.TBA_AUTH_KEY);
            call.enqueue(this);
        //}
    }

    /**
     * Add events from a file if
     * stored on previous use of app
     *
     * @return Whether or not file exists
     */
    private boolean addTeamsFromStorage(String number, String season) {
        String file = SharedMap.getInstance(getContext())
                .getEventCachePath(number, season);
        boolean fileExists = IOUtils.doesFileExist(file);

        // if a cache file exists
        if (fileExists) {
            // add to event and adapter list
            mEvents = JSONUtil.read(file);
            updateSpinner();
        }

        return fileExists;
    }

    /**
     * Helper method to update value arrays with the
     * event names and keys
     */
    private void updateSpinner() {
        // clear values
        mEntries = new String[mEvents.size()];
        mEntryValues = new String[mEvents.size()];

        // update accordingly
        for (int i = 0; i < mEvents.size(); i++) {
            Event event = mEvents.get(i);
            mEntries[i] = event.getName();
            mEntryValues[i] = event.getKey();
        }
    }

}
