package org.technowolves.ontheprowl.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.ListPreference;
import android.util.AttributeSet;

import org.technowolves.ontheprowl.model.Event;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListPreference extends ListPreference implements Callback<List<Event>> {

    private static final String DEFAULT_VALUE = "2017ncral";

    /** Selected event key */
    private String mEventKey;

    public EventListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        // set positive and negative button text
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.no);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        // persist value on "ok"
        if (positiveResult)
            persistString(mEventKey);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        if (restoreValue) {
            // restore existing state
            mEventKey = getPersistedString(DEFAULT_VALUE);
        } else {
            // set default state from xml attribute
            mEventKey = (String) defaultValue;
            persistString(mEventKey);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    @Override
    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
        List<Event> events = (List) response.body();
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    @Override
    public void onFailure(Call<List<Event>> call, Throwable t) {
        /*Snackbar.make(this, getString(R.string.download_failure),
                Snackbar.LENGTH_LONG).show();*/
    }

    /**
     *
     */
    private void getEvents() {
        /*if (!addTeamsFromStorage()) {
            SharedPreferences manager = PreferenceManager.getDefaultSharedPreferences(this);
            int idx = Integer.parseInt(manager.getString(FRC_SEASON, "0"));
            String year = getResources().getStringArray(R.array.season_names)[idx];
            year = year.substring(0, 4);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.thebluealliance.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            BlueAllianceService service = retrofit.create(BlueAllianceService.class);
            Call<List<Event>> call = service.listEvents(year, "hybrezz54:ontheprowl:1");
            call.enqueue(this);
        }*/
    }

    /**
     * Add events from a file if
     * stored on previous use of app
     *
     * @return Whether or not file exists
     */
    private boolean addTeamsFromStorage() {
        /*boolean fileExists = CSVUtil.isFileExisting(this, SharedMap.TBA_DATA_DIR, getFileName());

        if (fileExists && eventInfo != null) {
            Gson gson = new Gson();
            String json = CSVUtil.readStringFromFile(this, SharedMap.TBA_DATA_DIR, getFileName());
            List<Event> events = gson.fromJson(json, new TypeToken<List<Event>>() {}.getType());
            EventAdapter adapter = new EventAdapter(this,
                    android.R.layout.simple_dropdown_item_1line, events);
            eventInfo.setAdapter(adapter);
        }

        return fileExists;*/

        return false;
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

}
